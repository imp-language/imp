package org.imp.jvm.visitors;

import org.apache.commons.text.StringEscapeUtils;
import org.imp.jvm.Util;
import org.imp.jvm.domain.Environment;
import org.imp.jvm.parser.Expr;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.parser.tokenizer.Token;
import org.imp.jvm.parser.tokenizer.TokenType;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FuncType;
import org.imp.jvm.types.StructType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrettyPrinterVisitor implements IVisitor<String> {
    public final Environment rootEnvironment;
    public final boolean displayAnnotations = true;
    public Environment currentEnvironment;
    private int indent = 0;

    public PrettyPrinterVisitor(Environment rootEnvironment) {
        this.rootEnvironment = rootEnvironment;
        this.currentEnvironment = this.rootEnvironment;
    }

    public String print(List<? extends Stmt> stmts) {
        StringBuilder sb = new StringBuilder();
        for (var stmt : stmts) {
            sb.append(stmt.accept(this)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String visit(Stmt stmt) {
        return null;
    }

    @Override
    public String visitAlias(Stmt.Alias stmt) {
        return s("type", stmt.identifier.source(), "=", print(stmt.typeStmt));

    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return s(print(expr.left), "=", print(expr.right));
    }

    @Override
    public String visitBad(Expr.Bad expr) {
        String result = "(BAD ";
        result += (Arrays.stream(expr.badTokens).map(tok -> StringEscapeUtils.escapeJava(tok.source())).collect(Collectors.joining(", ")));
        result += "))";
        return result;
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return s(print(expr.left), expr.operator.representation(), print(expr.right));
    }

    @Override
    public String visitBlockStmt(Stmt.Block block) {
        StringBuilder sb = new StringBuilder("{");
        indent++;
        for (var stmt : block.statements) {
            sb.append(tabs()).append(print(stmt));
        }

        indent--;
        sb.append(tabs()).append("}");
        return sb.toString();
    }

    @Override
    public String visitCall(Expr.Call expr) {

        return print(expr.item) + "(" + expr.arguments.stream().map(a -> {
            String repr = print(a);
            var c = a.realType;
            if (c == null) {
                System.out.println("reee");
            }
            if (displayAnnotations) {
                repr += " : " + a.realType.getName();
            }
            return repr;
        }).collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public String visitEmpty(Expr.Empty empty) {
        return "";
    }

    @Override
    public String visitEmptyList(Expr.EmptyList emptyList) {
        return emptyList.tokenType.source() + "[]";
    }

    @Override
    public String visitEnum(Stmt.Enum stmt) {

        return "(enum " + stmt.name.source() + " (" + stmt.values.stream().map(Token::source).collect(Collectors.joining(", ")) +
                "))";
    }

    @Override
    public String visitExport(Stmt.Export stmt) {
        return s("export", print(stmt.stmt));
    }

    @Override
    public String visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return print(stmt.expr);
    }

    @Override
    public String visitFor(Stmt.For stmt) {
        currentEnvironment = stmt.block.environment;
        String source = stmt.name.source();

        if (displayAnnotations) {
            var t = currentEnvironment.getVariable(source);
            if (t != null) {
                source += " : " + t;

            } else {
                source += " : ";
            }
        }

        String str = s("for", source, "in", print(stmt.expr), print(stmt.block));
        currentEnvironment = currentEnvironment.getParent();
        return str;
    }

    @Override
    public String visitFunctionStmt(Stmt.Function stmt) {
        String name = stmt.name.source();
        // Find the FunctionType associated with this statement
        var funcType = currentEnvironment.getVariableTyped(stmt.name.source(), FuncType.class);

        currentEnvironment = stmt.body.environment;

        if (funcType != null) {
            String result = "func " + name + "(";
            result += stmt.parameters.stream().map(parameter -> parameter.name.source() + " " + parameter.type.accept(this)).collect(Collectors.joining(", "));
            result += ") " + funcType.returnType + " ";
            result += print(stmt.body);
            currentEnvironment = currentEnvironment.getParent();
            return result;
        }
        return "nope";
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return s("(", print(expr.expr), ")");
    }

    @Override
    public String visitIdentifierExpr(Expr.Identifier expr) {
        return expr.identifier.source();
    }

    @Override
    public String visitIf(Stmt.If stmt) {
        StringBuilder sb = new StringBuilder();
        sb.append(s("if", print(stmt.condition), print(stmt.trueBlock)));
        if (stmt.falseStmt != null) {
            sb.append(s(" else", print(stmt.falseStmt)));
        }
        return sb.toString();
    }

    @Override
    public String visitImport(Stmt.Import stmt) {

        String s = "import " + '"' + stmt.stringLiteral.source() + "\"";
        if (stmt.identifier.isPresent()) s += " as " + stmt.identifier.get().source();
        return s;
    }

    @Override
    public String visitIndexAccess(Expr.IndexAccess expr) {
        return print(expr.left) + "[" + print(expr.right) + "]";
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        String source = expr.literal.source();
        if (expr.literal.type() == TokenType.STRING) source = '"' + source + '"';

        return source;
    }

    @Override
    public String visitLiteralList(Expr.LiteralList expr) {
        return "[" + expr.entries.stream().map(this::print).collect(Collectors.joining(",")) + "]";
    }

    @Override
    public String visitMatch(Stmt.Match match) {
//        StringBuilder s = new StringBuilder("match " + print(match.expr) + " as " + print(match.identifier) + " {");
//        indent++;
//        for (var c : match.cases.entrySet()) {
//            var type = c.getKey();
//            var expr = c.getValue();
//            s.append(tabs()).append(s(print(type), "->", print(expr)));
//        }
//        indent--;
//        s.append(tabs()).append("}");
        return "s.toString()";
    }


    @Override
    public String visitParameterStmt(Stmt.Parameter stmt) {
        String name = stmt.name.source();
        var t = currentEnvironment.getVariable(stmt.name.source());
        if (displayAnnotations) {
            if (t != null) {
                if (!(t instanceof BuiltInType)) {
                    name += " : " + t;
                }

            } else {
                name += " : ";
            }
        }
        //Todo: below
//        if (stmt.listType()) result += "[]";
        return name + " " + stmt.type.accept(this);
    }

    @Override
    public String visitPostfixExpr(Expr.Postfix expr) {
        return print(expr.expr) + expr.operator.source();
    }

    @Override
    public String visitPrefix(Expr.Prefix expr) {
        return s(expr.operator.source(), print(expr.right));
    }

    @Override
    public String visitPropertyAccess(Expr.PropertyAccess expr) {
        String s = print(expr.expr);
        s += "." + expr.identifiers.stream().map(i -> i.identifier.source()).collect(Collectors.joining("."));
        return s;
    }


    @Override
    public String visitReturnStmt(Stmt.Return stmt) {
        var s = print(stmt.expr);
        if (stmt.expr instanceof Expr.Identifier) {

            if (displayAnnotations) {
                var t = currentEnvironment.getVariable(s);
                if (t != null) {
                    s += " : " + t;

                } else {
                    s += " : $";
                }
            }
        }
        return s("return", s);
    }

    @Override
    public String visitStruct(Stmt.Struct stmt) {
        StringBuilder result = new StringBuilder("struct " + stmt.name.source() + " {");
        var structType = currentEnvironment.getVariableTyped(stmt.name.source(), StructType.class);
        if (structType != null) {
            indent++;
            for (var field : structType.parameters) {
                result.append(tabs()).append(field.name).append(" ").append(field.type);
            }
            indent--;
        } else {
            Util.exit("struct type missing", 792);
        }

        result.append(tabs()).append("}");
        return result.toString();
    }

    @Override
    public String visitType(Stmt.TypeStmt stmt) {
        // if no type.next exists, treat as normal type
        if (stmt.next.isEmpty()) {
            String s = stmt.identifier.source();
            if (stmt.listType) {
                s += "[]";
            }
            return s;
        }
        // if type.next exists, make an unknown type and pass to TypeCheckVisitor
        else {
            return stmt.identifier.source() + "." + stmt.next.get().accept(this);
        }
    }

    @Override
    public String visitUnionType(Stmt.UnionTypeStmt unionTypeStmt) {
        return unionTypeStmt.types.stream().map(this::print).collect(Collectors.joining(" | "));
    }


    @Override
    public String visitVariable(Stmt.Variable stmt) {

        var name = stmt.name.source();

        if (displayAnnotations) {
            var t = currentEnvironment.getVariable(name);
            if (t != null) {
                name += " : " + t;

            } else {
                name += " : $";
            }
        }

        return s(stmt.mutability.source(), name, "=", print(stmt.expr));
    }

    @Override
    public String visitWhile(Stmt.While aWhile) {
        return s("while", print(aWhile.condition), print(aWhile.block));
    }

    String print(Expr expr) {
        return expr.accept(this);
    }

    String print(Stmt stmt) {
        return stmt.accept(this);
    }

    String tabs() {
        return "\n" + "\t".repeat(indent);
    }

    private String s(String... strs) {

        List<String> strList = Arrays.asList(strs);

        return String.join(" ", strList);
    }
}
