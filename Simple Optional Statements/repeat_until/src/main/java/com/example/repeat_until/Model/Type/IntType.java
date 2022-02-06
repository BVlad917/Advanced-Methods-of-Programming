package com.example.repeat_until.Model.Type;

import com.example.repeat_until.Model.Value.IValue;
import com.example.repeat_until.Model.Value.IntValue;

public class IntType implements IType{
    public IntType() {}

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }
}