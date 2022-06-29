package org.imp.jvm.tool.manifest;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Describes an Imp program's dependencies and settings.
 */
public record Manifest(
		String name,
		String version,
		String entry,
		String description,
		List<String> authors,
		String homepage,
		String docs,
		String repo,
		String license,
		Set<String> keywords,
		boolean publish,
		String imp,
		String runtime

) {
	static final String manifestPath = "imp.toml";

	/**
	 * Used by Imp CLI in a similar manner to `npm init`
	 * Example: mapper.writeValue(System.out, Manifest.create("myapp", "0.1.7", "ree.imp"));
	 *
	 * @return a new Manifest object
	 */
	public static Manifest create(String name, String version, String entry) {
		return new Manifest(name, version, entry,
				"", new ArrayList<>(),
				"", "", "",
				"", new HashSet<>(),
				false, "", "");
	}

	/**
	 * Get the current project's manifest file
	 *
	 * @return a Manifest object based on the current working directory.
	 */
	public static Manifest get() throws FileNotFoundException {
		TomlMapper mapper = new TomlMapper();
		String pwd = System.getProperty("user.dir");
		Path p = Path.of(pwd, manifestPath);
		try {

			return mapper.readValue(Files.readString(p), Manifest.class);
		} catch (IOException e) {
			throw new FileNotFoundException("Manifest not found.");
		}
	}

	public record Dependency(
	) {

	}
}
