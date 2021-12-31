package org.imp.jvm.tool;

import org.imp.jvm.tool.manifest.Version;

import java.util.List;
import java.util.Set;

public record Manifest(
        String name,
        Version version,
        String description,
        List<String> authors,
        String homepage,
        String docs,
        String repo,
        String license,
        Set<String> keywords,
        boolean publish,
        Version imp

) {
}
