package org.imp.jvm.codegen;

import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.statement.Function;
import org.imp.jvm.domain.types.Type;

import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//According to https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.3
public final class DescriptorFactory {

    public static String getMethodDescriptor(Function function) {
        List<Identifier> parameters = function.parameters;
        Type returnType = function.type;
        return getMethodDescriptor(parameters, returnType);
    }

    public static String getMethodDescriptor(FunctionSignature signature) {
        Collection<Identifier> parameters = signature.parameters;
        Type returnType = signature.type;
        return getMethodDescriptor(parameters, returnType);
    }

    //
    private static String getMethodDescriptor(Collection<Identifier> parameters, Type returnType) {
        String parametersDescriptor = parameters.stream()
                .map(parameter -> parameter.type.getDescriptor())
                .collect(Collectors.joining("", "(", ")"));
        String returnDescriptor = returnType.getDescriptor();
        return parametersDescriptor + returnDescriptor;
    }
}