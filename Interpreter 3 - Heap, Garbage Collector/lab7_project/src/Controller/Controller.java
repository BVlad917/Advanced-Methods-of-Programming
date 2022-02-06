package Controller;

import Model.ADT.IList;
import Model.ADT.IStack;
import Model.ADT.MyList;
import Model.Exceptions.*;
import Model.ProgramState.ProgramState;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repository.IRepository;

import java.io.IOException;
import java.sql.Ref;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private boolean displayFlag;

    public Controller(IRepository repo) {
        this.repo = repo;
        this.displayFlag = false;
    }

    public void addProgramState(ProgramState prg) {
        this.repo.addProgramState(prg);
    }

    public ProgramState oneStep(ProgramState currentState) throws ControllerException, StackException, StatementException, ExpressionException, DictionaryException, FileException, HeapException {
        IStack<IStatement> executionStack = currentState.getExecutionStack();
        if (executionStack.isEmpty()) {
            throw new ControllerException("Program state's execution stack is empty.");
        }
        IStatement topStatement = executionStack.pop();
        return topStatement.execute(currentState);
    }

    public void allSteps() throws ControllerException, StatementException, StackException, ExpressionException, DictionaryException, ListException, FileException, IOException, HeapException {
        ProgramState programState = this.repo.getCurrentProgramState();
        if (programState.getExecutionStack().isEmpty()) {
            throw new ControllerException("ERROR: The program was already executed. The execution stack is empty.");
        }
        this.repo.logPrgStateExec();
        if (this.displayFlag) {
            System.out.println("Program execution started:");
            System.out.print(programState + "\n");
        }
        int outputListSize = 0;
        IList<IValue> output;
        while (!programState.getExecutionStack().isEmpty()) {
            this.oneStep(programState);
            this.repo.logPrgStateExec();
            Map<Integer, IValue> heapContent = programState.getHeapTable().getContent();
            List<Integer> symbolTableAddresses = this.getAddressesFromSymTable(programState.getSymbolTable().getContent().values());
            List<Integer> allReferencedAddresses = this.addIndirectAddresses(symbolTableAddresses, heapContent);
            programState.getHeapTable().setContent(this.garbageCollector(allReferencedAddresses, heapContent));   // garbage collector call

            if (this.displayFlag) {
                System.out.println(programState);
            } else {
                output = programState.getOutput();
                if (outputListSize != output.size()) {
                    outputListSize = output.size();
                    System.out.println(output.getElemAtIndex(output.size() - 1).toString());
                }
            }
        }
    }

    private List<Integer> getAddressesFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> addIndirectAddresses(List<Integer> addressesFromSymbolTable, Map<Integer, IValue> heap) {
        boolean change = true;
        List<Integer> newAddressList = new ArrayList<>(addressesFromSymbolTable);   //copy of list in order to add indirections
        // we go through heapSet again and again and each time we add to the address list new indirection level
        // and new addresses which must NOT be deleted
        while (change) {
            List<Integer> appendingList;
            change = false;

            appendingList = heap.entrySet().stream()
                    .filter(e -> e.getValue() instanceof RefValue)      // check if val in heap is RefValue, so it can have indirections
                    .filter(e -> newAddressList.contains(e.getKey()))   // check if address list contains ref to this
                    .map(e -> ((RefValue) e.getValue()).getAddress())   // map the reference to its address, so we can add it
                    .filter(e -> !newAddressList.contains(e))           // check if the address list already has that reference from prev elems
                    .collect(Collectors.toList());                      // collect to list
            if (!appendingList.isEmpty()) {
                // if we get here => we still have indirect references, so we have to keep checking
                change = true;
                newAddressList.addAll(appendingList);
            }
        }
        return newAddressList;
    }

    private Map<Integer, IValue> garbageCollector(List<Integer> referencedAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> referencedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    void turnDisplayFlagOn() {
        this.displayFlag = true;
    }

    void turnDisplayFlagOff() {
        this.displayFlag = false;
    }
}
