package com.example.switch_statement.Model.ADT;

import java.util.Stack;

public interface IStack<T> {
    void push(T newElem);
    T pop();
    T peek();
    boolean isEmpty();
    Stack<T> getContent();
}
