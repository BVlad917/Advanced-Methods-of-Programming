package com.example.sleep.Model.Statement;

import com.example.sleep.Model.ADT.IDictionary;
import com.example.sleep.Model.Exceptions.MyException;
import com.example.sleep.Model.Expression.IExpression;
import com.example.sleep.Model.ProgramState.ProgramState;
import com.example.sleep.Model.Type.IType;
import com.example.sleep.Model.Type.RefType;
import com.example.sleep.Model.Value.IValue;
import com.example.sleep.Model.Value.RefValue;

public class writeToHeap implements IStatement{
    private String id;
    private IExpression expression;

    public writeToHeap(String varName, IExpression expr) {
        this.id = varName;
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws MyException {
        if (!currentState.getSymbolTable().isDefined(this.id)) {
            throw new MyException("ERROR: The given variable(" + this.id + ") is not defined in the symbol table.");
        }
        IValue variableValue = currentState.getSymbolTable().lookUp(this.id);
        if (!(variableValue.getType().equals(new RefType(null)))) {
            throw new MyException("ERROR: The given variable(" + this.id + ") is not of type RefType.");
        }
        RefValue refValue = (RefValue) variableValue;
        if (!currentState.getHeapTable().isDefined(refValue.getAddress())) {
            throw new MyException("ERROR: The address associated with the given variable(" + this.id + ") is no longer available in the heap.");
        }
        IValue expressionValue = this.expression.eval(currentState.getSymbolTable(), currentState.getHeapTable());
        if (!expressionValue.getType().equals(refValue.getLocationType())) {
            throw new MyException("ERROR: The type of the given expression(" + this.expression.toString() + ") does not match with the location type.");
        }
        currentState.getHeapTable().updateHeapEntry(refValue.getAddress(), expressionValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new writeToHeap(this.id, this.expression.deepCopy());
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = this.expression.typeCheck(typeEnv);
        IType typeVar = typeEnv.lookUp(this.id);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else {
            throw new MyException("TYPE CHECK ERROR: Right hand side and left hand side in writeHeap statement have different types.");
        }
    }

    @Override
    public String toString() {
        return "WriteToHeap(" + this.id + ", " + this.expression.toString() + ")";
    }
}
