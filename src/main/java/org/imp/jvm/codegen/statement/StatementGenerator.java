package org.imp.jvm.codegen.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.domain.expression.FunctionCall;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.*;
import org.imp.jvm.parser.visitor.statement.ReturnVisitor;
import org.objectweb.asm.MethodVisitor;

public class StatementGenerator {
    private final BlockGenerator blockGenerator;
    private final ExpressionGenerator expressionGenerator;
    //    private final FunctionGenerator functionGenerator;
    private final ReturnGenerator returnGenerator;
    private final IfGenerator ifGenerator;
    private final LoopGenerator loopGenerator;
    private final VariableDeclarationGenerator variableDeclarationGenerator = new VariableDeclarationGenerator(null, null);
//    private final AssignmentGenerator assignmentGenerator;
//    private final ImportGenerator importGenerator;
//    private final ExportGenerator exportGenerator;


    public StatementGenerator(MethodVisitor methodVisitor, Scope scope) {
        blockGenerator = new BlockGenerator(methodVisitor);
        expressionGenerator = new ExpressionGenerator(methodVisitor, scope);
//        functionGenerator = new FunctionGenerator()
        returnGenerator = new ReturnGenerator(expressionGenerator, methodVisitor);
        ifGenerator = new IfGenerator(this, expressionGenerator, methodVisitor);
        loopGenerator = new LoopGenerator(null, null, null);

    }

    public void generate(VariableDeclaration variableDeclaration) {
        variableDeclarationGenerator.generate(variableDeclaration);
    }

    public void generate(IfStatement ifStatement) {
        ifGenerator.generate(ifStatement);
    }

    public void generate(Block block) {
        blockGenerator.generate(block);
    }


    public void generate(Loop loop) {
        loopGenerator.generate(loop);
    }

    public void generate(FunctionCall functionCall) {
        functionCall.accept(expressionGenerator);
    }

    public void generate(ReturnStatement returnStatement) {
        returnGenerator.generate(returnStatement);
    }
}
