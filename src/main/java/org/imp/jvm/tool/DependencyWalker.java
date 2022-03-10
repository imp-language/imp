package org.imp.jvm.tool;

import org.imp.jvm.SourceFile;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DependencyWalker {
    private final Graph<SourceFile, DefaultEdge> dependencies2 = new DefaultDirectedGraph<>(DefaultEdge.class);

    public Graph<SourceFile, DefaultEdge> walkDependencies(SourceFile entry) {
        dependencies2.addVertex(entry);
        recurse(entry);
        return dependencies2;

    }


    private void recurse(SourceFile file) {
        var imports = API.gatherImports(file);

        for (var impFile : imports.values()) {
            dependencies2.addVertex(impFile);
            dependencies2.addEdge(file, impFile);

            recurse(impFile);
        }
    }

}
