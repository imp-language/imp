package org.imp.jvm.types;

import org.objectweb.asm.Opcodes;

public record GenericType(String key) implements ImpType {


	@Override
	public Object getDefaultValue() {
		return null;
	}

	@Override
	public String getDescriptor() {
		return "Ljava/lang/Object;";
	}

	@Override
	public String getInternalName() {
		return key;
	}

	@Override
	public int getLoadVariableOpcode() {
		return Opcodes.ALOAD;
	}


	@Override
	public String getName() {
		return null;
	}

	@Override
	public int getReturnOpcode() {
		return Opcodes.ARETURN;
	}

	@Override
	public Class<?> getTypeClass() {
		return Object.class;
	}

	@Override
	public boolean isNumeric() {
		return false;
	}

	@Override
	public String kind() {
		return null;
	}
}
