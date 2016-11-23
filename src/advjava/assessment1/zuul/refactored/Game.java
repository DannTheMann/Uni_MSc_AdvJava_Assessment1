package advjava.assessment1.zuul.refactored;

import java.util.Properties;

import advjava.assessment1.zuul.refactored.character.CharacterManager;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.cmds.CommandManager;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;
import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;
import advjava.assessment1.zuul.refactored.interfaces.CommandLineInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.item.ItemManager;
import advjava.assessment1.zuul.refactored.room.RoomManager;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;
import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.XMLManager;
import java.io.IOException;

/**
 * 
 * The bulk of the game happens here. Multiple fields handle management for
 * respective roles in the game. The class provides access to these managers
 * through getters and includes methods to interact with the game with limited
 * scope as to stop accidental breaking of links between references.
 * 
 * 
 * @author dja33
 *
 */
public class Game {

	// The command line parser used to interpret user instruction
	private final UserInterface ui;
        //private final Parser parser = new Parser();
    
    
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
	
	/*
	 * If we planned on having multiple 'LOCAL' players then could remove this
	 * reference and use the CharacterManager more fluidly as it currently
	 * stores all characters including players. The game can load multiple
	 * players from the characters.xml and by default will use whatever player
	 * it loads first as player '1'.
	 * 
	 * Otherwise we could assume that 'ONLINE' multiplayer would require some
	 * form of NetworkPlayer class that extends the Player class.
	 * 
	 * However, since I want to make this perfectly clear - I didn't want to
	 * implement more than I needed to and tried to make this as open to
	 * modification as plausible, hence the XML can store multiple players and
	 * the CharacterManager will load all of them as Players. It will only use
	 * this single reference for the time being.
	 */
	private Player player; 	// Instance of the local player is stored here

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
                Out.createLogger(Main.LOG_FILES);
                this.ui = new CommandLineInterface();
		this.commandManager = new CommandManager();
		this.characterManager = new CharacterManager();
		this.roomManager = new RoomManager();
		this.itemManager = new ItemManager();
		this.im = InternationalisationManager.im;
	}

	/**
	 * Initialise the game, loads all characters, rooms and items from the XML
	 * files provided in the folder /config. Creates the player reference and
	 * using the properties file provided establishes his credentials for play
	 * during the game.
	 * 
	 * @param properties
	 *            The properties specified in Zuul.properties
	 * @throws InvalidCharacterNamingException
	 *             Name for player is null or empty
	 */
	protected final void initialiseGame(Properties properties) throws InvalidCharacterNamingException {

		this.commandManager.loadPlugins();

		this.properties = properties;

		try {

			ui.println();
			Out.out.logln(" - - - - - - - - - - - - - - - - - - - - ");
			Out.out.logln(im.getMessage("game.loadXML"));
                        Out.out.logln("-> " + Main.XML_CONFIGURATION_FILES);
			Out.out.logln(" - - - - - - - - - - - - - - - - - - - - ");

			XMLManager.loadItems(itemManager);
			XMLManager.loadRooms(roomManager);
			XMLManager.loadCharacters(characterManager);

			Out.out.logln(im.getMessage("game.finishXML"));
			ui.println();

		} catch (MalformedXMLException e) {
			ui.printlnErr(im.getMessage("game.failXML"));
			System.exit(1);
		}

		// Get the first player we loaded from the XML
		player = characterManager.getFirstPlayer();
		
		// If no player is specified in the XML, throw an erro
		if(player == null){
			throw new NullPointerException(
					String.format(im.getMessage("game.noPlayer")));
		}

		// Print out details regarding player '1' and total players loaded.
		Out.out.logln(String.format(im.getMessage("game.totalPlayers"), characterManager.players().size()));
		Out.out.logln(String.format(im.getMessage("game.startPlayer"), player.getName()));
		
	}

	// A boolean used to terminate the game
	protected boolean finished = false;

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.
		while (!finished) {
			CommandExecution command = ui.getCommand();
			if(!processCommand(command)){
				continue;
			}
			characterManager.act(this);
		}
		ui.exit();
                System.exit(0);
	}

	/**
	 * Get the current players instance
	 * 
	 * @return player reference
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Print out the opening message for the player and their starting room
	 */
	private void printWelcome() {
		ui.println();
		ui.println(im.getMessage("game.welcome1"));
		ui.println(im.getMessage("game.welcome2"));
		ui.println(im.getMessage("game.welcome3"));
		ui.println();

		ui.println(player.getCurrentRoom());
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 *
	 * @param command
	 *            The command to be processed.
	 * @return true if the command was executed correctly
	 */
	private boolean processCommand(CommandExecution command) {

		// If the command is empty of null
		if (command.isUnknown())
			return false;

		// If the command isn't a known command
		if (commandManager.getCommand(command.getCommandWord()) == null) {
			ui.println(im.getMessage("game.invalidCmd"));
			return false;
		}

		// Perform the action of the command and return whether
		// the action performed as expected
		return commandManager.getCommand(command.getCommandWord()).action(this, command);
	}

	/**
	 * Get command manager, access to the all commands and references
	 * 
	 * @return CommandManager
	 */
	public CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * Get character manager, access to the all characters and references
	 * 
	 * @return CharacterManager
	 */
	public CharacterManager getCharacterManager() {
		return characterManager;
	}

	/**
	 * Get room manager, access to the all rooms and references
	 * 
	 * @return RoomManager
	 */
	public RoomManager getRoomManager() {
		return roomManager;
	}

	/**
	 * Get command manager, access to the all commands and references
	 * 
	 * @return CommandManager
	 */
	public ItemManager getItemManager() {
		return itemManager;
	}

	/**
	 * Return a property in the properties file
	 * 
	 * @param property
	 *            to look for
	 * @return The value in response to key
	 */
	public String getProperty(String property) {
		return properties.getProperty(property);
	}

	/**
	 * Get the internationlisation manager for multiple language
	 * 
	 * @return im
	 */
	public InternationalisationManager getInternationalisationManager() {
		return im;
	}

	/**
	 * Terminate the game
	 */
	public void terminate() {
		finished = true;
                try{
                    Out.out.close();
                }catch(IOException ioe){
                    Out.out.loglnErr("Failed to close logger!");
                }
	}

        public UserInterface getInterface() {
            return ui;
        }
}
