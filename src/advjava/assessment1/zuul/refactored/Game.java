package advjava.assessment1.zuul.refactored;

import java.util.Properties;

import advjava.assessment1.zuul.refactored.character.CharacterManager;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;
import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;

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

/**
 * 
 * The bulk of the game happens here. Multiple fields handle management 
 * for respective roles in the game. The class provides access to these
 * managers through getters and includes methods to interact with the game
 * with limited scope as to stop accidental breaking of links between references.
 * 
 * 
 * @author dja33
 *
 */
public class Game {

	// The command line parser used to interpret user instruction
	private final Parser parser = new Parser();
	
	/*
	 * Management for multiple roles
	 */
    private final CommandManager commandManager;
	private final CharacterManager characterManager;
	private final RoomManager roomManager;
	private final ItemManager itemManager;
	private final InternationalisationManager im;
	
	// Properties provided in Zuul.properties are stored here
	private Properties properties;
	// Instance of the local player is stored here
    private Player player;

    
    /**
     * Create new instance of the Game.
     * 
     * Creates all management systems.
     * 
     * Call .initialiseGame( prop ) to prepare the game
     * 
     * Call .play() to start the game
     */
    public Game() {
    	this.commandManager = new CommandManager();
    	this.characterManager = new CharacterManager();
    	this.roomManager = new RoomManager();
    	this.itemManager = new ItemManager();
    	this.im = InternationalisationManager.im;
    }

    /**
     * Initialise the game, loads all characters, rooms and items from the
     * XML files provided in the folder /config. Creates the player reference
     * and using the properties file provided establishes his credentials for 
     * play during the game.
     * @param properties The properties specified in Zuul.properties
     * @throws InvalidCharacterNamingException Name for player is null or empty
     */
    protected final void initialiseGame(Properties properties) throws InvalidCharacterNamingException{

    			this.commandManager.loadPlugins();
    	
    			this.properties = properties;
    	
				try {
					
					System.out.println();
					System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
					System.out.println(im.getMessage("game.loadXML"));
					System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
					
	        		XMLManager.loadItems(itemManager);
	        		XMLManager.loadRooms(roomManager);
					XMLManager.loadCharacters(characterManager);
					
					System.out.println(im.getMessage("game.finishXML"));
					System.out.println();
					
				} catch (MalformedXMLException e) {
					System.err.println(im.getMessage("game.failXML"));
					e.printStackTrace();
					System.exit(1);
				}
				
				Room startingRoom = roomManager.getRoom(properties.getProperty("startingRoom"));
				
				if(startingRoom == null)
					throw new NullPointerException(String.format(im.getMessage("game.noStartRoom"), properties.getProperty("startingRoom")));

				player = new Player(properties.getProperty("playerName"), properties.getProperty("playerDescription"), startingRoom, Integer.parseInt(properties.getProperty("playerMaxWeight")));
    }

    // A boolean used to terminate the game
    protected boolean finished = false;
    
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
        parser.close();
    }
    
    /**
     * Get the current players instance
     * @return player reference
     */
    public Player getPlayer(){
    	return player;
    }

    /**
     * Print out the opening message for the player and their starting room
     */
    private void printWelcome() {
        System.out.println();
        System.out.println(im.getMessage("game.welcome1"));
        System.out.println(im.getMessage("game.welcome2"));
        System.out.println(im.getMessage("game.welcome3"));
        System.out.println();
        
        System.out.println(player.getCurrentRoom());
        
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true if the command was executed correctly
     */
    private boolean processCommand(CommandExecution command) {
    	
    	// If the command is empty of null
    	if(command.isUnknown())
    		return false;
    	
    	// If the command isn't a known command
        if (commandManager.getCommand(command.getCommandWord()) == null) {
            System.out.println(im.getMessage("game.invalidCmd"));
            return false;
        }

        // Perform the action of the command and return whether
        // the action performed as expected
        return commandManager.getCommand(command.getCommandWord()).action(this, command);
    }
    
    /**
     * Get command manager, access to the 
     * all commands and references
     * @return CommandManager
     */
    public CommandManager getCommandManager(){
    	return commandManager;
    }
    
    /**
     * Get character manager, access to the 
     * all characters and references
     * @return CharacterManager
     */
    public CharacterManager getCharacterManager(){
    	return characterManager;
    }
    
    /**
     * Get room manager, access to the 
     * all rooms and references
     * @return RoomManager
     */
    public RoomManager getRoomManager(){
    	return roomManager;
    }
    
    /**
     * Get command manager, access to the 
     * all commands and references
     * @return CommandManager
     */
    public ItemManager getItemManager(){
    	return itemManager;
    }

    /**
     * Return a property in the properties file
     * @param property to look for
     * @return The value in response to key
     */
	public String getProperty(String property) {
		return properties.getProperty(property);		
	}
	
	/**
	 * Get the internationlisation manager for multiple language
	 * @return im
	 */
	public InternationalisationManager getInternationalisationManager(){
		return im;
	}

	/**
	 * Terminate the game
	 */
	public void terminate() {
		finished = true;
	}
}
