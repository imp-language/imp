package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.types.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EnvironmentVisitor implements Stmt.Visitor<Optional<Type>>, Expr.Visitor<Optional<Type>> {

    public final Environment rootEnvironment;
    public Environment currentEnvironment;
    public final File file;

    public EnvironmentVisitor(Environment rootEnvironment, File file) {
        this.rootEnvironment = rootEnvironment;
        this.file = file;
        this.currentEnvironment = this.rootEnvironment;
    }


    @Override
    public Optional<Type> visit(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public Optional<Type> visitBlockStmt(Stmt.Block block) {
        for (var stmt : block.statements()) {
            stmt.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitExport(Stmt.Export stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitEnum(Stmt.Enum stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitStruct(Stmt.Struct stmt) {
        List<Identifier> fields = new ArrayList<>();

        // At this point we do not know of any custom types that exist.
        for (var field : stmt.fields()) {
            Type type = null;
            var bt = BuiltInType.getFromString(field.type().source());
            type = Objects.requireNonNullElseGet(bt, () -> new UnknownType(field.type().source()));

            String n = field.name().source();
            var f = new Identifier(n, type);
            fields.add(f);
        }
        String name = stmt.name().source();
        var id = new Identifier(name, BuiltInType.STRUCT);

        // Create struct type object
        StructType structType = new StructType(id, fields);
        currentEnvironment.addVariableOrError(name, structType, file, stmt);

        return Optional.of(structType);
    }

    @Override
    public Optional<Type> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitFunctionStmt(Stmt.Function stmt) {
        String name = stmt.name().source();


        var childEnvironment = stmt.body().environment();
        childEnvironment.setParent(currentEnvironment);

        // Annotate parameters in the current scope
        List<Identifier> parameters = new ArrayList<>();
        for (var param : stmt.parameters()) {
            var bt = BuiltInType.getFromString(param.type().source());
            Type t = Objects.requireNonNullElseGet(bt, () -> new UnknownType(param.type().source()));
            childEnvironment.addVariable(param.name().source(), t);
            parameters.add(new Identifier(param.name().source(), t));
        }

        var funcType = new FuncType(name, Modifier.NONE, parameters);
        if (stmt.returnType() != null) {
            var bt = BuiltInType.getFromString(stmt.returnType().source());
            funcType.returnType = Objects.requireNonNullElseGet(bt, () -> new UnknownType(stmt.returnType().source()));
        }
        currentEnvironment.addVariableOrError(name, funcType, file, stmt);


        currentEnvironment = childEnvironment;
        stmt.body().accept(this);

        currentEnvironment = currentEnvironment.getParent();
        return Optional.of(funcType);
    }

    @Override
    public Optional<Type> visitIf(Stmt.If stmt) {

        var childEnvironment = stmt.trueBlock().environment();
        childEnvironment.setParent(currentEnvironment);
        currentEnvironment = childEnvironment;
        stmt.trueBlock().accept(this);
        stmt.falseStmt().accept(this);

        // Todo: accept conditions and etc

        currentEnvironment = currentEnvironment.getParent();
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitReturnStmt(Stmt.Return stmt) {
        // Set the return type of the function to the type of the
        // expression you are returning.
        var e = stmt.expr();
        if (e instanceof Expr.Identifier identifier) {
            var name = identifier.identifier().source();
            var v = currentEnvironment.getVariable(name);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitVariable(Stmt.Variable stmt) {

        Type t;
        if (stmt.expr() instanceof Expr.Literal literal) {
            t = BuiltInType.getFromToken(literal.literal().type());
        } else if (stmt.expr() instanceof Expr.EmptyList emptyList) {
            Type generic = null;
            var bt = BuiltInType.getFromString(emptyList.type().source());
            if (bt != null) {
                generic = bt;
            } else {
                generic = new UnknownType();
            }
            t = new ListType(generic);
        } else {
            t = new UnknownType();
        }
        currentEnvironment.addVariableOrError(stmt.name().source(), t, file, stmt);
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitParameterStmt(Stmt.Parameter stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitFor(Stmt.For stmt) {

        var childEnvironment = stmt.block().environment();
        childEnvironment.setParent(currentEnvironment);

        currentEnvironment = childEnvironment;
        stmt.condition().accept(this);
        stmt.block().accept(this);
        currentEnvironment = currentEnvironment.getParent();

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitForInCondition(Stmt.ForInCondition stmt) {
        currentEnvironment.addVariable(stmt.name().source(), new UnknownType());
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitTypeAlias(Stmt.TypeAlias stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitAssignExpr(Expr.Assign expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitBinaryExpr(Expr.Binary expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitBad(Expr.Bad expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitCall(Expr.Call expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitGroupingExpr(Expr.Grouping expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitIdentifierExpr(Expr.Identifier expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitIndexAccess(Expr.IndexAccess expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitLogicalExpr(Expr.Logical expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitLiteralExpr(Expr.Literal expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitLiteralList(Expr.LiteralList expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitNew(Expr.New expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPrefix(Expr.Prefix expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPostfixExpr(Expr.Postfix expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPropertyAccess(Expr.PropertyAccess expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitRange(Expr.Range range) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitEmptyList(Expr.EmptyList emptyList) {
        return Optional.empty();
    }
}
