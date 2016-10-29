package com.victor.atomic;
import java.util.EmptyStackException;

public class Stack{
    private int[] array;//������ʵ��
    private int top; //ջ��ָ��
    private final int size=100;
    public Stack(){
        array = new int[size];
        top = -1; //ջ�յ�ʱ�� 
    }
    //ѹջ
    public void push(int element){
        if(top == size-1){
            throw new StackOverflowError();
        }
        else 
            array[++top] = element;
    }
    //��ջ
    public int pop(){
        if(top == -1){
            throw new EmptyStackException();
        }
        return array[top--];
    }
    //�ж��Ƿ�Ϊ��
    public boolean isEmpty(){
        return top == -1;
    }
    //����ջ��Ԫ��
    public Integer peek(){
        if(top == -1){
            throw new EmptyStackException();
        }
        return array[top];
    }
}