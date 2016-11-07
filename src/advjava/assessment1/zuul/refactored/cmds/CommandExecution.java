package advjava.assessment1.zuul.refactored.cmds;

/**
 * 
 * Commands that executed are stored in this brief reference
 * of the objects from this class. It takes the input line
 * passed from Parser and breaks each argument of the input
 * into parameters with the first being the command word.
 * 
 * Provides methods to get certain elements of the command such
 * as the command word, nth parameter and length.
 * 
 * @author dja33
 *
 */
public class CommandExecution
{
	// entire command stores as elements
	// e.g "give tom sword" -> ["give", "tom", "sword"]
    private String[] words;

    /**
     * Create a command object. Takes an
     *  entire string and splits it
     * into each word for the command. 
     * @param words An Array of words
     */
    public CommandExecution(String input)
    {
        this.words = input.split(" ");
    }
    
    /**
     * Replaced by other constructor to keep cohesion 
     * between parser and commandexecution class.
     * @param words An array of words
     */
    @Deprecated
    public CommandExecution(String[] words){
    	this.words = words;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command is null then return an empty string else the commandword.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return words[0] != null ? words[0] : "";
    }
    
    /**
     * Get the entire command as an Array of strings
     * @return Command Array
     */
    public String[] getCommand(){
    	return words;
    }
    
    /**
     * Get the length of the command and words used
     * @return length
     */
    public int commandLength(){
    	return words.length;
    }

    /**
     * Get a specific parameter from the command
     * @param index The parameter to retrieve
     * @return The command parameter
     */
    public String getWord(int index){
    	if(index < 0 || index >= words.length)
    		return "";
    	return words[index];
    }
    
    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return getCommandWord().equals("");
    }

    /**
     * Checks whether the command has a parameter at
     * the specified index
     * @param index The position to check
     * @return true if there is a parameter here
     */
    public boolean hasParameter(int index){
    	return words.length > index;
    }
    
}

