package org.imp.jvm.statement;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.types.ExternalType;
import org.imp.jvm.types.StructType;
import org.objectweb.asm.MethodVisitor;

import java.util.Optional;

public class TypeAlias extends Statement {

    public final String name;
    public final String extern;

    public TypeAlias(String name, String extern) {

        this.name = name;
        this.extern = extern;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {

    }

    @Override
    public void validate(Scope scope) {
        /*
         * Steps to support Java interop:
         * 1. Find the extern class
         * 2. Add new type in scope
         * 3. Add all methods on class for valid interop
         */

        // Find the external class
        var c = getClass(extern);
        if (c.isEmpty()) {
            Logger.syntaxError(Errors.ExternNotFound, this, extern);
            return;
        }
        Class<?> foundClass = c.get();
        System.err.println("ree");

        // Add a new type to the scope
        ExternalType type = new ExternalType(foundClass);
        scope.addType(type);

        // Add all methods on the class to the scope
        var methods = foundClass.getMethods();


    }

    private Optional<Class<?>> getClass(String externalName) {
        try {
            Class<?> c = Class.forName(externalName);
            return Optional.of(c);
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
