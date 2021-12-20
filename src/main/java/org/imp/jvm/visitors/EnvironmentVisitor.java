package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
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
    public Optional<Type> visitBlockStmt(Stmt.Block stmt) {
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
            childEnvironment.addVariable(param.name().source(), new UnknownType());
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
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitVariable(Stmt.Variable stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitParameterStmt(Stmt.Parameter stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitFor(Stmt.For stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitForInCondition(Stmt.ForInCondition stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitTypeAlias(Stmt.TypeAlias stmt) {
        return Optional.empty();
    }
}
