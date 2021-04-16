package org.imp.jvm.domain;

import org.objectweb.asm.Opcodes;

import java.util.Arrays;

public enum CompareSign {
    EQUAL("==", Opcodes.IF_ICMPEQ),
    NOT_EQUAL("!=", Opcodes.IF_ICMPNE),
    LESS("<", Opcodes.IF_ICMPLT),
    GREATER(">", Opcodes.IF_ICMPGT),
    LESS_OR_EQUAL("<=", Opcodes.IF_ICMPLE),
    GREATER_OR_EQUAL(">=", Opcodes.IF_ICMPGE);

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