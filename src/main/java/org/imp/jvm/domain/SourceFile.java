package org.imp.jvm.domain;

import org.imp.jvm.Stmt;

import java.io.File;
import java.util.List;

public class SourceFile {
    public final File file;
    public final List<Stmt> stmts;

    public SourceFile(File file, List<Stmt> stmts) {

        this.file = file;
        this.stmts = stmts;
    }

    @Override
    public String toString() {
        return file.getName();
    }
}
