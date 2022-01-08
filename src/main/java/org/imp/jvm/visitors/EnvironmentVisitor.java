package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.tool.ExportTable;
import org.imp.jvm.types.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EnvironmentVisitor implements IVisitor<Optional<Type>> {

    public final Environment rootEnvironment;
    public final SourceFile source;
    final File file;
    public Environment currentEnvironment;

    public EnvironmentVisitor(Environment rootEnvironment, SourceFile source) {
        this.rootEnvironment = rootEnvironment;
        this.source = source;
        this.file = source.file;
        this.currentEnvironment = this.rootEnvironment;
    }


    @Override
    public Optional<Type> visit(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public Optional<Type> visitAssignExpr(Expr.Assign expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitBad(Expr.Bad expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitBinaryExpr(Expr.Binary expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitBlockStmt(Stmt.Block block) {
        for (var stmt : block.statements()) {
            stmt.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitCall(Expr.Call expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitEmptyList(Expr.EmptyList emptyList) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitEnum(Stmt.Enum stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitExport(Stmt.Export stmt) {
        stmt.stmt().accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
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
    public Optional<Type> visitFunctionStmt(Stmt.Function stmt) {
        String name = stmt.name().source();

        var childEnvironment = stmt.body().environment();
        childEnvironment.setParent(currentEnvironment);

        // Annotate parameters in the current scope
        List<Identifier> parameters = new ArrayList<>();
        for (var param : stmt.parameters()) {
            Type t = param.type().accept(this).get();
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
    public Optional<Type> visitGroupingExpr(Expr.Grouping expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitIdentifierExpr(Expr.Identifier expr) {
        return Optional.empty();
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
    public Optional<Type> visitImport(Stmt.Import stmt) {
        // Add all exports from the imported module into the current environment
        String basePath = source.basePath();
        String importName = stmt.stringLiteral().source();
        Path importPath = Path.of(basePath, importName);

        String alias = stmt.identifier().source();

        System.out.println(importPath);

        var results = ExportTable.getExportsFromSource(importPath);

        for (var result : results) {
            var typeName = alias + "." + result.name();
            switch (result.kind()) {
                case "struct" -> {
                    var st = (StructType) result.o();
                    st.name = typeName;
                    st.qualifiedName = result.qualifiedName();
                    this.currentEnvironment.addVariableOrError(typeName, st, file, stmt);
                }
                case "function" -> {
                    var funcType = (FuncType) result.o();
                    funcType.name = typeName;
                    this.currentEnvironment.addVariableOrError(typeName, funcType, file, stmt);
                }
                default -> {
                    System.err.println("Bad deserialization.");
                    System.exit(62);
                }
            }
        }

        // Todo:
        // It's impossible to query the MultiKeyMap for values that match one key instead
        // of both keys, so we need a different solution. Got to do the SQLite thing I
        // do believe.

//        var type

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitIndexAccess(Expr.IndexAccess expr) {
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
    public Optional<Type> visitLogicalExpr(Expr.Logical expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitNew(Expr.New expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitParameterStmt(Stmt.Parameter stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPostfixExpr(Expr.Postfix expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPrefix(Expr.Prefix expr) {
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
    public Optional<Type> visitStruct(Stmt.Struct stmt) {
        var fieldNames = new String[stmt.fields().size()];
        var fieldTypes = new Type[stmt.fields().size()];

        // At this point we do not know of any custom types that exist.
        for (int i = 0; i < fieldNames.length; i++) {
            var field = stmt.fields().get(i);
            fieldNames[i] = field.name().source();
            fieldTypes[i] = field.type().accept(this).get();
        }
        String name = stmt.name().source();

        // Create struct type object
        StructType structType = new StructType(name, fieldNames, fieldTypes);
        currentEnvironment.addVariableOrError(name, structType, file, stmt);

        return Optional.of(structType);
    }

    @Override
    public Optional<Type> visitType(Stmt.Type stmt) {
        Type type;
        // if no type.next exists, treat as normal type
        if (stmt.next().isEmpty()) {
            var bt = BuiltInType.getFromString(stmt.identifier().source());
            type = Objects.requireNonNullElseGet(bt, () -> new UnknownType(stmt.identifier().source()));
        }
        // if type.next exists, make an unknown type and pass to TypeCheckVisitor
        else {
            StringBuilder path = new StringBuilder(stmt.identifier().source());
            var ptr = stmt.next();
            while (ptr.isPresent()) {
                path.append(".").append(ptr.get().identifier().source());
                ptr = ptr.get().next();
            }
            type = Objects.requireNonNullElse(
                    currentEnvironment.getVariableTyped(path.toString(), StructType.class),
                    new UnknownType(path.toString())
            );
        }
        // Todo: above ^^
        return Optional.of(type);
    }

    @Override
    public Optional<Type> visitTypeAlias(Stmt.TypeAlias stmt) {
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
            generic = Objects.requireNonNullElseGet(bt, UnknownType::new);
            t = new ListType(generic);
        } else {
            t = new UnknownType();
        }
        currentEnvironment.addVariableOrError(stmt.name().source(), t, file, stmt);
        return Optional.empty();
    }
}
