package org.imp.jvm.domain;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Environment;
import org.imp.jvm.Stmt;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.types.ImpType;
import org.imp.jvm.visitors.IVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SourceFile {
    public final File file;
    public final List<Stmt> stmts;
    public final LinkedMap<String, ImpType> exports = new LinkedMap<>();
    public final Environment rootEnvironment = new Environment();
    /**
     * root directory, where the imp.toml manifest file resides
     */
    public final String projectRoot;

    /**
     * parent path, the direct parent folder of file, used for relative imports
     */
    public final String relativePath;

    /**
     * name of the file
     */
    public final String name;

    // filename -> SourceFile
    private final LinkedMap<String, SourceFile> imports = new LinkedMap<>();


    public SourceFile(String projectRoot, String relativePath, String name) throws FileNotFoundException {
        /**
         * We need-
         * 1. project root directory
         * 2. file name
         * 3. relative path
         * Then total path to file is <project root directory> + <relative path> + <file name>
         */

        this.projectRoot = FilenameUtils.separatorsToUnix(projectRoot);
        this.relativePath = FilenameUtils.separatorsToUnix(relativePath);
        this.name = FilenameUtils.removeExtension(FilenameUtils.removeExtension(name));

        String fullPath = FilenameUtils.separatorsToUnix(Path.of(projectRoot, relativePath, this.name + ".imp").toString());
        this.file = new File(fullPath);

        Tokenizer tokenizer = new Tokenizer(file);
        var parser = new org.imp.jvm.parser.Parser(tokenizer);
        this.stmts = parser.parse();
    }

    public <R> List<R> acceptVisitor(IVisitor<R> visitor) {
        List<R> results = new ArrayList<>();
        for (var s : this.stmts) {
            var r = s.accept(visitor);
            results.add(r);
        }
        return results;
    }

    public void addImport(File file, SourceFile sourceFile) {
        String s = FilenameUtils.separatorsToUnix(file.getPath());
        s = FilenameUtils.removeExtension(s);
        imports.put(s, sourceFile);
    }

//    @Deprecated
//    public String base() {
//        return relativePath;
//    }

//    @Deprecated
//    public String basePath() {
//        return FilenameUtils.separatorsToUnix(file.getPath());
//    }

    public <T extends Stmt, R> void filter(Class<T> kind, Function<T, R> function) {
        for (var s : stmts) {
            if (kind.isInstance(s)) {
                function.apply(kind.cast(s));
            }
        }
    }

    public String getFullRelativePath() {
        return FilenameUtils.separatorsToUnix(Path.of(relativePath, name).toString());
    }

    public SourceFile getImport(String filepath) {
        return imports.get(filepath);
    }

//    public String name() {
//        return FilenameUtils.removeExtension(file.getName());
//    }

//    @Deprecated
//    public String path() {
//        return FilenameUtils.separatorsToUnix(Path.of(basePath(), name()).toString());
//    }

    public LinkedMap<String, SourceFile> getImports() {
        return imports;
    }

    @Override
    public String toString() {
        return file.getName();
    }
}
