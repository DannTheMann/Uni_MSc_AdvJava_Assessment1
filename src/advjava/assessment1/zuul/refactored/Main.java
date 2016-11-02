package advjava.assessment1.zuul.refactored;

import java.io.File;

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
public class Main {

	/**
	 * Singleton for game
	 */
	protected static final Game game = new Game();
	
	public static final String PLUGIN_COMMANDS_FOLDER = System.getProperty("user.dir") + System.lineSeparator() + "Plugins";
	public static final String XML_CONFIGURATION_FILES = System.getProperty("user.dir") + System.lineSeparator() + "Config";
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {   	
    	
    	System.out.println("Starting World of Zuul...");
    	makeDirectory(PLUGIN_COMMANDS_FOLDER);
    	makeDirectory(XML_CONFIGURATION_FILES);
    
    	System.out.println("Init game.");
    	Thread.sleep(1000);
        game.play();
        System.out.println("Game init.");
    }

	private static void makeDirectory(String dir) {
    	File directory = new File(dir);
    	
    	if(!directory.exists()){
    		System.out.println(dir + " did not exist, creating it now.");
    		System.out.println("created -> " + directory.mkdirs());
    	}else{
    		System.out.println(dir + " exists.");
    	}
	}
}
