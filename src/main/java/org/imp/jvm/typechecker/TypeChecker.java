package org.imp.jvm.typechecker;

import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.*;
import org.imp.jvm.expression.reference.VariableReference;
import org.imp.jvm.statement.*;
import org.imp.jvm.tokenizer.TokenType;
import org.imp.jvm.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.imp.jvm.tokenizer.TokenType.*;

public class TypeChecker implements Expr.Visitor<Expression>, Stmt.Visitor<Statement> {

    private final Scope scope;
    private final ImpFile parent;

    public TypeChecker(Scope scope, ImpFile parent) {
        this.scope = scope;
        this.parent = parent;
    }

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
        TokenType op = expr.operator().type();
        switch (op) {
            case ADD, SUB, MUL, DIV, MOD -> {
                var left = expr.left().accept(this);
                var right = expr.right().accept(this);
                if (op == ADD) {
                    return new Arithmetic(left, right, Operator.ADD);
                } else if (op == SUB) {
                    return new Arithmetic(left, right, Operator.SUBTRACT);
                } else if (op == MUL) {
                    return new Arithmetic(left, right, Operator.MULTIPLY);
                } else if (op == DIV) {
                    return new Arithmetic(left, right, Operator.DIVIDE);
                } else if (op == MOD) {
                    return new Arithmetic(left, right, Operator.MODULUS);
                } else {
                    System.out.println("not implemented");
                    return null;
                }
            }
            default -> {
            }
        }

        return null;
    }

    @Override
    public Expression visitGroupingExpr(Expr.Grouping expr) {
        return null;
    }

    @Override
    public Expression visitIdentifierExpr(Expr.Identifier expr) {
        var vr = new VariableReference(expr.identifier().source(), parent);

        return vr;
    }

    @Override
    public Expression visitLogicalExpr(Expr.Logical expr) {
        var left = expr.left().accept(this);
        var right = expr.left().accept(this);

        //Todo will be unnecessary after migration complete
        var op = Logical.LogicalOperator.AND;
        if (expr.comparison().type() == TokenType.OR) op = Logical.LogicalOperator.OR;
        return new Logical(left, right, op);
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
        List<Expression> elements = new ArrayList<>();
        for (var e : expr.entries()) {
            elements.add(e.accept(this));
        }
        return new ListLiteral(elements);
    }

    @Override
    public Expression visitPrefix(Expr.Prefix expr) {
        return null;
    }

    @Override
    public Expression visitCall(Expr.Call expr) {

        var castToIdentifier = (Expr.Identifier) expr.item(); // Todo: bad!

        // Function name
        String functionName = castToIdentifier.identifier().source();

        // Function argument expressions
        List<Expression> argExpressions = new ArrayList<>();
        for (var arg : expr.arguments()) {
            argExpressions.add(arg.accept(this));
        }


        // Function Call
        FunctionCall call = new FunctionCall(functionName, argExpressions, parent);

        return call;
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
        List<Expression> expressions = new ArrayList<>();

        var callContext = (Expr.Call) expr.call();
        var itemContext = (Expr.Identifier) callContext.item();
        var expressionContexts = callContext.arguments();
        for (var expCtx : expressionContexts) {
            Expression exp = expCtx.accept(this);
            expressions.add(exp);
        }

        var si = new StructInit(itemContext.identifier().source(), expressions, null);
        return si;
    }

    @Override
    public Expression visitPropertyAccess(Expr.PropertyAccess expr) {
        // Todo: major refactor needed, old code only supported identifier property access
        return null;
    }

    @Override
    public Expression visitRange(Expr.Range range) {
        return null;
    }

    @Override
    public Expression visitEmptyList(Expr.EmptyList emptyList) {
        return null;
    }

    @Override
    public Expression visitIndexAccess(Expr.IndexAccess expr) {
        return null;
    }

    @Override
    public Statement visit(Stmt stmt) {
        return null;
    }

    @Override
    public Statement visitBlockStmt(Stmt.Block stmt) {
        List<Statement> statements = new ArrayList<>();
        for (var statement : stmt.statements()) {
            Statement s = statement.accept(this);
            statements.add(s);
        }
        return new Block(statements, new Scope(/*Todo*/));
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

        List<Identifier> fields = new ArrayList<>();

        // At this point we do not know of any custom types that exist.
        for (var parameter : stmt.fields()) {
//            Type t = TypeResolver.getFromTypeContext(types.get(i), scope);
            String typeToken = parameter.type().source();
            Type t = new UnknownType(typeToken);


            String n = parameter.name().source();
            var field = new Identifier(n, t);
            fields.add(field);
        }
        String name = stmt.name().source();
        var id = new Identifier(name, BuiltInType.STRUCT);

        // Create struct type object
        StructType structType = new StructType(id, fields, parent, scope);

        // Create struct object
        Struct struct = new Struct(structType);

        // Add struct to scope
        scope.addType(name, structType);

        return struct;
    }

    @Override
    public Expression visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return stmt.expr().accept(this);
    }

    @Override
    public Statement visitFunctionStmt(Stmt.Function stmt) {
        /*
         * Function analysis plan:
         *
         * In the parser:
         * - Get name and parameters.
         * - Create Function instance.
         *
         * In the validator:
         * - Find (or create) a FunctionType that matches the name
         * - Add the Function to the FunctionType
         *
         * Todo: walk the tree of each block and infer the return type
         *
         */

        // Is the function exported? (nope, moved to a separate statement)
        Modifier modifier = Modifier.NONE;

        // The original name of the function
        String name = stmt.name().source();

        // Arguments of the function
        List<Identifier> parameters = new ArrayList<>();
        for (var parameter : stmt.parameters()) {
            parameters.add(new Identifier(parameter.name().source(), new UnknownType(parameter.type().source())));
        }

        // Block of the function
        Block block = new Block();
        if (stmt.body() != null) {
            var blockStatements = stmt.body().statements();
            List<Statement> statements = blockStatements.stream().map(this::validate).collect(Collectors.toList());
            block = new Block(statements, scope);
        }

        // Return type (or void)
        var typeContext = stmt.returnType();
        Type returnType = BuiltInType.VOID;
        if (typeContext != null) {
            returnType = new UnknownType(typeContext.source());
        }


        Function function = new Function(modifier, name, parameters, returnType, block, parent);
        return function;
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

        Mutability mutability = Mutability.Val;
        if (stmt.mutability().type() == TokenType.MUT) mutability = Mutability.Mut;

        String name = stmt.name().source();

        return new Declaration(mutability, name, expression);
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
