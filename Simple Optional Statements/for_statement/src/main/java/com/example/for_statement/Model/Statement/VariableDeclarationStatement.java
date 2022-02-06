package com.example.for_statement.Model.Statement;

import com.example.for_statement.Model.ADT.IDictionary;
import com.example.for_statement.Model.Exceptions.MyException;
import com.example.for_statement.Model.ProgramState.ProgramState;
import com.example.for_statement.Model.Type.IType;
import com.example.for_statement.Model.Value.IValue;

public class VariableDeclarationStatement implements IStatement{
    private String name;
    private IType type;

    public VariableDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws MyException {
        IDictionary<String, IValue> symbolTable = currentState.getSymbolTable();
        if (symbolTable.isDefined(this.name)) {
            throw new MyException("Variable " + this.name + " is already declared.");
        } else {
            symbolTable.addKeyValuePair(this.name, this.type.getDefaultValue());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(this.name, this.type.deepCopy());
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.addKeyValuePair(this.name, this.type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}
