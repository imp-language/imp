package org.imp.jvm.domain;

import org.imp.jvm.types.ImpType;

import java.io.Serializable;


/**
 * Todo: this should be a record
 */
public class Identifier implements Serializable {
    public final String name;
    public ImpType type;

    public Identifier(String name, ImpType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String toString() {
        return name + " " + type.getName();
    }


}