package org.imp.jvm.parsing.visitor;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.ImpFile2;
import org.imp.jvm.domain.root.ClassUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.domain.root.StaticUnit2;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.VariableDeclaration;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.parsing.visitor.statement.StatementVisitor;
import org.imp.jvm.statement.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImpFileVisitor extends ImpParserBaseVisitor<ImpFile2> {

    private FunctionSignature mainSignature = new FunctionSignature(
            "main",
            new ArrayList<>(),
            BuiltInType.VOID
    );

    @Override
    public ImpFile2 visitProgram(ImpParser.ProgramContext ctx) {
        // get all top level statements in the file
        List<ImpParser.StatementContext> statementContexts = ctx.statement();

        // Root Scope for Static Unit
        Scope staticScope = new Scope("static");


        // static unit for all non-class statements in the file
        var staticUnit = new StaticUnit2("static_unit");
        var main = new Function(mainSignature, new Block());
        Identifier varArgs = new Identifier();
        varArgs.type = BuiltInType.STRING_ARR;
        varArgs.name = "args";
        main.signature.parameters.add(varArgs);
        main.block.scope = staticScope;

        // create an ImpFile node with appropriate children
        var impFile = new ImpFile2(staticUnit);

        // handle each statement appropriately
        StatementVisitor statementVisitor = new StatementVisitor(staticScope);
        for (var statement : statementContexts) {
            Statement s = statement.accept(statementVisitor);
            System.out.println(s);


            // Split classes out to their own files
            if (false) {

            } else {
                // For everything else, add to the static class.
                if (s instanceof org.imp.jvm.statement.Declaration) {
                    Declaration declaration = (Declaration) s;

                    // Add variable signature to static unit
//                    staticUnit.properties.add(declaration);

                    // Initialize variable in static block
                    main.block.statements.add(declaration);
                } else if (s instanceof Function) {
                    Function f = (Function) s;

                    // add function to static class methods
                    staticUnit.functions.add(f);
                } else {
                    // All other root level nodes go in the main method
                    main.block.statements.add(s);
                }

            }


        }

        staticUnit.functions.add(main);
        var constructorSignature = new FunctionSignature("nullmain", Collections.emptyList(), BuiltInType.VOID);
        staticUnit.functions.add(new Constructor(constructorSignature, new Block()));
        System.out.println(staticUnit);

        // Visit the Static Unit
//        StaticUnitVisitor staticUnitVisitor = new StaticUnitVisitor();
//        StaticUnit staticUnit = staticUnitVisitor.

        // Visit each Class Unit

        return impFile;
    }

}
