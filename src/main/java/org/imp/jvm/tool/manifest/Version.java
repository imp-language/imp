package org.imp.jvm.tool.manifest;

import java.net.URL;

/**
 * Semver-ish compliant parser
 *
 * @see URL https://semver.org/
 */
public record Version(int major, int minor, int patch, String tag) {
    public static Version from(String literal) {
        int minLength = 5;
        if (literal.length() < minLength) throw new Error("not valid");
        var versionAndTag = literal.split("[-+]", 2);
        var split = versionAndTag[0].split("\\.");
        System.out.println(literal);
        int maj = Integer.parseInt(split[0]);
        int min = Integer.parseInt(split[1]);
        int pat = Integer.parseInt(split[2]);

        return new Version(maj, min, pat, versionAndTag[1]);
    }
}
