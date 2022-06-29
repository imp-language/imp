package org.imp.jvm.tool;

import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.Util;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

/**
 * Global-ish timer class for the Imp compiler.
 * Prints pretty statements to stdout regarding the
 * timing and progress of the compiler.
 */
public class Timer {

	private static final long startTime = System.nanoTime();
	private static final String TIME_SYMBOL = "◔ ";
	/**
	 * Change Timer.Log to `true` to enable messages
	 */
	public static boolean LOG = false;
	private static long time = startTime;

	public static void log(String message) {
		if (LOG) {
			long current = System.nanoTime();
			long delta = current - time;
			float runtime = ((float) delta) / 1000000;
			time = current;
			String formattedRuntime = String.format("%.2fms", runtime);
			formattedRuntime = StringUtils.rightPad(formattedRuntime, 10);
			Util.println(colorize(TIME_SYMBOL + formattedRuntime + message, TEXT_COLOR(104)));
			time = System.nanoTime();
		}
	}

	@SuppressWarnings("UnusedReturnValue")
	public static float logTotalTime() {
		if (LOG) {
			long current = System.nanoTime();
			long delta = current - startTime;
			float runtime = ((float) delta) / 1000000;
			String formattedRuntime = String.format("%.2fms", runtime);
			formattedRuntime = StringUtils.rightPad(formattedRuntime, 10);
			Util.println(colorize(TIME_SYMBOL + formattedRuntime + "done", TEXT_COLOR(212)));
			return runtime;
		}
		return 0;
	}
}
