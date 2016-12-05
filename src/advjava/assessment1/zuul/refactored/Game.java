package advjava.assessment1.zuul.refactored;

import java.util.Properties;

import advjava.assessment1.zuul.refactored.character.CharacterManager;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.CommandManager;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;
import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.item.ItemManager;
import advjava.assessment1.zuul.refactored.room.RoomManager;
import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.XMLManager;

public abstract class Game {
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
	private Player player; // Instance of the local player is stored here
	// The command line parser used to interpret user instruction
	protected final UserInterface ui;
	// private final Parser parser = new Parser();

	// Properties provided in Zuul.properties are stored here
	private Properties properties;
	
	/*
	 * Management for multiple roles
	 */
	protected final CommandManager commandManager;
	protected final CharacterManager characterManager;
	protected final RoomManager roomManager;
	protected final ItemManager itemManager;
	protected final InternationalisationManager im;
	// A boolean used to terminate the game
	protected boolean finished = false;
	
	public Game(){
		Out.createLogger(Main.LOG_FILES);
		this.ui = new GraphicalInterface();
		//this.ui = new CommandLineInterface();
		this.commandManager = new CommandManager();
		this.characterManager = new CharacterManager();
		this.roomManager = new RoomManager();
		this.itemManager = new ItemManager();
		this.im = InternationalisationManager.im;
	}
	
	public abstract void play();
	public abstract void terminate();
	public abstract boolean hasTerminated();
	

	/**
	 * Get the current players instance
	 * 
	 * @return player reference
	 */
	public Player getPlayer() {
		return player;
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

		commandManager.loadPlugins();

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
		if (player == null) {
			throw new NullPointerException(String.format(im.getMessage("game.noPlayer")));
		}

		// Print out details regarding player '1' and total players loaded.
		Out.out.logln(String.format(im.getMessage("game.totalPlayers"), characterManager.players().size()));
		Out.out.logln(String.format(im.getMessage("game.startPlayer"), player.getName()));

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
	 * Get the internationlisation manager for multiple language
	 * 
	 * @return im
	 */
	public InternationalisationManager getInternationalisationManager() {
		return im;
	}
	

	public UserInterface getInterface() {
		return ui;
	}
	
	public String getProperty(String key){
		return properties.getProperty(key);
	}
	
	
}
