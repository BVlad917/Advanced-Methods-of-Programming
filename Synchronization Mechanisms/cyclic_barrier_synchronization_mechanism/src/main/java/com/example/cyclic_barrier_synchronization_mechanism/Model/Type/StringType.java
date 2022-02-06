package com.example.cyclic_barrier_synchronization_mechanism.Model.Type;

import com.example.cyclic_barrier_synchronization_mechanism.Model.Value.IValue;
import com.example.cyclic_barrier_synchronization_mechanism.Model.Value.StringValue;

public class StringType implements IType{
    public StringType() {}

    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }
}
