package org.imp.jvm.domain;

import org.objectweb.asm.Opcodes;

import java.util.Arrays;

public enum Operator {
    ADD("+", Opcodes.IFEQ),
    SUBTRACT("-", Opcodes.IFNE),
    MULTIPLY("*", Opcodes.IFEQ),
    DIVIDE("/", Opcodes.IFEQ),
    MODULUS("%", Opcodes.IFEQ),
    LESS("<", Opcodes.IFLT),
    GREATER(">", Opcodes.IFGT),
    LESS_OR_EQUAL("<=", Opcodes.IFLE),
    GREATER_OR_EQUAL(">=", Opcodes.IFGE),
    INDEX("[]", Opcodes.IFEQ);

    private final String sign;
    private final int opcode;

    Operator(String s, int opcode) {
        sign = s;
        this.opcode = opcode;
    }

    public static Operator fromString(String sign) {
        return Arrays.stream(values()).filter(cmpSign -> cmpSign.sign.equals(sign))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sign not implemented"));
    }

    public int getOpcode() {
        return opcode;
    }
}