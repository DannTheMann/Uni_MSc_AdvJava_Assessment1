package advjava.assessment1.zuul.refactored;

import advjava.assessment1.zuul.*;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a three word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/*

* Allow for Command to have many nth value words
-> Reduces a lot of predefined objects (word1 = null, word2 = null etc)
-> Makes code much easier to read as well identify 

*/

public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public CommandExecution getCommand() 
    {
        String inputLine;   // will hold the full input line

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();
        
        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
//        if(commands.isCommand(inputLine.split(" ")[0])) {
            return new CommandExecution(inputLine.split(" "));
//        }
//        else {
//            return new CommandExecution(inputLine.split(" ")); 
//        }
    }
}
