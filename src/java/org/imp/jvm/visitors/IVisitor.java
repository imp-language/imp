package org.imp.jvm.visitors;

import org.imp.jvm.parser.Expr;
import org.imp.jvm.parser.Stmt;

public interface IVisitor<R> extends Stmt.Visitor<R>, Expr.Visitor<R> {
}
