package org.imp.jvm.visitors;

import org.imp.jvm.Constants;
import org.imp.jvm.Util;
import org.imp.jvm.parser.Expr;
import org.imp.jvm.parser.tokenizer.Token;
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
    public static void logicalAnd(GeneratorAdapter ga, Expr left, Expr right, CodegenVisitor visitor) {
        Label falseLabel = new Label(); // short-circuit evaluation, if left is false, skip remaining clauses and return "false"
        Label successLabel = new Label(); // both clauses are truthy, return "true"

        left.accept(visitor); // get the truthiness of the left expression
        ga.ifZCmp(GeneratorAdapter.EQ, falseLabel); // if first expression is false, return early

        right.accept(visitor); // get the truthiness of the right expression
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
     * Generates short-circuiting logical or behavior.
     * `expr.right` should only be executed if `expr.left` is false.
     */
    public static void logicalOr(GeneratorAdapter ga, Expr left, Expr right, CodegenVisitor visitor) {
        Label falseLabel = new Label(); // short-circuit evaluation, if left is false, skip remaining clauses and return "false"
        Label successLabel = new Label(); // both clauses are truthy, return "true"
        Label endLabel = new Label();

        left.accept(visitor); // get the truthiness of the left expression
        ga.ifZCmp(GeneratorAdapter.NE, successLabel); // if first expression is true, return true
        // else, fallthrough to second expression

        right.accept(visitor); // get the truthiness of the right expression
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

    /**
     * Generates standard logical xor behaviour
     */
    public static void logicalXor(GeneratorAdapter ga, Expr left, Expr right, CodegenVisitor visitor) {
        left.accept(visitor);
        right.accept(visitor);
        ga.visitInsn(Opcodes.IXOR);     //apply XOR operation to left and right expressions
    }

    private static Type castAndAccept(GeneratorAdapter ga, Expr left, Expr right, CodegenVisitor visitor) {
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
            left.accept(visitor);
            right.accept(visitor);
        }
        return cmpType;
    }

    public static void relational(GeneratorAdapter ga, Expr left, Expr right, Token operator, CodegenVisitor visitor) {
        // Currently, only primitive comparisons are implemented
        var cmpType = castAndAccept(ga, left, right, visitor);

        Label endLabel = new Label();
        Label falseLabel = new Label();

        int opcode = switch (operator.type()) {
            case EQUAL -> GeneratorAdapter.NE;
            case NOTEQUAL -> GeneratorAdapter.EQ;
            case LT -> GeneratorAdapter.GT;
            case GT -> GeneratorAdapter.LT;
            case LE -> GeneratorAdapter.GE;
            case GE -> GeneratorAdapter.LE;
            default -> throw new IllegalStateException("Unexpected value: " + operator.type());
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

    /**
     * Applies the modulo operator to two expressions. Only primitive types supported.
     */
    public static void modulus(GeneratorAdapter ga, Expr left, Expr right, CodegenVisitor visitor) {
        var cmpType = castAndAccept(ga, left, right, visitor);

        if (cmpType == Type.DOUBLE_TYPE) {           //second, switch between which type is being used, then apply
            ga.visitInsn(Opcodes.DREM);             //the modulus operation between the two expressions depending on
        } else if (cmpType == Type.INT_TYPE) {       //the type that was converted to
            ga.visitInsn(Opcodes.IREM);
        } else if (cmpType == Type.FLOAT_TYPE) {
            ga.visitInsn(Opcodes.FREM);
        }
    }

    public static void exponents(GeneratorAdapter ga, Expr left, Expr right, CodegenVisitor visitor) {
        //Todo: Exponent handling

        //First, we need to convert any data types, so that the 2 types used are the same
    }

    /**
     * Perform arithmetic expression. Codegen of left and right sides must happen in
     * different orders according to cast rules. Push both sides to the stack, casting
     * when necessary, then insert the add/subtract/multiply/divide opcode.
     */
    public static void arithmetic(GeneratorAdapter ga, Expr left, Expr right, Token operator, ImpType goalType, CodegenVisitor visitor) {
        if (left.realType.equals(right.realType)) {
            left.accept(visitor);
            right.accept(visitor);
        } else {
            // the types don't match
            if (left.realType == BuiltInType.INT && right.realType == BuiltInType.FLOAT) {
                left.accept(visitor);
                ga.visitInsn(Opcodes.I2F);
                right.accept(visitor);
                goalType = BuiltInType.FLOAT;
            } else if (left.realType == BuiltInType.FLOAT && right.realType == BuiltInType.INT) {
                left.accept(visitor);
                right.accept(visitor);
                ga.visitInsn(Opcodes.I2F);
                goalType = BuiltInType.FLOAT;
            } else if (left.realType == BuiltInType.INT && right.realType == BuiltInType.DOUBLE) {
                left.accept(visitor);
                ga.visitInsn(Opcodes.I2D);
                right.accept(visitor);
                goalType = BuiltInType.DOUBLE;
            } else if (left.realType == BuiltInType.DOUBLE && right.realType == BuiltInType.INT) {
                left.accept(visitor);
                right.accept(visitor);
                ga.visitInsn(Opcodes.I2D);
                goalType = BuiltInType.DOUBLE; // Fixme: shouldn't have to assign this here
            }

        }
        if (goalType instanceof BuiltInType bt) {

            int op = switch (operator.type()) {
                case ADD -> bt.getAddOpcode();
                case SUB -> bt.getSubtractOpcode();
                case MUL -> bt.getMultiplyOpcode();
                case DIV -> bt.getDivideOpcode();
                case LT, GT, LE, GE -> 0;
                default -> throw new IllegalStateException("Unexpected value: " + operator.type());
            };
            ga.visitInsn(op);
        } else {
            Util.exit("need a test case here", 452);
        }
    }
}
