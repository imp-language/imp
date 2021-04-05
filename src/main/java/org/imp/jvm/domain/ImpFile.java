package org.imp.jvm.domain;


import org.imp.jvm.domain.root.ClassUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImpFile {
    // Filename
    public String name;

    // Top-level entities in the source file.
    public final StaticUnit staticUnit;

    // All classes defined in the source file.
    public final List<ClassUnit> classUnits = new ArrayList<>();

    public ImpFile(StaticUnit staticUnit) {
//        this.name = FilenameUtils.getBaseName(file.getName());
        this.staticUnit = staticUnit;
    }

    public String getClassName() {
        return name;
    }
}
