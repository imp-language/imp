package org.imp.jvm.parsing;

import org.imp.jvm.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class Node<T extends Statement> {
    public final T value;
    public final List<Node<T>> children;
    public final boolean IsEmpty;

    public Node() {
        value = null;
        this.children = new ArrayList<>();
        IsEmpty = true;
    }

    public Node(T value, ArrayList<Node<T>> children) {
        this.value = value;
        this.children = children;
        IsEmpty = false;
    }

    public Node(T value) {
        this.value = value;
        this.children = new ArrayList<>();
        IsEmpty = false;
    }
}