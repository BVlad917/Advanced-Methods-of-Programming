package com.example.countdown_latch_synchronization_mechanism.Model.Statement;

import com.example.countdown_latch_synchronization_mechanism.Model.ADT.IDictionary;
import com.example.countdown_latch_synchronization_mechanism.Model.Exceptions.MyException;
import com.example.countdown_latch_synchronization_mechanism.Model.ProgramState.ProgramState;
import com.example.countdown_latch_synchronization_mechanism.Model.Type.IType;
import com.example.countdown_latch_synchronization_mechanism.Model.Type.IntType;
import com.example.countdown_latch_synchronization_mechanism.Model.Value.IValue;
import com.example.countdown_latch_synchronization_mechanism.Model.Value.IntValue;

public class awaitStmt implements IStatement{
    private String varName;

    public awaitStmt(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws MyException {
        IValue foundIndex = currentState.getSymbolTable().lookUp(this.varName);
        if (!foundIndex.getType().equals(new IntType())) {
            throw new MyException("ERROR: The variable " + this.varName + " is not an integer. [AWAIT ERROR]");
        }
        IntValue foundIndexInteger = (IntValue) foundIndex;
        int foundIndexInt = foundIndexInteger.getValue();

        if (!currentState.getLatchTable().isLatchAddressUsed(foundIndexInt)) {
            throw new MyException("ERROR: The index " + foundIndexInt + " held by variable " + this.varName + " is not an index in the Latch Table[AWAIT ERROR].");
        }
        if (currentState.getLatchTable().getLatchCounter(foundIndexInt) > 0) {
            currentState.getExecutionStack().push(this);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new awaitStmt(this.varName);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.lookUp(this.varName);
        if (!varType.equals(new IntType())) {
            throw new MyException("ERROR: The variable " + this.varName + " is not an integer. [AWAIT ERROR]");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "await(" + this.varName + ")";
    }
}
