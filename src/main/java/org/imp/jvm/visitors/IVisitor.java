package org.imp.jvm.visitors;

import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;

public interface IVisitor<R> extends Stmt.Visitor<R>, Expr.Visitor<R> {
}
