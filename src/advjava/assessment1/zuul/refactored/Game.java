package advjava.assessment1.zuul.refactored;

import advjava.assessment1.zuul.refactored.character.CharacterManager;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterMoveException;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;
import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;

import java.util.Properties;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * To play this game, create an instance of this class and call the "play"
 * method.
 *
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */


/*

========
COMMENTS
========

ArrayList is generic, should be list
Items and weights should be their own item, or at the least a map

Rooms should implement mapping between other rooms
-> Can 'printinfo' to avoid large clusters of unwarranted code

printWelcome and goRoom have duplication of code, slim down printWelcome and
use goRoom or vice versa

*/
public class Game {

	private final Parser parser = new Parser();
    private final CommandManager commandManager;
	private final CharacterManager characterManager;
	private final RoomManager roomManager;
	private final ItemManager itemManager;
	private Properties properties;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
    	this.commandManager = new CommandManager();
    	this.characterManager = new CharacterManager();
    	this.roomManager = new RoomManager();
    	this.itemManager = new ItemManager();
    }

    /**
     * Create all the rooms and link their exits together.
     * @param properties 
     * @throws InvalidCharacterNamingException 
     */
    protected final void initialiseGame(Properties properties) throws InvalidCharacterNamingException{
      		
    			this.properties = properties;
    	
				try {
					
					System.out.println();
					System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
					System.out.println("         Loading XML Config files ...    ");
					System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
					
	        		XMLManager.loadItems(itemManager);
	        		XMLManager.loadRooms(roomManager);
					XMLManager.loadCharacters(characterManager);
					
					System.out.println(" - - Finished - - - - - - - - - - - - - - ");
					System.out.println();
					
				} catch (MalformedXMLException e) {
					System.err.println("Failed to load XML files!");
					e.printStackTrace();
					System.exit(1);
				}
				
				Room startingRoom = roomManager.getRoom(properties.getProperty("startingRoom"));
				
				if(startingRoom == null)
					throw new NullPointerException(String.format("No room matches the starting room! [Specified: %s]", properties.getProperty("startingRoom")));
				else
					System.out.println("Found current room.");
				
				player = new Player(properties.getProperty("playerName"), properties.getProperty("playerDescription"), startingRoom);

    }

    private boolean finished = false;
    
    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        while (!finished) {
            CommandExecution command = parser.getCommand();
            processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    public Player getPlayer(){
    	return player;
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        
        player.getCurrentRoom().printDetails();
        
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(CommandExecution command) {
    	
        if (commandManager.getCommand(command.getCommandWord()) == null) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        return commandManager.getCommand(command.getCommandWord()).action(this, command);
    }
    
    public CommandManager getCommandManager(){
    	return commandManager;
    }
    
    public CharacterManager getCharacterManager(){
    	return characterManager;
    }
    
    public RoomManager getRoomManager(){
    	return roomManager;
    }
    
    public ItemManager getItemManager(){
    	return itemManager;
    }

	public String getProperty(String property) {
		return properties.getProperty(property);		
	}

	public void terminate() {
		finished = true;
	}
}
