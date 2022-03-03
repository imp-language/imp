package org.imp.jvm.runtime.stdlib.collections;

import java.util.Stack;

public class ImpStack<T> {
    private final Stack<T> stack;
    
    public ImpStack(){
        stack = new Stack<>();
    }
    
    public void push(T item){
        stack.push(item);
    }
    
    public T pop(){                 //return value on top of stack then remove it
        return stack.pop();
    }
    
    public T remove(){              //remove value from top of stack and return new value
        stack.pop();
        return stack.peek();
    }
    
    public T lookAt(){              //return value on top of stack without popping it off the stack
        return stack.peek();
    }
    
    public T dupe(){                //duplicate the value on top of the stack and return the duplicated value
        T item = stack.peek();
        stack.push(item);
        return stack.peek();
    }
    
    public T swap(T item){          //swaps a variable with the value on top of the stack
        T returnItem = stack.pop();
        stack.push(item);
        return returnItem;
    }
    
    public boolean isEmpty(){
        return stack.empty();
    }
}
