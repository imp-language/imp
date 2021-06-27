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

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class LocalVariableReference extends Expression {

    public final LocalVariable localVariable;
    public final FunctionType functionType;
    // Todo


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
            if (localVariable.name.equals("p")) { // Todo: this is AWFUL
//                index = 0;
            }
//            index++;
            this.type = localVariable.type; // Todo: this should be designed as to make this redundant. Both LocalVariable and LocalVariableReference shouldn't need type
            mv.visitVarInsn(localVariable.type.getLoadVariableOpcode(), index);
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
        if (localVariable != null) {
            this.type = localVariable.type;
        } else {
            this.type = functionType;
        }
    }

}
