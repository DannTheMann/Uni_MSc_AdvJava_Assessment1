package advjava.assessment1.zuul.refactored;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.builtin.DebugCommand;
import advjava.assessment1.zuul.refactored.cmds.builtin.DropItemCommand;
import advjava.assessment1.zuul.refactored.cmds.builtin.GiveCommand;
import advjava.assessment1.zuul.refactored.cmds.builtin.GoCommand;
import advjava.assessment1.zuul.refactored.cmds.builtin.HelpCommand;
import advjava.assessment1.zuul.refactored.cmds.builtin.LookCommand;
import advjava.assessment1.zuul.refactored.cmds.builtin.PickUpItemCommand;
import advjava.assessment1.zuul.refactored.cmds.builtin.QuitCommand;

/**
 * Manages all instances of commands used within the game. Provides methods to
 * add commands, load plugins, and clear all existing commands as well as
 * return a collection of all existing commands.
 * 
 * Several commands are provided by default.
 * @author dja33
 *
 */
public class CommandManager {

	// Map of commans, K=command name, V=command object
	static final Map<String, Command> commands = new HashMap<>();

	/**
	 * Create an instance of the Command Manager, by default
	 * loads all default commands present
	 */
	public CommandManager() {
		loadDefaultCommands();
	}
	
	/**
	 * Attempts to load all plugins provided as a .jar in the
	 * plugins folder. Only plugins that implement the plugininterface
	 * can be loaded. 
	 */
	public void loadPlugins() {

		System.out.println();
		System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
		System.out.println(InternationalisationManager.im.getMessage("loadingPlugins"));
		System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
		
		try {		

			// Retrieve the folder for plugins
			File folder = new File(Main.PLUGIN_COMMANDS_FOLDER);
			// Create array of every file/directory within the folder
			File[] listOfFiles = folder.listFiles();

			for (File jar : listOfFiles) {

				// If the file we're looking at is a .jar file
				if (!jar.isDirectory() && jar.getName().endsWith(".jar")) {

					// Create the jar file
					JarFile jarFile = new JarFile(jar);
					Enumeration<JarEntry> e = jarFile.entries();

					// Create the class loader, used to load classes
					URL[] urls = { new URL("jar:file:" + jar.getAbsolutePath() + "!/") };
					URLClassLoader cl = URLClassLoader.newInstance(urls);

					// Iterate through every file within the .jar
					while (e.hasMoreElements()) {
						JarEntry je = e.nextElement();
						// Looking for specifically .class files to load
						if (!je.getName().endsWith(".class") || je.isDirectory()) {
							continue;
						}

						// Get the className including package path, remove the last 6 characters '.class' 
						// and replace the instances of / with .
						String className = je.getName().substring(0, je.getName().length() - 6).replace("/", ".");
						Class<?> c = cl.loadClass(className);
						Method m = null;

						try {

							// Try to get the method initialise
							m = c.getMethod("initialise", Main.game.getClass());

						} catch (NoSuchMethodException nsme) {
							// This .class file does not implement the plugininterface
							// check the next .class file
							continue;
						}

						try {
							// Try to invoke the method, pass it the game instance as a parameter
							m.invoke(c.newInstance(), Main.game);
						} catch (InvocationTargetException ite) {
							// Failed to invoke, try next .class
							System.err.println(ite.getTargetException());
							System.err.println(String.format(InternationalisationManager.im.getMessage("failedLoadPlugins"), jar.getName()));
							continue;
						}
						// We've successfully found the method we're looking for, no need to carry on looking
						// at this plugin, so print success and break out the while loop
						System.out.println(String.format(InternationalisationManager.im.getMessage("loadedClass"), c.getName()));
						break;

					}
					jarFile.close();
					
				} else {
					// An unexpected file or directory is present in the plugins folder, so we ignore it
					System.out.println(String.format(InternationalisationManager.im.getMessage("ignoreFile"), jar.getName()));
				}

			}

		} catch (Exception e) {
			// Something more serious occurred while trying to load the plugins
			System.err.println(String.format(InternationalisationManager.im.getMessage("failedReflection")));
			e.printStackTrace();
		}
		System.out.println(InternationalisationManager.im.getMessage("loadingPluginsFinished"));			

	}

	/**
	 * Load all default commands provided in the
	 * package advjava.assessment1.zuul.cmds.builtin
	 * 
	 * These commands utilise internationlisation by default
	 */
	private void loadDefaultCommands() {
		System.out.println(InternationalisationManager.im.getMessage("loadingDefaultCommands"));
		
		commands.put(InternationalisationManager.im.getMessage("loadLook"), 
				new LookCommand(InternationalisationManager.im.getMessage("loadLook"), InternationalisationManager.im.getMessage("loadLookDesc")));
		commands.put(InternationalisationManager.im.getMessage("loadGo"), 
				new GoCommand(InternationalisationManager.im.getMessage("loadGo"), InternationalisationManager.im.getMessage("loadGoDesc")));
		commands.put(InternationalisationManager.im.getMessage("loadHelp"), new HelpCommand("help",InternationalisationManager.im.getMessage("loadHelpDesc")));
		commands.put(InternationalisationManager.im.getMessage("loadPickUpItem"), 
				new PickUpItemCommand(InternationalisationManager.im.getMessage("loadPickUpItem"), InternationalisationManager.im.getMessage("loadPickUpItemDesc")));
		commands.put(InternationalisationManager.im.getMessage("loadDropItem"),
				new DropItemCommand(InternationalisationManager.im.getMessage("loadDropItem"), InternationalisationManager.im.getMessage("loadDropItemDesc")));
		commands.put(InternationalisationManager.im.getMessage("loadGive"),
				new GiveCommand(InternationalisationManager.im.getMessage("loadGive"), InternationalisationManager.im.getMessage("loadGiveDesc")));
		commands.put(InternationalisationManager.im.getMessage("loadQuit"), new QuitCommand(InternationalisationManager.im.getMessage("loadQuit"), InternationalisationManager.im.getMessage("loadQuitDesc")));
		commands.put("debug",
				new DebugCommand("debug", "Debug information on game. /debug <rooms|characters|items|general|player>"));
		
		System.out.println(InternationalisationManager.im.getMessage("loadedDefault"));
	}

	/**
	 * Get all commands currently loaded
	 * @return Collection of commands
	 */
	public Collection<Command> commands() {
		return commands.values();
	}

	/**
	 * Add a new command to the command list
	 * @param cmd The command to add
	 * @throws IllegalArgumentException if command already exists
	 **/
	public void addCommand(Command cmd) {
		if (commands.containsKey(cmd.getName())){
			throw new IllegalArgumentException(
					String.format(InternationalisationManager.im.getMessage("addCommandException"), cmd.getName()));
		}
		commands.put(cmd.getName(), cmd);
	}

	/**
	 * Get a particular command based on its command name
	 * @param name name of the command
	 * @return Command object
	 */
	public Command getCommand(String name) {
		return commands.get(name);
	}

	/**
	 * Remove all existing commands from the list of commands
	 */
	public void clearCommands() {
		commands.clear();
	}

}
