package org.imp.jvm.domain;

import java.util.Arrays;

public enum Modifier {
    NONE(""),
    EXPORT("export");

    private final String modifier;

    Modifier(String modifier) {
        this.modifier = modifier;
    }


    public static Modifier fromString(String modifier) {
        return Arrays.stream(values()).filter(a -> a.modifier.equals(modifier))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sign not implemented"));
    }
}
