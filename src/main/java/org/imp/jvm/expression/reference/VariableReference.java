package org.imp.jvm.expression.reference;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.runtime.Glue;
import org.objectweb.asm.MethodVisitor;

public class VariableReference extends Expression {

    public Reference reference;
    public final String name;
    public final ImpFile owner;

    public VariableReference(String name, ImpFile owner) {
        this.name = name;
        this.reference = null;
        this.owner = owner;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        reference.generate(mv, scope);
    }

    @Override
    public void validate(Scope scope) {
        // Now we actually resolve the name to a variable.

        // Check for module names- can't override imports
        if (Glue.coreModules.containsKey(name)) {
            this.reference = new ModuleReference(name);
        } else if (this.owner.qualifiedImports.size() > 0) {
            var a = this.owner.qualifiedImports.stream().filter(impFile -> impFile.getBaseName().equals(name)).findFirst();
            if (a.isPresent()) {
                this.reference = new ModuleReference(name);
            }
        }

        // Todo: look for Enums


        // First check the scope for local variables,
        if (scope.variableExists(name) /*&& !scope.getLocalVariable(name).closure*/) {
            this.reference = new LocalReference(scope.getLocalVariable(name));
        }
        // If that fails, look for function names,
        else if (scope.findFunctionType(name, false) != null) {
            this.reference = new FunctionReference(scope.findFunctionType(name, false));
        }
        // Todo: check the Java interop standard lib

        // Maybe the variable is a closure? Search closures in the function object
        else if (scope.getClosure(name) != null) {
            this.reference = scope.getClosure(name);
        }
        // If we can't find the variable *anywhere* make a closure in the outer scope
        else if (scope.parentScope != null && scope.parentScope.variableExists(name)) {
            // Mark this variable as a closed-over variable so we know to Box it everywhere else
            LocalVariable outerVariable = scope.parentScope.getLocalVariable(name);
            outerVariable.closure = true;
            var closure = new ClosureReference(outerVariable);
            scope.addClosure(closure);
            this.reference = closure;
        }

        if (reference == null) {
            Logger.syntaxError(Errors.LocalVariableNotFound, this, name);
//            Logger.killIfErrors("Missing variables.");
            return;
        }

        reference.validate(scope);
        this.type = reference.type;

    }

}
