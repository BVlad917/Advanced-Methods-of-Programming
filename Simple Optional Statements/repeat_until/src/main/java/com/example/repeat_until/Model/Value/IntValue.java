package com.example.repeat_until.Model.Value;

import com.example.repeat_until.Model.Type.IType;
import com.example.repeat_until.Model.Type.IntType;

import java.util.Objects;

public class IntValue implements IValue{
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "" + this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return value == intValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
