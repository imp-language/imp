package org.imp.jvm;

import org.apache.commons.collections4.map.LinkedMap;
import org.imp.jvm.types.Type;

public class NewScope {
    private final LinkedMap<String, Type> types;

    public NewScope() {
        this.types = new LinkedMap<>();
    }

}
