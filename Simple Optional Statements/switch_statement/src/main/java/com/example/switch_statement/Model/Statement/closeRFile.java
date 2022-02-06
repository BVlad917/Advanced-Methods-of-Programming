package com.example.switch_statement.Model.Statement;

import com.example.switch_statement.Model.ADT.IDictionary;
import com.example.switch_statement.Model.Exceptions.MyException;
import com.example.switch_statement.Model.Expression.IExpression;
import com.example.switch_statement.Model.ProgramState.ProgramState;
import com.example.switch_statement.Model.Type.IType;
import com.example.switch_statement.Model.Type.StringType;
import com.example.switch_statement.Model.Value.IValue;
import com.example.switch_statement.Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStatement{
    private IExpression expression;

    public closeRFile(IExpression expr) {
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws MyException {
        IValue val =  this.expression.eval(currentState.getSymbolTable(), currentState.getHeapTable());
        if (val.getType().equals(new StringType())) {
            StringValue stringValue = (StringValue) val;
            BufferedReader fileDescriptor = currentState.getFileTable().lookUp(stringValue);
            try {
                fileDescriptor.close();
            } catch (IOException e) {
                throw new MyException("Failed to close the file " + stringValue);
            }
            currentState.getFileTable().removeByKey(stringValue);
        }
        else {
            throw new MyException("The given expression (" + this.expression.toString() + ") is not a String");
        }
        return null;
    }

    @Override
    public String toString() {
        return "closeFile " + this.expression.toString();
    }

    @Override
    public IStatement deepCopy() {
        return new closeRFile(this.expression);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("TYPE CHECK ERROR: The given file name is not of type String.");
        }
    }
}
