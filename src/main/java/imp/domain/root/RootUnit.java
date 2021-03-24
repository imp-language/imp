package main.java.imp.domain.root;

/**
 * An Imp file is split into several root units,
 * one for all global content, and one for each class
 */
public abstract class RootUnit {
    public final String name;

    public RootUnit(String name) {
        this.name = name;
    }
}
