package advjava.assessment1.zuul.refactored.testing;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static java.lang.System.out;

import java.util.Arrays;

public class RoomTestRunner {

	private static final String BORDER_TOP = "/------------------------------------------------------------------------\\";
	private static final String BORDER_MIDDLE = "|------------------------------------------------------------------------|";
	private static final String EMPTY_MIDDLE = "|                                                                        |";
	private static final String BORDER_BOTTOM = "\\------------------------------------------------------------------------/";
	private static final int LENGTH = BORDER_TOP.length() - 2;
	private static int errorCount = 1;

	/**
	 * Run the tests on Room, display in a pretty way.
	 * @param args
	 */
	public static void main(String[] args) {

		out.println(BORDER_TOP);
		out.println(confineToSpace("                      Beginning Tests."));
		out.println(BORDER_BOTTOM);

		Result result = JUnitCore.runClasses(RoomTestSuite.class);

		out.println(BORDER_TOP);
		out.println(confineToSpace("                         Finished."));
		out.println(BORDER_MIDDLE);
		out.println(EMPTY_MIDDLE);
		out.println(confineToSpace("Tests run: " + result.getRunCount()));
		out.println(confineToSpace("Elapsed time: " + result.getRunTime()));
		out.println(confineToSpace("Tests succeeded: " + (result.getRunCount()-result.getFailureCount())));
		out.println(confineToSpace("Tests failed: " + result.getFailureCount()));
		out.println(confineToSpace("Tests ignored: " + result.getIgnoreCount()));
		out.println(confineToSpace("Successful: " + result.wasSuccessful()));
		out.println(EMPTY_MIDDLE);
		out.println(BORDER_MIDDLE);
		out.println(confineToSpace("Printing any failed tests..."));
		out.println(BORDER_MIDDLE);
		out.println(EMPTY_MIDDLE);
		
		result.getFailures().stream().forEach(r -> {
			String msg = errorCount++ + ". " + r.getTestHeader() + " >> " + r.getMessage();
			msg = msg.replaceAll("(.{" + (LENGTH - 5) + "})", "$1\n");
			Arrays.stream(msg.split("\n")).forEach(s -> out.println(confineToSpace(s)));
			out.println(confineToSpace(""));

		});
		out.println(BORDER_BOTTOM);

	}

	/**
	 * Confine the message to a border
	 * @param message The message to confine
	 * @return The confined message
	 */
	private static String confineToSpace(String message) {

		String msg = "| ";

		if (message.length() + 2 <= LENGTH) 
			msg += message.replaceAll("(\r\n)|\n|\r", "");
			for (int i = msg.length(); i < LENGTH; i++)
				msg += " ";
			msg += " |";
		
		return msg;

	}

	/**
	 * Print and in turn confine
	 * @param message What to print
	 */
	public static void println(String message) {
		out.println(confineToSpace(message.replaceAll("(.{" + (LENGTH - 5) + "})", "$1\n")));
	}

}
