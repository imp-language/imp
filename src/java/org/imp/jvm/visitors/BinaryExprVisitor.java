package org.imp.jvm.visitors;

import org.imp.jvm.Constants;
import org.imp.jvm.parser.Expr;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ImpType;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

/**
 * Helper functions for CodegenVisitor.
 */
public class BinaryExprVisitor {

    public static void concatenateStrings(GeneratorAdapter ga, Expr.Binary expr, CodegenVisitor visitor) {
        ga.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        ga.visitInsn(Opcodes.DUP);
        ga.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", Constants.Init, "()V", false);

        expr.left.accept(visitor);

        String leftExprDescriptor = expr.left.realType.getDescriptor();
        String descriptor = "(" + leftExprDescriptor + ")Ljava/lang/StringBuilder;";
        ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false);

        expr.right.accept(visitor);

        String rightExprDescriptor = expr.right.realType.getDescriptor();
        descriptor = "(" + rightExprDescriptor + ")Ljava/lang/StringBuilder;";
        ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false);
        ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);

    }


    /**
     * Generates short-circuiting logical and behavior.
     * `expr.right` must never be executed if `expr.left` does not evaluate to true.
     */
    public static void logicalAnd(GeneratorAdapter ga, Expr.Binary expr, CodegenVisitor visitor) {
        Label falseLabel = new Label(); // short-circuit evaluation, if left is false, skip remaining clauses and return "false"
        Label successLabel = new Label(); // both clauses are truthy, return "true"

        expr.left.accept(visitor); // get the truthiness of the left expression
        ga.ifZCmp(GeneratorAdapter.EQ, falseLabel); // if first expression is false, return early

        expr.right.accept(visitor); // get the truthiness of the right expression
        ga.ifZCmp(GeneratorAdapter.EQ, falseLabel); // if second expression is false, return false
        ga.push(true); // success
        ga.goTo(successLabel); // skip to end

        // if either case failed, return false
        ga.mark(falseLabel);
        ga.push(false);

        // if both cases pass, return true
        ga.mark(successLabel);
    }

    /**
     * Generates standard logical or behavior.
     * `expr.right` should only be executed if `expr.left` is false.
     */
    public static void logicalOr(GeneratorAdapter ga, Expr.Binary expr, CodegenVisitor visitor) {
        Label falseLabel = new Label(); // short-circuit evaluation, if left is false, skip remaining clauses and return "false"
        Label successLabel = new Label(); // both clauses are truthy, return "true"
        Label endLabel = new Label();

        expr.left.accept(visitor); // get the truthiness of the left expression
        ga.ifZCmp(GeneratorAdapter.NE, successLabel); // if first expression is true, return true
        // else, fallthrough to second expression

        expr.right.accept(visitor); // get the truthiness of the right expression
        ga.ifZCmp(GeneratorAdapter.NE, successLabel); // if second expression is false, return false
        ga.goTo(falseLabel);

        ga.mark(successLabel);
        ga.push(true);
        ga.goTo(endLabel); // skip to end

        // if either case failed, return false
        ga.mark(falseLabel);
        ga.push(false);

        // if both cases pass, return true
        ga.mark(endLabel);
    }

    public static void relational(GeneratorAdapter ga, Expr.Binary expr, CodegenVisitor visitor) {
        // Currently, only primitive comparisons are implemented
        var left = expr.left;
        var right = expr.right;

        // Cast to the "bigger" type
        int lWide = BuiltInType.widenings.getOrDefault((BuiltInType) left.realType, -1);
        int rWide = BuiltInType.widenings.getOrDefault((BuiltInType) right.realType, -1);
        var lType = Type.getType(left.realType.getDescriptor());
        var rType = Type.getType(right.realType.getDescriptor());

        var cmpType = lType;

        if (lWide != -1 && rWide != -1) {
            if (lWide > rWide) {
                left.accept(visitor);
                right.accept(visitor);
                ga.cast(rType, lType);
            } else if (lWide < rWide) {
                left.accept(visitor);
                ga.cast(lType, rType);
                right.accept(visitor);
                cmpType = rType;
            } else {
                // now cast needed
                left.accept(visitor);
                right.accept(visitor);
            }
        } else {
            // Todo: better define the operator rules with overloads etc
            // here be booleans

            left.accept(visitor);
            right.accept(visitor);
        }

        Label endLabel = new Label();
        Label falseLabel = new Label();

        int opcode = switch (expr.operator.type()) {
            case EQUAL -> GeneratorAdapter.NE;
            case NOTEQUAL -> GeneratorAdapter.EQ;
            case LT -> GeneratorAdapter.GT;
            case GT -> GeneratorAdapter.LT;
            case LE -> GeneratorAdapter.GE;
            case GE -> GeneratorAdapter.LE;
            default -> throw new IllegalStateException("Unexpected value: " + expr.operator.type());
        };
        // if false, jump to falseLabel
        ga.ifCmp(cmpType, opcode, falseLabel);
        // else set true and jump to endLabel
        ga.push(true);
        ga.goTo(endLabel);

        ga.mark(falseLabel);
        ga.push(false);
        ga.mark(endLabel);
    }

    public static void arithmetic(GeneratorAdapter ga, Expr.Binary expr, CodegenVisitor visitor) {
        var left = expr.left;
        var right = expr.right;
        ImpType goalType = expr.realType;
        if (left.realType.equals(right.realType)) {
            expr.left.accept(visitor);
            expr.right.accept(visitor);
        } else {
            // the types don't match
            if (left.realType == BuiltInType.INT && right.realType == BuiltInType.FLOAT) {
                expr.left.accept(visitor);
                ga.visitInsn(Opcodes.I2F);
                expr.right.accept(visitor);
                goalType = BuiltInType.FLOAT;
            } else if (left.realType == BuiltInType.FLOAT && right.realType == BuiltInType.INT) {
                expr.left.accept(visitor);
                expr.right.accept(visitor);
                ga.visitInsn(Opcodes.I2F);
                goalType = BuiltInType.FLOAT;
            } else if (left.realType == BuiltInType.INT && right.realType == BuiltInType.DOUBLE) {
                expr.left.accept(visitor);
                ga.visitInsn(Opcodes.I2D);
                expr.right.accept(visitor);
                goalType = BuiltInType.DOUBLE;
            } else if (left.realType == BuiltInType.DOUBLE && right.realType == BuiltInType.INT) {
                expr.left.accept(visitor);
                expr.right.accept(visitor);
                ga.visitInsn(Opcodes.I2D);
                goalType = BuiltInType.DOUBLE;
            }

        }
        int op = switch (expr.operator.type()) {
            case ADD -> goalType.getAddOpcode();
            case SUB -> goalType.getSubtractOpcode();
            case MUL -> goalType.getMultiplyOpcode();
            case DIV -> goalType.getDivideOpcode();
            // Todo: Modulus implementation is more complicated than just a single opcode
            case MOD -> 0;
            case LT, GT, LE, GE -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + expr.operator.type());
        };
        ga.visitInsn(op);
    }
}
