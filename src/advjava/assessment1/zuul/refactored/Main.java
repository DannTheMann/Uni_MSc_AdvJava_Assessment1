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
public class Main {
	
	public static final String PLUGIN_COMMANDS_FOLDER = System.getProperty("user.dir") + File.separator + "Plugins";
	public static final String XML_CONFIGURATION_FILES = System.getProperty("user.dir") + File.separator + "Config";
	private static final String PROPERTIES_FILE = XML_CONFIGURATION_FILES + File.separator + "zuul.properties";	
	
	private static Properties properties = null;
	
	/**
	 * Singleton for game
	 */
	protected static Game game;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {   
    	System.out.println(InternationalisationManager.im.getMessage("main.start"));
    	makeDirectory(PLUGIN_COMMANDS_FOLDER);
    	makeDirectory(XML_CONFIGURATION_FILES);
    	
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

	private static Properties loadProperties() {
		properties = new Properties();
		FileInputStream fileIn = null;
		FileOutputStream fileOut = null;
		File propFile = new File(PROPERTIES_FILE);
		try{
			
			if(!propFile.exists()){
				
				System.out.println(InternationalisationManager.im.getMessage("main.createProp"));
				fileOut = new FileOutputStream(propFile);
				properties.setProperty("startingRoom", "outside");
				properties.setProperty("playerName", "Richard Jones");
				properties.setProperty("playerDescription", InternationalisationManager.im.getMessage("main.pdesc"));
				properties.setProperty("playerMaxWeight", "30");
				properties.setProperty("helpIntroductionText", InternationalisationManager.im.getMessage("main.introText"));
				properties.store(fileOut, "Zuul Configuration");
				fileOut.close();
				
				
			}else{
				
				System.out.println(InternationalisationManager.im.getMessage("main.propFound"));
				fileIn = new FileInputStream(propFile);
				properties.load(fileIn);

				checkProperty("startingRoom", "outside");
				checkProperty("playerName", "Richard Jones");
				checkProperty("playerDescription", InternationalisationManager.im.getMessage("main.pdesc"));
				checkProperty("playerMaxWeight", "30");
				checkProperty("helpIntroductionText", InternationalisationManager.im.getMessage("main.introText"));
				
				fileOut = new FileOutputStream(propFile);
				properties.store(fileOut, "Zuul Configuration");
				fileOut.close();
				
			}
		}catch(Exception e){
			System.err.println(InternationalisationManager.im.getMessage("main.propFail"));
			e.printStackTrace();
			System.exit(1);
		}finally{
			try {
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

	private static void checkProperty(String key, String value) {
		if(!properties.containsKey(key)){
			System.err.println(String.format(InternationalisationManager.im.getMessage("main.badProp"), key, value));
			properties.setProperty(key, value);
		}
	}

	private static final void makeDirectory(String dir) {
    	File directory = new File(dir);
    	
    	if(!directory.exists()){
    		System.out.println(String.format(InternationalisationManager.im.getMessage("main.noDir"), directory.getAbsolutePath()));
    		System.out.println(String.format(InternationalisationManager.im.getMessage("main.mkdir"), directory.mkdirs()));
    	}
	}
}
