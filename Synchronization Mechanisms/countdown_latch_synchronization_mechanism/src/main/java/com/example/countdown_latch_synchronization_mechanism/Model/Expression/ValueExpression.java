package com.example.countdown_latch_synchronization_mechanism.Model.Expression;

import com.example.countdown_latch_synchronization_mechanism.Model.ADT.IDictionary;
import com.example.countdown_latch_synchronization_mechanism.Model.ADT.IHeapTable;
import com.example.countdown_latch_synchronization_mechanism.Model.Exceptions.MyException;
import com.example.countdown_latch_synchronization_mechanism.Model.Type.IType;
import com.example.countdown_latch_synchronization_mechanism.Model.Value.IValue;

public class ValueExpression implements IExpression{
    private IValue val;

    public ValueExpression(IValue v) {
        this.val = v;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable, IHeapTable<IValue> heapTable) {
        return this.val;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(this.val.deepCopy());
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        return this.val.getType();
    }

    @Override
    public String toString() {
        return this.val.toString();
    }
}
