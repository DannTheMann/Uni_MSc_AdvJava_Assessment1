package advjava.assessment1.zuul.refactored.cmds;

import java.util.Arrays;

import advjava.assessment1.zuul.*;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a second
 * word (for example, if the command was "take map", then the two strings
 * obviously are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */


/*

==========
COMMENT
==========

* Should use sometihng like a tokenizer so that nth words can be
stored rather than predefined limitations
* 


*/
public class CommandExecution
{
    private String[] words;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     * @param thirdWord The second word of the command.
     */
    public CommandExecution(String[] words)
    {
        this.words = words;
        System.out.println(Arrays.toString(words));
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return words[0];
    }

    public String getWord(int index){
    	if(index < 0 || index >= words.length)
    		return null;
    	return words[index];
    }
    
    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (words.length == 0 );
    }

    public boolean hasParameter(int index){
    	return words.length > index;
    }
}

