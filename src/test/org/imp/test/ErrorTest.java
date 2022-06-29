package org.imp.test;

import org.imp.jvm.Util;
import org.imp.jvm.errors.Comptime;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {
	static final String pwd = System.getProperty("user.dir");
	static final String moduleLocation = Path.of(pwd, "verification").toString();

	@Test
	void MatchCoverage() throws IOException {
		assertEquals(
				Util.countedSet(Comptime.MatchCoverage.code),
				Load.checkForErrors("errors/matchCoverage", moduleLocation)
		);
	}

	@Test
	void MutabilityError() throws IOException {
		assertEquals(
				Util.countedSet(Comptime.MutabilityError.code),
				Load.checkForErrors("errors/mutabilityError", moduleLocation)
		);
	}

	@Test
	void ParameterTypeMismatch() throws IOException {
		assertEquals(
				Util.countedSet(
						Comptime.ParameterTypeMismatch.code,
						Comptime.ParameterTypeMismatch.code,
						Comptime.ParameterTypeMismatch.code,
						Comptime.ParameterTypeMismatch.code
				),
				Load.checkForErrors("errors/parameterTypeMismatch", moduleLocation)
		);
	}

	@Test
	void Unreachable() throws IOException {
		assertEquals(
				Util.countedSet(Comptime.Unreachable.code, Comptime.Unreachable.code, Comptime.Unreachable.code),
				Load.checkForErrors("errors/unreachable", moduleLocation)
		);
	}

	@Test
	void VoidUsage() throws IOException {
		assertEquals(
				Util.countedSet(Comptime.VoidUsage.code),
				Load.checkForErrors("errors/voidUsage", moduleLocation)
		);
	}


}
