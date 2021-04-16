package org.imp.jvm.domain;


import org.imp.jvm.domain.root.ClassUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.domain.root.StaticUnit2;

import java.util.ArrayList;
import java.util.List;

public class ImpFile2 {
    // Filename
    public String name;

    // Top-level entities in the source file.
    public final StaticUnit2 staticUnit;

    // All classes defined in the source file.
    public final List<ClassUnit> classUnits = new ArrayList<>();

    public ImpFile2(StaticUnit2 staticUnit, String name) {
        this.name = name;
//        this.name = FilenameUtils.getBaseName(file.getName());
        this.staticUnit = staticUnit;
    }

    public String getClassName() {
        return name;
    }
}
