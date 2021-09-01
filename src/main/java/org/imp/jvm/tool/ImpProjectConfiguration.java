package org.imp.jvm.tool;

public class ImpProjectConfiguration {
    public static final int NAME_SIZE = 128;
    public static final int DESCRIPTION_SIZE = 256;

    public final String name;
    public final String version;
    public final String entry;

    public ImpProjectConfiguration(String name, String version, String entry) {
        this.name = name;
        this.version = version;
        this.entry = entry;
    }

    public boolean isValid() {
        if (name.length() > NAME_SIZE) return false;


        return true;
    }

    @Override
    public String toString() {
        String s = "[package]";
        s += "\nname = \"" + name + "\"";
        s += "\nversion = \"" + version + "\"";
        s += "\nentry = \"" + entry + "\"";

        return s;
    }
}
