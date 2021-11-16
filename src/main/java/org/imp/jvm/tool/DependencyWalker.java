package org.imp.jvm.tool;

import org.imp.jvm.domain.ImpFile;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.FileNotFoundException;

public class DependencyWalker {
    private final Graph<ImpFile, DefaultEdge> dependencies = new DefaultDirectedGraph<>(DefaultEdge.class);

    public Graph<ImpFile, DefaultEdge> walkDependencies(ImpFile entry) throws FileNotFoundException {
        dependencies.addVertex(entry);

        recurse(entry);

        return dependencies;

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
