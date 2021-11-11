package org.imp.jvm.typechecker;

import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.expression.Literal;
import org.imp.jvm.statement.If;
import org.imp.jvm.statement.Return;
import org.imp.jvm.statement.Statement;
import org.imp.jvm.tokenizer.TokenType;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.Type;

public class TypeChecker implements Expr.Visitor<Expression>, Stmt.Visitor<Statement> {

    Expression validate(Expr expr) {
        return expr.accept(this);
    }

    Statement validate(Stmt stmt) {
        return stmt.accept(this);
    }


    @Override
    public Expression visitAssignExpr(Expr.Assign expr) {
        return null;
    }

    @Override
    public Expression visitBinaryExpr(Expr.Binary expr) {
        return null;
    }

    @Override
    public Expression visitGroupingExpr(Expr.Grouping expr) {
        return null;
    }

    @Override
    public Expression visitIdentifierExpr(Expr.Identifier expr) {
        return null;
    }

    @Override
    public Expression visitLogicalExpr(Expr.Logical expr) {
        return null;
    }

    @Override
    public Expression visitLiteralExpr(Expr.Literal expr) {
        String source = expr.literal().source();
        TokenType tokenType = expr.literal().type();

        Type type = BuiltInType.getFromToken(tokenType);
//        Object transformed = TypeResolver.getValueFromString(expr.literal().source(), type);


        return new Literal(type, source);
    }

    @Override
    public Expression visitLiteralList(Expr.LiteralList expr) {
        return null;
    }

    @Override
    public Expression visitPrefix(Expr.Prefix expr) {
        return null;
    }

    @Override
    public Expression visitCall(Expr.Call expr) {
        return null;
    }

    @Override
    public Expression visitPostfixExpr(Expr.Postfix expr) {
        return null;
    }

    @Override
    public Expression visitBad(Expr.Bad expr) {
        return null;
    }

    @Override
    public Expression visitNew(Expr.New expr) {
        return null;
    }

    @Override
    public Expression visitPropertyAccess(Expr.PropertyAccess expr) {
        return null;
    }

    @Override
    public Expression visitIndexAccess(Expr.IndexAccess expr) {
        return null;
    }

    @Override
    public Statement visitBlockStmt(Stmt.Block stmt) {
        for (var statement : stmt.statements()) {
            statement.accept(this);
        }
        return null;
    }

    @Override
    public Statement visitExport(Stmt.Export stmt) {
        return null;
    }

    @Override
    public Statement visitEnum(Stmt.Enum stmt) {
        return null;
    }

    @Override
    public Statement visitStruct(Stmt.Struct stmt) {
        return null;
    }

    @Override
    public Statement visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return null;
    }

    @Override
    public Statement visitFunctionStmt(Stmt.Function stmt) {
        return null;
    }

    @Override
    public Statement visitIf(Stmt.If stmt) {
        var condition = stmt.condition().accept(this);
        var trueBlock = stmt.trueStmt().accept(this);
        Statement falseBlock = null;
        if (stmt.falseStmt() != null) {
            falseBlock = stmt.falseStmt().accept(this);
        }

        return new If(condition, trueBlock, falseBlock);
    }

    @Override
    public Statement visitReturnStmt(Stmt.Return stmt) {
        var expression = stmt.expr().accept(this);
        return new Return(expression);
    }

    @Override
    public Statement visitVariable(Stmt.Variable stmt) {
        Expression expression = stmt.expr().accept(this);

        return null;
    }

    @Override
    public Statement visitParameterStmt(Stmt.Parameter stmt) {
        return null;
    }

    @Override
    public Statement visitFor(Stmt.For stmt) {
        return null;
    }

    @Override
    public Statement visitForInCondition(Stmt.ForInCondition stmt) {
        return null;
    }

    @Override
    public Statement visitTypeAlias(Stmt.TypeAlias stmt) {
        return null;
    }
}
