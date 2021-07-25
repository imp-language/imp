package org.imp.jvm.expression.reference;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.SemanticErrors;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.types.FunctionType;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class VariableReference extends Expression {

    public Reference reference;
    public String name;

    public VariableReference(String name) {
        this.name = name;
        this.reference = null;
    }


    public VariableReference(LocalVariable localVariable) {
        this.type = localVariable.getType();
        this.name = localVariable.name;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        reference.generate(mv, scope);
    }

    @Override
    public void validate(Scope scope) {
        // Now we actually resolve the name to a variable.

        // First check the scope for local variables,
        if (scope.variableExists(name) /*&& !scope.getLocalVariable(name).closure*/) {
            this.reference = new LocalReference(scope.getLocalVariable(name));
        }
        // If that fails, look for function names,
        else if (scope.findFunctionType(name) != null) {
            this.reference = new FunctionReference(scope.findFunctionType(name));
        }
        // Todo: check the Java standard lib

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
            Logger.syntaxError(SemanticErrors.LocalVariableNotFound, getCtx());
            return;
        }

        reference.validate(scope);
        this.type = reference.type;

    }

}
