package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
import org.imp.jvm.SourceFile;
import org.imp.jvm.Util;
import org.imp.jvm.domain.Identifier;
import org.imp.jvm.domain.Modifier;
import org.imp.jvm.domain.Mutability;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Expr;
import org.imp.jvm.parser.ReservedWords;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.parser.tokenizer.TokenType;
import org.imp.jvm.runtimeHelp.Glue;
import org.imp.jvm.tool.ExportTable;
import org.imp.jvm.types.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EnvironmentVisitor implements IVisitor<Optional<ImpType>> {

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
    public Optional<ImpType> visit(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public Optional<ImpType> visitAssignExpr(Expr.Assign expr) {
        expr.left.accept(this);
        expr.right.accept(this);

        if (expr.left instanceof Expr.Identifier identifier) {
            var mut = currentEnvironment.getVariableMutability(identifier.identifier.source());
            if (mut == Mutability.Val) {
                Comptime.MutabilityError.submit(file, identifier, identifier.identifier.source());
            }

        } else {
            Comptime.Implementation.submit(file, expr, "Assignment not implemented for any recipient but identifier yet");
        }

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitBad(Expr.Bad expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitBinaryExpr(Expr.Binary expr) {

        expr.left.accept(this);
        expr.right.accept(this);

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitBlockStmt(Stmt.Block block) {
        for (var stmt : block.statements) {
            stmt.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitCall(Expr.Call expr) {

        for (var arg : expr.arguments) {
            arg.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitEmpty(Expr.Empty empty) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitEmptyList(Expr.EmptyList emptyList) {
        var bt = BuiltInType.getFromString(emptyList.tokenType.source());
        var lt = new ListType(bt);
        emptyList.realType = lt;
        return Optional.of(lt);
    }

    @Override
    public Optional<ImpType> visitEnum(Stmt.Enum stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitExport(Stmt.Export stmt) {
        stmt.stmt.accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return stmt.expr.accept(this);
    }

    @Override
    public Optional<ImpType> visitFor(Stmt.For stmt) {
        var childEnvironment = stmt.block.environment;
        childEnvironment.setParent(currentEnvironment);

        currentEnvironment = childEnvironment;

        stmt.expr.accept(this);
        // visit expression (could be an iterator or a list)
//        var b = stmt.expr.accept(this);
//        if (true /*for now assume it's an iterator every time*/) {
//            System.out.println(b);
//        }

        // add variable for iterator in child block scope
        currentEnvironment.addVariable(stmt.name.source(), new UnknownType());

        // visit block
        stmt.block.accept(this);

        // reset environment pointer
        currentEnvironment = currentEnvironment.getParent();

        return Optional.empty();
    }


    @Override
    public Optional<ImpType> visitFunctionStmt(Stmt.Function stmt) {
        String name = stmt.name.source();

        var childEnvironment = stmt.body.environment;
        childEnvironment.setParent(currentEnvironment);

        // Annotate parameters in the current scope
        List<Identifier> parameters = new ArrayList<>();
        for (var param : stmt.parameters) {
            ImpType t = param.type.accept(this).get();
            childEnvironment.addVariable(param.name.source(), t);
            parameters.add(new Identifier(param.name.source(), t));
        }

        var funcType = new FuncType(name, Modifier.NONE, parameters);
        if (stmt.returnType != null) {
            var bt = BuiltInType.getFromString(stmt.returnType.source());
            funcType.returnType = Objects.requireNonNullElseGet(bt, () -> new UnknownType(stmt.returnType.source()));
        }
        currentEnvironment.addVariableOrError(name, funcType, file, stmt);

        currentEnvironment = childEnvironment;
        stmt.body.accept(this);

        currentEnvironment = currentEnvironment.getParent();
        return Optional.of(funcType);
    }

    @Override
    public Optional<ImpType> visitGroupingExpr(Expr.Grouping expr) {
        expr.expr.accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitIdentifierExpr(Expr.Identifier expr) {
        return Optional.ofNullable(currentEnvironment.getVariable(expr.identifier.source()));
    }

    @Override
    public Optional<ImpType> visitIf(Stmt.If stmt) {

        var childEnvironment = stmt.trueBlock.environment;
        childEnvironment.setParent(currentEnvironment);
        currentEnvironment = childEnvironment;
        stmt.condition.accept(this);
        stmt.trueBlock.accept(this);
        if (stmt.falseStmt != null) stmt.falseStmt.accept(this);

        currentEnvironment = currentEnvironment.getParent();
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitImport(Stmt.Import stmt) {
        // Add all exports from the imported module into the current environment
        String requestedImport = stmt.stringLiteral.source();
        Path importPath = Path.of(source.projectRoot, source.relativePath, requestedImport + ".imp");

        if (Glue.coreModules.containsKey(requestedImport)) {
            // Look in Glue
            var ree = Glue.getExports(requestedImport);
            for (var ft : ree) {
                var typeName = ft.name;
                this.currentEnvironment.addVariableOrError(typeName, ft, file, stmt);
            }

        } else {

            var results = ExportTable.getExportsFromSource(importPath);

            String alias = "";
            if (stmt.identifier.isPresent()) {
                alias = stmt.identifier.get().source() + ".";
            }
            for (var result : results) {
                var typeName = result.name();
                if (stmt.identifier.isPresent()) {
                    typeName = alias + result.name();
                }
                switch (result.kind()) {
                    case "struct" -> {
                        var st = (StructType) result.o();
                        st.name = typeName;
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
        }

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitIndexAccess(Expr.IndexAccess expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitLiteralExpr(Expr.Literal expr) {
        var t = BuiltInType.getFromToken(expr.literal.type());
        if (t == null) {
            Comptime.Implementation.submit(file, expr, "This should never happen. All literals should be builtin, for now.");
        } else {
            expr.realType = t;
        }
        return Optional.ofNullable(t);
    }

    @Override
    public Optional<ImpType> visitLiteralList(Expr.LiteralList expr) {
        if (expr.entries.size() == 0) {
            Comptime.ListLiteralIncomplete.submit(file, expr);
        }

        for (var entry : expr.entries) {
            entry.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitLogicalExpr(Expr.Logical expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitNew(Expr.New expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitParameterStmt(Stmt.Parameter stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitPostfixExpr(Expr.Postfix expr) {
        expr.expr.accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitPrefix(Expr.Prefix expr) {
        expr.right.accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitPropertyAccess(Expr.PropertyAccess expr) {
        var exprs = expr.exprs;
        var start = exprs.get(0);
        var startType = start.accept(this);
        if (startType.isPresent()) {
            if (startType.get() instanceof ExternalType externalType) {
                // Todo: resolve the external static method call
                /*
                 * Notes:
                 * use f = c.getDeclaredField(String) to get a Field like "out" here
                 * then do fieldType = f.type to get the type of said field
                 * method = fieldType.getMethod("println", ...)
                 *
                 * All in all, we *replace* the PropertyAccess expression somehow
                 * with a note for the next steps to treat the entire expr as an
                 * external method call. Our job is to decide what method to call.
                 */
                try {
                    var c = externalType.foundClass();

                    var id = ((Expr.Identifier) exprs.get(1)).identifier.source();

                    var f = c.getDeclaredField(id);
                    var fieldType = f.getType();

                    var expr2 = exprs.get(2);
                    if (expr2 instanceof Expr.Call call) {
                        var funcName = ((Expr.Identifier) call.item).identifier.source();

//                    var m = fieldType.getMethod("println", types);
                    }


                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitRange(Expr.Range range) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitReturnStmt(Stmt.Return stmt) {
        // Set the return type of the function to the type of the
        // expression you are returning.
        stmt.expr.accept(this);
        var e = stmt.expr;
        if (e instanceof Expr.Identifier identifier) {
            var name = identifier.identifier.source();
            var v = currentEnvironment.getVariable(name);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitStruct(Stmt.Struct stmt) {

        var fieldNames = new String[stmt.fields.size()];
        var fieldTypes = new ImpType[stmt.fields.size()];

        // At this point we do not know of any custom types that exist.
        List<Identifier> parameters = new ArrayList<>();
        for (int i = 0; i < fieldNames.length; i++) {
            var field = stmt.fields.get(i);
            fieldNames[i] = field.name.source();
            if (ReservedWords.isReserved(fieldNames[i])) {
                Comptime.ReservedWord.submit(file, stmt.fields.get(i), fieldNames[i]);
            }
            fieldTypes[i] = field.type.accept(this).get();
            parameters.add(new Identifier(fieldNames[i], fieldTypes[i]));
        }
        String name = stmt.name.source();

        // Create struct type object
        StructType structType = new StructType(name, fieldNames, fieldTypes);
        structType.qualifiedName = source.getFullRelativePath() + "$" + name;
        structType.parentName = source.getFullRelativePath();
        currentEnvironment.addVariableOrError(name, structType, file, stmt);

        return Optional.of(structType);
    }

    @Override
    public Optional<ImpType> visitType(Stmt.Type stmt) {
        ImpType type;
        // if no type.next exists, treat as normal type
        if (stmt.next.isEmpty()) {
            var bt = BuiltInType.getFromString(stmt.identifier.source());
            type = Objects.requireNonNullElseGet(bt, () -> new UnknownType(stmt.identifier.source()));
            if (stmt.listType) {
                type = new ListType(type);
            }
        }
        // if type.next exists, make an unknown type and pass to TypeCheckVisitor
        else {
            StringBuilder path = new StringBuilder(stmt.identifier.source());
            var ptr = stmt.next;
            while (ptr.isPresent()) {
                path.append(".").append(ptr.get().identifier.source());
                ptr = ptr.get().next;
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
    public Optional<ImpType> visitTypeAlias(Stmt.TypeAlias stmt) {
        String identifier = stmt.identifier();
        String extern = stmt.literal.literal.source();

        var c = Util.getClass(extern);
        if (c.isEmpty()) {
            Comptime.ExternNotFound.submit(file, stmt.literal, extern);
            return Optional.empty();
        }
        Class<?> foundClass = c.get();

        // Add a new type to the scope
        ExternalType type = new ExternalType(foundClass);
        currentEnvironment.addVariable(identifier, type);

        return Optional.of(type);
    }

    @Override
    public Optional<ImpType> visitVariable(Stmt.Variable stmt) {

        var t = stmt.expr.accept(this);

        ImpType type;
        type = t.orElseGet(UnknownType::new);

        var mut = Mutability.Val;
        if (stmt.mutability.type() == TokenType.MUT) {
            mut = Mutability.Mut;
        }

        currentEnvironment.addVariableOrError(stmt.name.source(), type, file, stmt);
        currentEnvironment.setVariableMutability(stmt.name.source(), mut);
        return Optional.empty();
    }
}
