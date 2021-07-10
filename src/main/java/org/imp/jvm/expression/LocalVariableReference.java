package org.imp.jvm.expression;

import org.antlr.v4.runtime.ParserRuleContext;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.SemanticErrors;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.TypeResolver;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class LocalVariableReference extends Expression {

    public LocalVariable localVariable;
    public FunctionType functionType;
    // Todo
    public String name;

    /**
     * Pass 0 constructor
     */
    public LocalVariableReference(String name) {
        this.name = name;
        localVariable = null;
        functionType = null;
    }


    public LocalVariableReference(LocalVariable localVariable) {
        this.type = localVariable.getType();
        this.localVariable = localVariable;
        this.functionType = null;
    }

    public LocalVariableReference(FunctionType functionType) {
        this.type = functionType;
        this.localVariable = null;
        this.functionType = functionType;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        if (localVariable != null) {
            String varName = localVariable.getName();
            int index = scope.getLocalVariableIndex(varName);

            this.type = localVariable.type; // Todo: this should be designed as to make this redundant. Both LocalVariable and LocalVariableReference shouldn't need type

            if (localVariable.closure) {
                mv.visitVarInsn(Opcodes.ALOAD, index);
            } else {
                mv.visitVarInsn(localVariable.type.getLoadVariableOpcode(), index);
            }
        } else {
            // LocalVariableReference to a first-class function
            String signature = functionType.toString();
            mv.visitLdcInsn(signature);
        }

//        BiConsumer<SemanticErrors, ParserRuleContext> ref = Logger::syntaxError;
//        var r = Logger::syntaxError;

    }

    @Override
    public void validate(Scope scope) {
        // Now we actually resolve the name to a variable.

        // First check the scope for local variables,
        LocalVariable local = scope.getLocalVariable(name);
        if (local != null) {
            this.localVariable = local;
            this.type = localVariable.type;
            return;
        }

        // If that fails, look for function names,
        FunctionType functionType = scope.findFunctionType(name);
        if (functionType != null) {
            this.functionType = functionType;
            this.type = functionType;
            return;
        }

        // If we can't find the variable make a closure in the outer scope
        var parentScope = scope.parentScope;
        if (parentScope != null && parentScope.variableExists(name)) {
            // Mark this variable as a closed-over variable so we know to Box it everywhere else
            LocalVariable outerVariable = parentScope.getLocalVariable(name);
            outerVariable.closure = true;

            this.localVariable = outerVariable;
            this.type = localVariable.type;

            return;
        }

        System.err.println("Bad!");
        System.exit(11);
    }

}
