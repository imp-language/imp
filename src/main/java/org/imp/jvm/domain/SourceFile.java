package org.imp.jvm.domain;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Environment;
import org.imp.jvm.Stmt;
import org.imp.jvm.types.Type;
import org.imp.jvm.visitors.IVisitor;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class SourceFile {
    public final File file;
    public final List<Stmt> stmts;
    public final LinkedMap<String, Type> exports = new LinkedMap<>();
    public final Environment rootEnvironment = new Environment();
    // filename -> SourceFile
    private final LinkedMap<String, SourceFile> imports = new LinkedMap<>();

    public SourceFile(File file, List<Stmt> stmts) {

        this.file = file;
        this.stmts = stmts;
    }

    public <R> void acceptVisitor(IVisitor<R> visitor) {
        for (var s : this.stmts) {
            s.accept(visitor);
        }
    }

    public void addImport(File file, SourceFile sourceFile) {
        String s = FilenameUtils.separatorsToUnix(file.getPath());
        s = FilenameUtils.removeExtension(s);
        imports.put(s, sourceFile);
    }

    public String basePath() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.getPath(file.getPath()));
    }

    public <T extends Stmt, R> void filter(Class<T> kind, Function<T, R> function) {
        for (var s : stmts) {
            if (kind.isInstance(s)) {
                function.apply(kind.cast(s));
            }
        }
    }

    public SourceFile getImport(String filepath) {
        return imports.get(filepath);
    }

    public LinkedMap<String, SourceFile> getImports() {
        return imports;
    }

    public String name() {
        return FilenameUtils.removeExtension(file.getName());
    }

    public String path() {
        return FilenameUtils.separatorsToUnix(Path.of(basePath(), name()).toString());
    }

    @Override
    public String toString() {
        return file.getName();
    }
}
