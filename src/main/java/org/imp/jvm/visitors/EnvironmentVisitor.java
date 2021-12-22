package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnvironmentVisitor implements Stmt.Visitor<Optional<Type>> {

    public final Environment rootEnvironment;
    public Environment currentEnvironment;

    public EnvironmentVisitor(Environment rootEnvironment) {
        this.rootEnvironment = rootEnvironment;
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
        StructType structType = new StructType(id, fields);

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
        for (var param : stmt.parameters()) {
            var bt = BuiltInType.getFromToken(param.type().type());
            if (bt != null) {
                childEnvironment.addVariable(param.name().source(), bt);
            } else {
                childEnvironment.addVariable(param.name().source(), new UnknownType());
            }
        }


        var returnType = Optional.ofNullable(stmt.returnType());


        FunctionType functionType = new FunctionType(name, null, false);
        currentEnvironment.addVariable(name, functionType);

        currentEnvironment = childEnvironment;
        stmt.body().accept(this);

        currentEnvironment = currentEnvironment.getParent();
        return Optional.of(functionType);
    }

    @Override
    public Optional<Type> visitIf(Stmt.If stmt) {
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
            System.out.println("identifier " + v);
        } else {
            System.out.println("expr " + e);
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

        currentEnvironment.addVariable(stmt.name().source(), t);
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
}
