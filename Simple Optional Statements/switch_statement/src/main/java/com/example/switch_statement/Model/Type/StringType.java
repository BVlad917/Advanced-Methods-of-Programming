package com.example.switch_statement.Model.Type;

import com.example.switch_statement.Model.Value.IValue;
import com.example.switch_statement.Model.Value.StringValue;

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
