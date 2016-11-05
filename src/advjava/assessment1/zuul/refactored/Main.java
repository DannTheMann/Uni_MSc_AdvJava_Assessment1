package advjava.assessment1.zuul.refactored;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rej
 */

/*

* Some form of error handling with a log that may be useful to some?

* Items should be their own class as well characters
* Better documentation, some @ tags missing for method headers, for example
 in removeItem in the Room class, should ideally tell us what happens if it 
 cannot find an item in the room,help
does it throw an exception, 
 do nothing, print an error?

*/

/*

Lack of documentation for help command or look command or most commands in general

*/

// Protected

/**
 * The starting point of the game, handles 
 * directory creation for the game and folders
 * such as plugins and config files.
 * 
 * Handles creation of properties and actually
 * initialising the game.
 * @author Daniel
 *
 */
public class Main {
	
	// Constants for directorys and files
	public static final String PLUGIN_COMMANDS_FOLDER = System.getProperty("user.dir") + File.separator + "Plugins";
	public static final String XML_CONFIGURATION_FILES = System.getProperty("user.dir") + File.separator + "Config";
	private static final String PROPERTIES_FILE = XML_CONFIGURATION_FILES + File.separator + "zuul.properties";	
	
	private static Properties properties = null;
	
	/**
	 * Singleton for game
	 */
	protected static Game game;

    /**
     * Starting point for the game, checks directories exist
     * for config and plugins, else creates them, loads
     * the properties and finally initialises the game
     * and passes the properties.
     * @param args the command line arguments
     * @throws Exception if any initial problems occur further in
     */
    public static void main(String[] args) throws Exception {   
    	System.out.println(InternationalisationManager.im.getMessage("main.start"));
    	checkDirectory(PLUGIN_COMMANDS_FOLDER);
    	checkDirectory(XML_CONFIGURATION_FILES);
    	
    	System.out.println(InternationalisationManager.im.getMessage("main.loadProp"));   	
    	properties = loadProperties();
    	System.out.println(InternationalisationManager.im.getMessage("main.finishProp"));
    
    	// Delay to allow everything to be ready...
    	Thread.sleep(1000);
    	System.out.println(InternationalisationManager.im.getMessage("main.createSession"));
    	game = new Game();
    	game.initialiseGame(properties);
        game.play();

    }

    /**
     * Load properties if they exist, 
     * else return the existing properties
     * @return Properties properties of the game
     */
	private static Properties loadProperties() {
		
		properties = new Properties();
		FileInputStream fileIn = null;
		FileOutputStream fileOut = null;
		File propFile = new File(PROPERTIES_FILE);
		
		// Catch any IO issues
		try{
			
			// A file doesn't exist
			if(!propFile.exists()){
				
				// Create the file, store default values
				System.out.println(InternationalisationManager.im.getMessage("main.createProp"));
				fileOut = new FileOutputStream(propFile);
				properties.setProperty("startingRoom", "outside");
				properties.setProperty("playerName", "Richard Jones");
				properties.setProperty("playerDescription", InternationalisationManager.im.getMessage("main.pdesc"));
				properties.setProperty("playerMaxWeight", "30");
				properties.setProperty("helpIntroductionText", InternationalisationManager.im.getMessage("main.introText"));
				properties.store(fileOut, "Zuul Configuration");				
				
			// File does exist
			}else{
				
				System.out.println(InternationalisationManager.im.getMessage("main.propFound"));
				fileIn = new FileInputStream(propFile);
				// load properties
				properties.load(fileIn);

				// check all properties exist, if any do not add them
				checkProperty("startingRoom", "outside");
				checkProperty("playerName", "Richard Jones");
				checkProperty("playerDescription", InternationalisationManager.im.getMessage("main.pdesc"));
				checkProperty("playerMaxWeight", "30");
				checkProperty("helpIntroductionText", InternationalisationManager.im.getMessage("main.introText"));
				
				fileOut = new FileOutputStream(propFile);
				properties.store(fileOut, "Zuul Configuration");
				
			}
			
		}catch(Exception e){
			System.err.println(InternationalisationManager.im.getMessage("main.propFail"));
			e.printStackTrace();
			System.exit(1);
		}finally{
			try {
				
				// Close any resources we were using				
				if(fileOut!=null)
					fileOut.close();
				if(fileIn!=null)
					fileIn.close();
				System.out.println(InternationalisationManager.im.getMessage("main.closeIO"));
			} catch (IOException e) {
				System.err.println(InternationalisationManager.im.getMessage("main.IOFail"));
				e.printStackTrace();
			}
		}
		return properties;
	}

	/**
	 * Checks to see if a property exists,
	 * if not creates the property with the
	 * assigned key and value
	 * @param key The key
	 * @param value The value
	 * @return true if property existed
	 */
	private static boolean checkProperty(String key, String value) {
		if(!properties.containsKey(key)){
			System.err.println(String.format(InternationalisationManager.im.getMessage("main.badProp"), key, value));
			properties.setProperty(key, value);
			return false;
		}
		return true;
	}

	/**
	 * Check to see if a directory exists, if not
	 * create it.
	 * @param dir Directory to check
	 * @return true if it exists
	 */
	private static final boolean checkDirectory(String dir) {
    	File directory = new File(dir);
    	
    	if(!directory.exists()){
    		System.out.println(String.format(InternationalisationManager.im.getMessage("main.noDir"), directory.getAbsolutePath()));
    		System.out.println(String.format(InternationalisationManager.im.getMessage("main.mkdir"), directory.mkdirs()));
    		return false;
    	}
    	return true;
	}
}
