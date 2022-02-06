package toy_interpreter.lab11_project.Model.Expression;


import toy_interpreter.lab11_project.Model.ADT.IDictionary;
import toy_interpreter.lab11_project.Model.ADT.IHeapTable;
import toy_interpreter.lab11_project.Model.Exceptions.MyException;
import toy_interpreter.lab11_project.Model.Type.IType;
import toy_interpreter.lab11_project.Model.Type.IntType;
import toy_interpreter.lab11_project.Model.Value.IValue;
import toy_interpreter.lab11_project.Model.Value.IntValue;

public class ArithmeticExpression implements IExpression{
    private IExpression exp1;
    private IExpression exp2;
    private String operation;

    public ArithmeticExpression(IExpression e1, IExpression e2, String op) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.operation = op;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable, IHeapTable<IValue> heapTable) throws MyException {
        IValue val1, val2;
        val1 = exp1.eval(symbolTable, heapTable);
        if (val1.getType().equals(new IntType())) {
            val2 = exp2.eval(symbolTable, heapTable);
            if (val2.getType().equals(new IntType())) {
                IntValue intVal1 = (IntValue) val1;
                IntValue intVal2 = (IntValue) val2;
                int num1, num2;
                num1 = intVal1.getValue();
                num2 = intVal2.getValue();
                switch (this.operation) {
                    case "+":
                        return new IntValue(num1 + num2);
                    case "-":
                        return new IntValue(num1 - num2);
                    case "*":
                        return new IntValue(num1 * num2);
                    case "/":
                        if (num2 == 0) {
                            throw new MyException("Division by zero.");
                        } else {
                            return new IntValue(num1 / num2);
                        }
                    default:
                        throw new MyException("Invalid arithmetic operation given.");
                }
            } else {
                throw new MyException("Second operand is not an integer.");
            }
        } else {
            throw new MyException("First operand is not an integer.");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(this.exp1.deepCopy(), this.exp2.deepCopy(), this.operation);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = this.exp1.typeCheck(typeEnv);
        type2 = this.exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new MyException("TYPE CHECK ERROR: Second operand is not an integer.");
            }
        } else {
            throw new MyException("TYPE CHECK ERROR: First operand is not an integer.");
        }
    }

    @Override
    public String toString() {
        return this.exp1.toString() + " " + this.operation + " " + this.exp2.toString();
    }
}
