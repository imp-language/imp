package org.imp.jvm.visitors;

import org.apache.commons.text.StringEscapeUtils;
import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.TokenType;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FuncType;
import org.imp.jvm.types.StructType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrettyPrinterVisitor implements Expr.Visitor<String>, Stmt.Visitor<String> {
    public boolean displayAnnotations = true;


    public final Environment rootEnvironment;
    public Environment currentEnvironment;

    public PrettyPrinterVisitor(Environment rootEnvironment) {
        this.rootEnvironment = rootEnvironment;
        this.currentEnvironment = this.rootEnvironment;
    }

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


    private String s(Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        List<Expr> exprList = Arrays.asList(exprs);

        return exprList.stream().map(this::print).collect(Collectors.joining(" "));
    }

    private String s(String... strs) {
        StringBuilder builder = new StringBuilder();

        List<String> strList = Arrays.asList(strs);

        return String.join(" ", strList);
    }


    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return s(print(expr.left()), "=", print(expr.right()));
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return s(print(expr.left()), expr.operator().representation(), print(expr.right()));
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return s("(", print(expr), ")");
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
        if (expr.literal().type() == TokenType.STRING) source = '"' + source + '"';

        return source;
    }

    @Override
    public String visitLiteralList(Expr.LiteralList expr) {
        return "[" + expr.entries().stream().map(this::print).collect(Collectors.joining(",")) + "]";
    }

    @Override
    public String visitPrefix(Expr.Prefix expr) {
        return s(expr.operator().source(), print(expr.right()));
    }

    @Override
    public String visitCall(Expr.Call expr) {
        StringBuilder result = new StringBuilder(print(expr.item()) + "(");
        result.append(expr.arguments().stream().map(this::print).collect(Collectors.joining(", ")));

        result.append(")");
        return result.toString();
    }

    @Override
    public String visitPostfixExpr(Expr.Postfix expr) {
        return s(expr.operator().source(), print(expr.expr()));
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
        return s("new", print(expr.call()));
    }

    @Override
    public String visitPropertyAccess(Expr.PropertyAccess expr) {
        return s(print(expr.left()), ".", print(expr.right()));
    }

    // Todo: relate to binary expressions somehow
    @Override
    public String visitRange(Expr.Range range) {
        return print(range.left()) + ".." + print(range.right());
    }

    @Override
    public String visitEmptyList(Expr.EmptyList emptyList) {
        return emptyList.type().source() + "[]";
    }

    @Override
    public String visitIndexAccess(Expr.IndexAccess expr) {
        return print(expr.left()) + "[" + print(expr.right()) + "]";
    }

    @Override
    public String visit(Stmt stmt) {
        return null;
    }

    private int indent = 0;

    String tabs() {
        return "\n" + "\t".repeat(indent);
    }

    @Override
    public String visitBlockStmt(Stmt.Block block) {
        StringBuilder sb = new StringBuilder("{");
        indent++;
        for (var stmt : block.statements()) {
            sb.append(tabs() + print(stmt));
        }


        indent--;
        sb.append(tabs() + "}");
        return sb.toString();
    }

    @Override
    public String visitExport(Stmt.Export stmt) {
        return s("export", print(stmt.stmt()));
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
        StringBuilder result = new StringBuilder("struct " + stmt.name().source() + " {\n");
        var structType = currentEnvironment.getVariableTyped(stmt.name().source(), StructType.class);
        if (structType != null) {
            for (var field : structType.fields) {
                result.append("\t" + field.name + " " + field.type);
                result.append("\n");
            }
        } else {

            for (Stmt.Parameter field : stmt.fields()) {
                result.append("\t" + field.name().source() + " ");
                System.err.println("bad");


                if (field.listType()) result.append("[]");
                result.append("\n");
            }
        }

        result.append("))");
        return result.toString();
    }

    @Override
    public String visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return print(stmt.expr());
    }

    @Override
    public String visitFunctionStmt(Stmt.Function stmt) {
        String name = stmt.name().source();
        // Find the FunctionType associated with this statement
        var funcType = currentEnvironment.getVariableTyped(stmt.name().source(), FuncType.class);

        currentEnvironment = stmt.body().environment();

        if (funcType != null) {
            String result = "func " + name + "(";
            result += funcType.parameters.stream().map(identifier -> {
                return identifier.name + " " + identifier.type;
            }).collect(Collectors.joining(", "));
            result += ") " + funcType.returnType + " ";

            result += print(stmt.body());
            currentEnvironment = currentEnvironment.getParent();
            return result.toString();
        }
        return "nope";
    }

    @Override
    public String visitIf(Stmt.If stmt) {
        StringBuilder sb = new StringBuilder();
        sb.append(s("if", print(stmt.condition()), print(stmt.trueBlock())));
        if (stmt.falseStmt() != null) {
            sb.append(s(" else", print(stmt.falseStmt())));
        }
        return sb.toString();
    }

    @Override
    public String visitReturnStmt(Stmt.Return stmt) {
        var s = print(stmt.expr());
        if (stmt.expr() instanceof Expr.Identifier) {

            if (displayAnnotations) {
                var t = currentEnvironment.getVariable(s);
                if (t != null) {
                    s += " : " + t.toString();

                } else {
                    s += " : $reee";
                }
            }
        }
        return s("return", s);
    }

    @Override
    public String visitVariable(Stmt.Variable stmt) {

        var name = stmt.name().source();

        if (displayAnnotations) {
            var t = currentEnvironment.getVariable(name);
            if (t != null) {
                name += " : " + t.toString();

            } else {
                name += " : $reee";
            }
        }

        return s(stmt.mutability().source(), name, "=", print(stmt.expr()));
    }


    @Override
    public String visitParameterStmt(Stmt.Parameter stmt) {
        String name = stmt.name().source();
        var t = currentEnvironment.getVariable(stmt.name().source());
        if (displayAnnotations) {
            if (t != null) {
                if (!(t instanceof BuiltInType)) {
                    name += " : " + t.toString();
                }

            } else {
                name += " : $reee";
            }
        }
        String result = name + " " + stmt.type().source();

        if (stmt.listType()) result += "[]";
        return result;
    }


    @Override
    public String visitFor(Stmt.For stmt) {
        var childEnvironment = stmt.block().environment();
        currentEnvironment = childEnvironment;
        String str = s("for", print(stmt.condition()), print(stmt.block()));
        currentEnvironment = currentEnvironment.getParent();
        return str;
    }

    @Override
    public String visitForInCondition(Stmt.ForInCondition stmt) {
        var name = stmt.name().source();
        var t = currentEnvironment.getVariable(stmt.name().source());
        if (displayAnnotations) {
            if (t != null) {
                name += " : " + t.toString();

            } else {
                name += " : $reee";
            }
        }
        return s(name, "in", print(stmt.expr()));
    }


    @Override
    public String visitTypeAlias(Stmt.TypeAlias stmt) {
        return "(type " + stmt.name().source() + " extern " + print(stmt.literal()) + ")";
    }
}
