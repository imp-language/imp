package org.imp.jvm.visitors;

import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;


public class StandardTraversal {

    public static <T> void traverse(Stmt stmt, Stmt.Visitor<T> sVisitor, Expr.Visitor<T> eVisitor) {
        // process the current node
        stmt.accept(sVisitor);

        // get its children
        var children = stmt.children();

        // process the children
        for (var child : children) {
            if (child instanceof Stmt s) {
                traverse(s, sVisitor, eVisitor);
            } else if (child instanceof Expr e) {
                traverse(e, sVisitor, eVisitor);
            }
        }
    }

    public static <T> void traverse(Expr expr, Stmt.Visitor<T> sVisitor, Expr.Visitor<T> eVisitor) {
        // process the current node
        expr.accept(eVisitor);

        // get its children
        var children = expr.children();

        // process the children
        for (var child : children) {
            if (child instanceof Stmt s) {
                traverse(s, sVisitor, eVisitor);
            } else if (child instanceof Expr e) {
                traverse(e, sVisitor, eVisitor);
            }
        }
    }
}
