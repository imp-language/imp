package org.imp.jvm.domain.types;

import org.imp.jvm.ImpParser;

import java.util.Arrays;
import java.util.Optional;

public class TypeResolver {
    public static Type getFromTypeName(ImpParser.TypeContext typeContext) {
        if (typeContext.primitiveType() != null) {
            Optional<BuiltInType> builtInType = getBuiltInType(typeContext.primitiveType().getText());
            if (builtInType.isPresent()) {
                return builtInType.get();
            } else {
                return new ClassType(typeContext.getText());
            }
        }
        return null;
    }

    private static Optional<BuiltInType> getBuiltInType(String typeName) {
        return Arrays.stream(BuiltInType.values())
                .filter(type -> type.getName().equals(typeName))
                .findFirst();
    }
}
