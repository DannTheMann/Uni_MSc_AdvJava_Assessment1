package advjava.assessment1.zuul.refactored;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.DownloadManager;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;

/**
 * The starting point of the game, handles directory creation for the game and
 * folders such as plugins and config files.
 *
 * Handles creation of properties and actually initialising the game.
 *
 * @author Daniel
 *
 */
public class Main {

	// Constants for directories and files
	private static final String ROOT_DIRECTORY = System.getProperty("user.dir") + File.separator + "WorldOfZuul"
			+ File.separator;
	public static final String PLUGIN_COMMANDS_FOLDER = ROOT_DIRECTORY + "Plugins";
	public static final String XML_CONFIGURATION_FILES = ROOT_DIRECTORY + "Config";
	public static final String LOG_FILES = ROOT_DIRECTORY + "Config" + File.separator + "Logs";
	public static final String RESOURCE_FILES = ROOT_DIRECTORY + "Resources";
	public static final String RESOURCE_MUSIC = ROOT_DIRECTORY + "Resources" + File.separator + "music";
	public static final String PROPERTIES_FILE = XML_CONFIGURATION_FILES + File.separator + "zuul.properties";

	private static Properties properties = null;

	/**
	 * Singleton for game
	 */
	public static final ZuulGame game = new ZuulGame();

