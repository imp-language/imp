package org.imp.jvm.parser.visitor;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.root.ClassUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.domain.statement.Statement;
import org.imp.jvm.parser.visitor.statement.StatementVisitor;

public class ImpFileVisitor extends ImpParserBaseVisitor<ImpFile> {
    @Override
    public ImpFile visitProgram(ImpParser.ProgramContext ctx) {
        // get all top level statements in the file
        var statementContexts = ctx.statement();


        // static unit for all non-class statements in the file
        var staticUnit = new StaticUnit("static unit");

        // create an ImpFile node with appropriate children
        var impFile = new ImpFile(staticUnit);

        // handle each statement appropriately
        StatementVisitor statementVisitor = new StatementVisitor();
        for (var statement : statementContexts) {
            Statement s = statement.accept(statementVisitor);
            System.out.println(s.getClass().getSimpleName());
            System.out.println(statement);

//            if (statement is class){
//                var classUnit = new ClassUnit();
//                impFile.classUnits.add(classUnit);
//            } else{
//                staticUnit.statements.add(statement);
//            }
        }

        // Visit the Static Unit
//        StaticUnitVisitor staticUnitVisitor = new StaticUnitVisitor();
//        StaticUnit staticUnit = staticUnitVisitor.

        // Visit each Class Unit

        return impFile;
    }

}
