package org.imp.jvm.tool;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.SourceFile;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.FileNotFoundException;

public class DependencyWalker {
    private final Graph<ImpFile, DefaultEdge> dependencies = new DefaultDirectedGraph<>(DefaultEdge.class);
    private final Graph<SourceFile, DefaultEdge> dependencies2 = new DefaultDirectedGraph<>(DefaultEdge.class);

    public Graph<ImpFile, DefaultEdge> walkDependencies(ImpFile entry) throws FileNotFoundException {
        dependencies.addVertex(entry);
        recurse(entry);
        return dependencies;

    }

    public Graph<SourceFile, DefaultEdge> walkDependencies(SourceFile entry) throws FileNotFoundException {
        dependencies2.addVertex(entry);
        recurse(entry);
        return dependencies2;

    }

    private void recurse(SourceFile file) throws FileNotFoundException {
        var imports = API.gatherImports(file);

        for (var impFile : imports.values()) {
            dependencies2.addVertex(impFile);
            dependencies2.addEdge(file, impFile);

            recurse(impFile);
        }
    }

    private void recurse(ImpFile file) throws FileNotFoundException {
        var imports = API.gatherImports(file);

        for (var impFile : imports.values()) {
            dependencies.addVertex(impFile);
            dependencies.addEdge(file, impFile);

            recurse(impFile);
        }
    }

}
