package advjava.assessment1.zuul.refactored;

import java.util.Scanner;

import advjava.assessment1.zuul.refactored.cmds.CommandExecution;


/**
 * The parser is designed to take input from the user one line at a time, this
 * line is then split based on the regex pattern " " and separated into strings
 * of an Array to then be passed into the constructor for CommandExecution.
 * 
 * 
 * @author dja33
 *
 */
public class Parser {
	private Scanner reader; // source of command input

	/**
	 * Create a parser to read from the terminal window.
	 */
	public Parser() {
		reader = new Scanner(System.in);
	}

	/**
	 * Get command that was executed
	 * 
	 * @return The next command from the user.
	 */
	public CommandExecution getCommand() {
		System.out.print("> "); // print prompt
		return new CommandExecution(reader.nextLine()); // Pass the entire line
														// to the command
														// execution
	}

	/**
	 * Close the scanner
	 */
	protected void close() {
		reader.close();
	}
}