	/**
	 * Starting point for the game, checks directories exist for config and
	 * plugins, else creates them, loads the properties and finally initialises
	 * the game and passes the properties.
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		try {

			// If this fails then we cannot locate the resources
			// to run the game
			if (!verifyRootDirectory()) {
				throw new FileNotFoundException(
						"Cannot proceed with game, one or more directories does not exist! Could not create it either, permission denied?"
								+ System.lineSeparator()
								+ " Have you made sure that the files provided with the game are in their correct location? "
								+ System.lineSeparator() + " @ " + ROOT_DIRECTORY);
			}

			Out.out.setPrintingDebugMessages(true);
			Out.out.logln(InternationalisationManager.im.getMessage("main.start"));

			// Check directories for plugins/xml files
			checkDirectory(PLUGIN_COMMANDS_FOLDER);
			checkDirectory(XML_CONFIGURATION_FILES);

			// Load properties file to gather default parameters for game
			Out.out.logln(InternationalisationManager.im.getMessage("main.loadProp"));
			properties = loadProperties();

			// Out.close();
			// Out.createLogger(properties.getProperty("logFile"));
			Out.out.logln(InternationalisationManager.im.getMessage("main.finishProp"));

			// Delay to allow everything to be ready...
			Thread.sleep(1000);
			Out.out.logln(InternationalisationManager.im.getMessage("main.createSession"));
			// Create the game, initialise and start it
			game.initialiseGame(properties);

			Out.out.setPrintingDebugMessages(Boolean.parseBoolean(properties.getProperty("logEverything")));
			Out.out.logln("Are we logging everything (properties file): " + Out.out.isPrintingDebugMessages());

			game.play();

		} catch (Exception e) {
			e.printStackTrace();
			Out.out.loglnErr("An error occurred somewhere, closing logger. " + e.getMessage());
			Out.close();
		}

	}

	/**
	 * Verify the root directory exists, if not
	 * will attempt to create them - if this succeeds will
	 * then attempt to download all core default files from
	 * www.danielandrews.co.uk/zuul
	 * Else if it fails, will terminate the application
	 * @return Whether to terminate or not
	 */
	private static boolean verifyRootDirectory() {

		// All static file directories
		final String[] files = { ROOT_DIRECTORY, PLUGIN_COMMANDS_FOLDER, XML_CONFIGURATION_FILES, LOG_FILES, RESOURCE_FILES , RESOURCE_MUSIC};
		boolean ready = true;
		
		// Iterate over every directory 
		for (String dir : files) {
			File f = new File(dir);
			// If the directory does not exist
			if (!f.exists()) {
				System.err.println("Could not find directory @ " + dir);
				f.mkdir(); // try to create it
				if (!f.exists()) {
					// if this fails, terminate later but try next directory
					System.err.println("Failed to create this directory, most likely a permissions issue! Try running this program as Admin.");
					ready = false;
					continue;
				}
				
				/* Download any files needed */
				if (dir.equals(XML_CONFIGURATION_FILES)) {
					DownloadManager.downloadFile("XML", "items.xml", XML_CONFIGURATION_FILES);
					DownloadManager.downloadFile("XML", "characters.xml", XML_CONFIGURATION_FILES);
					DownloadManager.downloadFile("XML", "rooms.xml", XML_CONFIGURATION_FILES);
					DownloadManager.downloadFile("XML", "zuul_style.css", XML_CONFIGURATION_FILES);
				} else if (dir.equals(PLUGIN_COMMANDS_FOLDER)) {
					DownloadManager.downloadFile("Plugins", "ExamplePlugin_WorldOfZuul.jar", PLUGIN_COMMANDS_FOLDER);
				} else if (dir.equals(RESOURCE_FILES)) {
					DownloadManager.downloadFile("Resources", "error.png", RESOURCE_FILES);

					String resDir = "Resources/characters";
					String outputDir = RESOURCE_FILES + File.separator + "characters";

					// More complex stuff
					DownloadManager.downloadFile(resDir, "charlie.jpg", outputDir);
					DownloadManager.downloadFile(resDir, "dan.png", outputDir);
					DownloadManager.downloadFile(resDir, "donald.png", outputDir);
					DownloadManager.downloadFile(resDir, "harold.jpg", outputDir);
					DownloadManager.downloadFile(resDir, "jack.png", outputDir);
					DownloadManager.downloadFile(resDir, "joe.png", outputDir);
					DownloadManager.downloadFile(resDir, "rosie.png", outputDir);
					DownloadManager.downloadFile(resDir, "stephen.png", outputDir);

					resDir = "Resources/items";
					outputDir = RESOURCE_FILES + File.separator + "items";

					DownloadManager.downloadFile(resDir, "apple.png", outputDir);
					DownloadManager.downloadFile(resDir, "book.png", outputDir);
					DownloadManager.downloadFile(resDir, "glasses.png", outputDir);
					DownloadManager.downloadFile(resDir, "grapes.png", outputDir);
					DownloadManager.downloadFile(resDir, "pear.png", outputDir);
					DownloadManager.downloadFile(resDir, "sword.png", outputDir);
					DownloadManager.downloadFile(resDir, "tomato.png", outputDir);
					
					resDir = "Resources/rooms";
					outputDir = RESOURCE_FILES + File.separator + "rooms";

					DownloadManager.downloadFile(resDir, "parkwood.jpg", outputDir);
					DownloadManager.downloadFile(resDir, "bar.jpg", outputDir);
					DownloadManager.downloadFile(resDir, "lab.jpg", outputDir);
					DownloadManager.downloadFile(resDir, "library.jpg", outputDir);
					DownloadManager.downloadFile(resDir, "outside.jpg", outputDir);
					DownloadManager.downloadFile(resDir, "theatre.jpg", outputDir);


				}else if (dir.equals(RESOURCE_MUSIC)) {
					
					DownloadManager.downloadFile("Resources/music", "main.mp3", RESOURCE_MUSIC);

					String resDir = "Resources/music";
					String outputDir = RESOURCE_FILES + File.separator + "music";
					
					DownloadManager.downloadFile(resDir, "bar.mp3", outputDir);
					DownloadManager.downloadFile(resDir, "soundofsilence.mp3", outputDir);
					DownloadManager.downloadFile(resDir, "lab.mp3", outputDir);
					DownloadManager.downloadFile(resDir, "library.mp3", outputDir);
					DownloadManager.downloadFile(resDir, "theatre.mp3", outputDir);
				}
			}
		}

		return ready;

	}

