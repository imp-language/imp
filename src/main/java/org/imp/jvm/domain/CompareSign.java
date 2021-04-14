package org.imp.jvm.domain;

import org.objectweb.asm.Opcodes;

import java.util.Arrays;

public enum CompareSign {
    EQUAL("==", Opcodes.IFEQ),
    NOT_EQUAL("!=", Opcodes.IFNE),
    LESS("<", Opcodes.IFLT),
    GREATER(">", Opcodes.IFGT),
    LESS_OR_EQUAL("<=", Opcodes.IFLE),
    GRATER_OR_EQAL(">=", Opcodes.IFGE);

    private final String sign;
    private final int opcode;

    CompareSign(String s, int opcode) {
        sign = s;
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }

    public static CompareSign fromString(String sign) {
        return Arrays.stream(values()).filter(cmpSign -> cmpSign.sign.equals(sign))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sign not implemented"));
    }
}