package org.imp.jvm.types;

import org.objectweb.asm.Opcodes;

import java.util.Set;
import java.util.stream.Collectors;

public class UnionType implements ImpType {
	public Set<ImpType> types;

	public UnionType(Set<ImpType> types) {
		this.types = types;

	}


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
		return null;
	}

	@Override
	public int getLoadVariableOpcode() {
		return Opcodes.ALOAD;
	}

	@Override
	public String getName() {
		return types.stream().map(ImpType::getName).collect(Collectors.joining(" | "));
	}


	@Override
	public int getReturnOpcode() {
		return Opcodes.ARETURN;
	}


	@Override
	public Class<?> getTypeClass() {
		return null;
	}

	@Override
	public boolean isNumeric() {
		return false;
	}

	@Override
	public String kind() {
		return null;
	}


	@Override
	public String toString() {
		return types.stream().map(Object::toString).collect(Collectors.joining(" | "));
	}
}
