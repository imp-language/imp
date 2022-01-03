package org.imp.jvm.visitors;

import org.apache.commons.text.StringEscapeUtils;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.TokenType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ASTPrinterVisitor implements Expr.Visitor<String>, Stmt.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    String print(Stmt stmt) {
        return stmt.accept(this);
    }

    public String print(List<Stmt> stmts) {
        StringBuilder sb = new StringBuilder();
        for (var stmt : stmts) {
            sb.append(stmt.accept(this)).append("\n");
        }
        return sb.toString();
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

    private String parenthesize(String name, Stmt... stmts) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Stmt stmt : stmts) {
            builder.append(" ");
            builder.append(stmt.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

    private String parenthesize(String name, List<Stmt> stmts) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Stmt stmt : stmts) {
            builder.append(" ");
            builder.append(stmt.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

    private String parenthesize2(String name, List<Expr> exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }


    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return parenthesize("=", expr.left(), expr.right());
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator().representation(), expr.left(), expr.right());
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expr());
    }

    @Override
    public String visitIdentifierExpr(Expr.Identifier expr) {
        return expr.identifier().source();
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        return null;
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        String source = expr.literal().source();
        if (expr.literal().type() == TokenType.STRING) return '"' + source + '"';
        return source;
    }

    @Override
    public String visitLiteralList(Expr.LiteralList expr) {
        return "[" + expr.entries().stream().map(this::print).collect(Collectors.joining(",")) + "]";
    }

    @Override
    public String visitPrefix(Expr.Prefix expr) {
        return parenthesize(expr.operator().source(), expr.right());
    }

    @Override
    public String visitCall(Expr.Call expr) {
        StringBuilder result = new StringBuilder("(call " + print(expr.item()) + " (");
        result.append(expr.arguments().stream().map(this::print).collect(Collectors.joining(", ")));

        result.append("))");
        return result.toString();
    }

    @Override
    public String visitPostfixExpr(Expr.Postfix expr) {
        return parenthesize(expr.operator().source(), expr.expr());
    }

    @Override
    public String visitBad(Expr.Bad expr) {
        String result = "(BAD ";
        result += (Arrays.stream(expr.badTokens()).map(tok -> StringEscapeUtils.escapeJava(tok.source())).collect(Collectors.joining(", ")));
        result += "))";
        return result;
    }

    @Override
    public String visitNew(Expr.New expr) {
        return parenthesize("new", expr.call());
    }

    @Override
    public String visitPropertyAccess(Expr.PropertyAccess expr) {


        return parenthesize("property", expr.left(), expr.right());
    }

    @Override
    public String visitRange(Expr.Range range) {
        return parenthesize("range", range.left(), range.right());
    }

    @Override
    public String visitEmptyList(Expr.EmptyList emptyList) {
        return "(list" + "[" + emptyList.type().source() + "])";
    }

    @Override
    public String visitIndexAccess(Expr.IndexAccess expr) {
        return parenthesize("index", expr.left(), expr.right());
    }

    @Override
    public String visit(Stmt stmt) {
        return null;
    }

    @Override
    public String visitBlockStmt(Stmt.Block stmt) {
        return parenthesize("block", stmt.statements());
    }

    @Override
    public String visitExport(Stmt.Export stmt) {
        return parenthesize("export", stmt.stmt());
    }

    @Override
    public String visitEnum(Stmt.Enum stmt) {
        StringBuilder result = new StringBuilder("(enum " + stmt.name().source() + " (");


        result.append(stmt.values().stream().map(Token::source).collect(Collectors.joining(", ")));

        result.append("))");
        return result.toString();
    }

    @Override
    public String visitStruct(Stmt.Struct stmt) {
        StringBuilder result = new StringBuilder("(struct " + stmt.name().source() + " (");


        result.append(stmt.fields().stream().map(this::print).collect(Collectors.joining(", ")));

        result.append("))");
        return result.toString();
    }

    @Override
    public String visitType(Stmt.Type stmt) {
        // if no type.next exists, treat as normal type
        if (stmt.next().isEmpty()) {
            return stmt.identifier().source();
        }
        // if type.next exists, make an unknown type and pass to TypeCheckVisitor
        else {
            return parenthesize("type " + stmt.identifier().source(), stmt.next().get());
        }
    }

    @Override
    public String visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return print(stmt.expr());
    }

    @Override
    public String visitFunctionStmt(Stmt.Function stmt) {
        StringBuilder result = new StringBuilder("(func " + stmt.name().source());


        result.append(" (");

        result.append(stmt.parameters().stream().map(this::print).collect(Collectors.joining(", ")));

        if (stmt.returnType() != null) result.append(") ").append(stmt.returnType().source());
        result.append(")\n\t");
        result.append(print(stmt.body()));
        return result.toString();
    }

    @Override
    public String visitIf(Stmt.If stmt) {
        StringBuilder sb = new StringBuilder();
        if (stmt.falseStmt() == null) {
            sb.append("(if ")
                    .append(print(stmt.condition()))
                    .append("\n\t")
                    .append(print(stmt.trueBlock()));
        } else {
            sb.append("(if-else ")
                    .append(print(stmt.condition()))
                    .append("\n\t")
                    .append(print(stmt.trueBlock()))
                    .append("\n\t")
                    .append(print(stmt.falseStmt()));
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String visitImport(Stmt.Import stmt) {
        return "(export " + stmt.stringLiteral().source() + ")";
    }

    @Override
    public String visitReturnStmt(Stmt.Return stmt) {
        return parenthesize("return", stmt.expr());
    }

    @Override
    public String visitVariable(Stmt.Variable stmt) {
        return parenthesize(stmt.mutability().source() + " " + stmt.name().source(), stmt.expr());
    }


    @Override
    public String visitParameterStmt(Stmt.Parameter stmt) {
        String result = stmt.name().source() + " " + stmt.type().accept(this);

        // Todo: below
//        if (stmt.listType()) result += "[]";
        return result;
    }


    @Override
    public String visitFor(Stmt.For stmt) {
        StringBuilder sb = new StringBuilder("(for ");
        sb.append(print(stmt.condition()))
                .append("\n\t")
                .append(print(stmt.block()));
        return sb.toString();
    }

    @Override
    public String visitForInCondition(Stmt.ForInCondition stmt) {
        return "(for-in " + stmt.name().source() + " " + print(stmt.expr()) + ")";
    }


    @Override
    public String visitTypeAlias(Stmt.TypeAlias stmt) {
        return "(type " + stmt.name().source() + " extern " + print(stmt.literal()) + ")";
    }
}
