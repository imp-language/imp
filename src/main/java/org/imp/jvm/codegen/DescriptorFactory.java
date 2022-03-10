package org.imp.jvm.codegen;

import org.imp.jvm.legacy.domain.scope.Identifier;
import org.imp.jvm.types.ImpType;

import java.util.Collection;
import java.util.stream.Collectors;

//According to https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.3
public final class DescriptorFactory {


    //
    public static String getMethodDescriptor(Collection<Identifier> parameters, ImpType returnType) {
        String parametersDescriptor = parameters.stream()
                .map(parameter -> parameter.type.getDescriptor())
                .collect(Collectors.joining("", "(", ")"));
        String returnDescriptor = returnType.getDescriptor();
        return parametersDescriptor + returnDescriptor;
    }

    public static String getDescriptor(Collection<ImpType> types, ImpType returnType) {
        String parametersDescriptor = types.stream()
                .map(ImpType::getDescriptor)
                .collect(Collectors.joining("", "(", ")"));
        String returnDescriptor = returnType.getDescriptor();
        return parametersDescriptor + returnDescriptor;
    }
}