	/**
	 * Load properties if they exist, else return the existing properties
	 *
	 * @return Properties properties of the game
	 */
	private static Properties loadProperties() {

		// Prepare variables for use
		properties = new Properties();
		FileInputStream fileIn = null;
		FileOutputStream fileOut = null;
		File propFile = new File(PROPERTIES_FILE);

		// Catch any IO issues
		try {

			// A file doesn't exist
			if (!propFile.exists()) {

				// Create the file, store default values
				Out.out.logln(InternationalisationManager.im.getMessage("main.createProp"));
				fileOut = new FileOutputStream(propFile);

				/**
				 * Used to use these to store a single reference to a player but
				 * sided against this to use the XML structure I created as it
				 * reduces repeated information but allows for multiple players.
				 */
				properties.setProperty("helpIntroductionText",
						InternationalisationManager.im.getMessage("main.introText"));
				properties.setProperty("logFile", LOG_FILES);
				properties.setProperty("logEverything", "true");
				properties.setProperty("title", "World of Zuul");
				properties.setProperty("css", "zuul_style.css");
				//properties.setProperty("resourceDirectory", RESOURCE_FILES);
				properties.setProperty("noResourceFound", "error.png");
				properties.setProperty("defaultFont", "Arial");
				properties.setProperty("guiHelpDescription",
						"Welcome to World of Zuul! You're running the GUI implementation of the game" + ". "
								+ System.lineSeparator() + System.lineSeparator()
								+ " Using the command buttons at the bottom you can interact with items,"
								+ " characters and rooms in the game. When you click one you'll be presented "
								+ "with a respective window relating to that command. If you click on an object"
								+ " in that window then you'll be able to use that command in relation to that"
								+ " object. For example, clicking Give will present you with your inventory, clicking"
								+ " the item wish to give will show you the characters in the room, clicking one will"
								+ " give the item to that character."
								+ System.lineSeparator()
								+ System.lineSeparator()
								+ "Credits: Made by Daniel Andrews"
								+ System.lineSeparator()
								+ "Music: HuniePop, Simon and Garfunkel, American Beauty"
								+ System.lineSeparator()
								+ "Images: Google respectively and myself.");

				properties.store(fileOut, "Zuul Configuration");

				// File does exist
			} else {

				Out.out.logln(InternationalisationManager.im.getMessage("main.propFound"));
				fileIn = new FileInputStream(propFile);
				// load properties
				properties.load(fileIn);

				/**
				 * check all properties exist, if any do not add them
				 */
				checkProperty("helpIntroductionText", InternationalisationManager.im.getMessage("main.introText"));
				checkProperty("logFile", LOG_FILES);
				checkProperty("logEverything", "true");
				checkProperty("title", "World of Zuul");
				checkProperty("css", "zuul_style.css");
				//checkProperty("resourceDirectory", RESOURCE_FILES);
				checkProperty("noResourceFound", "error.png");
				checkProperty("defaultFont", "Arial");
				checkProperty("guiHelpDescription",
						"Welcome to World of Zuul! You're running the GUI implementation of the game" + ". "
								+ System.lineSeparator() + System.lineSeparator()
								+ " Using the command buttons at the bottom you can interact with items,"
								+ " characters and rooms in the game. When you click one you'll be presented "
								+ "with a respective window relating to that command. If you click on an object"
								+ " in that window then you'll be able to use that command in relation to that"
								+ " object. For example, clicking Give will present you with your inventory, clicking"
								+ " the item wish to give will show you the characters in the room, clicking one will"
								+ " give the item to that character."
								+ System.lineSeparator()
								+ System.lineSeparator()
								+ "Credits: Made by Daniel Andrews"
								+ System.lineSeparator()
								+ "Music: HuniePop, Simon and Garfunkel, American Beauty"
								+ System.lineSeparator()
								+ "Images: Google respectively and myself.");

				// Save any changes made, if any properties were missing
				fileOut = new FileOutputStream(propFile);
				properties.store(fileOut, "Zuul Configuration");

			}

		} catch (Exception e) {
			Out.out.loglnErr(InternationalisationManager.im.getMessage("main.propFail"));
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {

				// Close any resources we were using
				if (fileOut != null) {
					fileOut.close();
				}
				if (fileIn != null) {
					fileIn.close();
				}
				Out.out.logln(InternationalisationManager.im.getMessage("main.closeIO"));
			} catch (IOException e) {
				Out.out.loglnErr(InternationalisationManager.im.getMessage("main.IOFail"));
				e.printStackTrace();
			}
		}
		return properties;
	}

	/**
	 * Checks to see if a property exists, if not creates the property with the
	 * assigned key and value
	 *
	 * @param key
	 *            The key
	 * @param value
	 *            The value
	 * @return true if property existed
	 */
	private static boolean checkProperty(String key, String value) {
		if (!properties.containsKey(key)) {
			Out.out.loglnErr(String.format(InternationalisationManager.im.getMessage("main.badProp"), key, value));
			properties.setProperty(key, value);
			return false;
		}
		return true;
	}

	/**
	 * Check to see if a directory exists, if not create it.
	 *
	 * @param dir
	 *            Directory to check
	 * @return true if it exists
	 */
	private static boolean checkDirectory(String dir) {
		File directory = new File(dir);

		if (!directory.exists()) {
			Out.out.logln(String.format(InternationalisationManager.im.getMessage("main.noDir"),
					directory.getAbsolutePath()));
			Out.out.logln(String.format(InternationalisationManager.im.getMessage("main.mkdir"), directory.mkdirs()));
			return false;
		}
		return true;
	}
}
