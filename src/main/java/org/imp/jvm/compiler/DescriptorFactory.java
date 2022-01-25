package org.imp.jvm.compiler;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.expression.Function;
import org.imp.jvm.types.Type;

import java.util.Collection;
import java.util.stream.Collectors;

//According to https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.3
public final class DescriptorFactory {


    public static String getMethodDescriptor(Function signature) {
        Collection<Identifier> parameters = signature.parameters;
        Type returnType = signature.returnType;
        return getMethodDescriptor(parameters, returnType);
    }

    //
    public static String getMethodDescriptor(Collection<Identifier> parameters, Type returnType) {
        String parametersDescriptor = parameters.stream()
                .map(parameter -> parameter.type.getDescriptor())
                .collect(Collectors.joining("", "(", ")"));
        String returnDescriptor = returnType.getDescriptor();
        return parametersDescriptor + returnDescriptor;
    }

    public static String getDescriptor(Collection<Type> types, Type returnType) {
        String parametersDescriptor = types.stream()
                .map(Type::getDescriptor)
                .collect(Collectors.joining("", "(", ")"));
        String returnDescriptor = returnType.getDescriptor();
        return parametersDescriptor + returnDescriptor;
    }
}