package advjava.assessment1.zuul.refactored;

import java.util.Scanner;

import advjava.assessment1.zuul.CommandWords;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
/*

* Allow for Command to have many nth value words
-> Reduces a lot of predefined objects (word1 = null, word2 = null etc)
-> Makes code much easier to read as well identify 

*/

/**
 * The parser is designed to take input from the user one 
 * line at a time, this line is then split based on the
 * regex pattern " " and separated into strings of an Array
 * to then be passed into the constructor for CommandExecution.
 * 
 * 
 * @author Daniel
 *
 */
public class Parser 
{
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Get command that was executed
     * @return The next command from the user.
     */
    public CommandExecution getCommand() 
    {
        System.out.print("> ");     // print prompt
        return new CommandExecution(reader.nextLine().split(" ")); // Split the input line into separated words in an array
    }
    
    /**
     * Close the scanner
     */
    protected void close(){
    	reader.close();
    }
}
