package advjava.assessment1.zuul.refactored;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;
import advjava.assessment1.zuul.refactored.utils.Out;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

    // Constants for directorys and files
    public static final String PLUGIN_COMMANDS_FOLDER = System.getProperty("user.dir") + File.separator + "Plugins";
    public static final String XML_CONFIGURATION_FILES = System.getProperty("user.dir") + File.separator + "Config";
    public static final String LOG_FILES = System.getProperty("user.dir") + File.separator + "Config" + File.separator + "Logs";
    public static final String RESOURCE_FILES = System.getProperty("user.dir") + File.separator + "Resources";
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
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Out.out.setPrintingDebugMessages(true);
            Out.out.logln(InternationalisationManager.im.getMessage("main.start"));

            // Check directories for plugins/xml files
            checkDirectory(PLUGIN_COMMANDS_FOLDER);
            checkDirectory(XML_CONFIGURATION_FILES);

            // Load properties file to gather default parameters for game
            Out.out.logln(InternationalisationManager.im.getMessage("main.loadProp"));
            properties = loadProperties();

            //Out.close();
            //Out.createLogger(properties.getProperty("logFile"));
            Out.out.logln(InternationalisationManager.im.getMessage("main.finishProp"));

            // Delay to allow everything to be ready...
            Thread.sleep(1000);
            Out.out.logln(InternationalisationManager.im.getMessage("main.createSession"));
            // Create the game, initialise and start it
            game.initialiseGame(properties);
            
            Out.out.setPrintingDebugMessages(Boolean.parseBoolean(properties.getProperty("logEverything")));
            game.getInterface().println("Are we logging everything (properties file): " + Out.out.isPrintingDebugMessages());
            
            game.play();

        } catch (Exception e) {
            e.printStackTrace();
            Out.out.loglnErr("An error occurred somewhere, closing logger. " + e.getMessage());
            try {
                Out.close();
            } catch (IOException ioe) {
            }
        }

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
                properties.setProperty("resourceDirectory", RESOURCE_FILES);
                properties.setProperty("noResourceFound", "error.png");
                properties.setProperty("defaultFont", "Arial");
                
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
                checkProperty("resourceDirectory", RESOURCE_FILES);
                checkProperty("noResourceFound", "error.png");
                checkProperty("defaultFont", "Arial");

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
     * @param key The key
     * @param value The value
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
     * @param dir Directory to check
     * @return true if it exists
     */
    private static final boolean checkDirectory(String dir) {
        File directory = new File(dir);

        if (!directory.exists()) {
            Out.out.logln(String.format(InternationalisationManager.im.getMessage("main.noDir"),
                    directory.getAbsolutePath()));
            Out.out.logln(
                    String.format(InternationalisationManager.im.getMessage("main.mkdir"), directory.mkdirs()));
            return false;
        }
        return true;
    }
}
