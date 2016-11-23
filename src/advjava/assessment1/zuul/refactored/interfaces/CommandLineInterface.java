/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.interfaces;

import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.utils.Out;
import java.util.Scanner;

/**
 *
 * @author dja33
 */
public class CommandLineInterface implements UserInterface{
    
        private Scanner reader; // source of command input

	/**
	 * Create a parser to read from the terminal window.
	 */
	public CommandLineInterface() {
		reader = new Scanner(System.in);
	}

	/**
	 * Get command that was executed
	 * 
	 * @return The next command from the user.
	 */
	public CommandExecution getCommand() {
		print("> "); // print prompt
		return new CommandExecution(reader.nextLine()); // Pass the entire line
														// to the command
														// execution
	}

	/**
	 * Close the scanner
	 */
	public void exit() {
		reader.close();
	}

    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void println() {
         System.out.println();
    }

    @Override
    public void printErr(Object obj) {
         System.err.print(obj);
    }

    @Override
    public void printlnErr(Object obj) {
         System.err.println(obj);
    }
    
}
