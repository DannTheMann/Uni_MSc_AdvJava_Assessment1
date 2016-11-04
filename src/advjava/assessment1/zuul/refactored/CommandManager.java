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

public class CommandManager {

	static final Map<String, Command> commands = new HashMap<>();

	public CommandManager() {
		loadDefaultCommands();
	}

	public void loadPlugins() {

		System.out.println();
		System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
		System.out.println("            Loading Plugins ...          ");
		System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
		try {
			

			File folder = new File(Main.PLUGIN_COMMANDS_FOLDER);
			File[] listOfFiles = folder.listFiles();

			for (File jar : listOfFiles) {

				if (!jar.isDirectory() && jar.getName().endsWith(".jar")) {

					JarFile jarFile = new JarFile(jar);
					Enumeration<JarEntry> e = jarFile.entries();

					URL[] urls = { new URL("jar:file:" + jar.getAbsolutePath() + "!/") };
					URLClassLoader cl = URLClassLoader.newInstance(urls);

					while (e.hasMoreElements()) {
						JarEntry je = e.nextElement();
						if (!je.getName().endsWith(".class") || je.isDirectory()) {
							continue;
						}

						String className = je.getName().substring(0, je.getName().length() - 6).replace("/", ".");

						Class<?> c = cl.loadClass(className);

						Method m = null;

						try {

							m = c.getMethod("initialise", Main.game.getClass());

						} catch (NoSuchMethodException nsme) {
							continue;
						}

						try {
							m.invoke(c.newInstance(), Main.game);
						} catch (InvocationTargetException ite) {
							System.err.println(ite.getTargetException());
							System.err.println(String.format("Failed to load plugin '%s'.", jar.getName()));
						}
						System.out.println(String.format("Loaded '%s'.", c.getName()));

					}
					jarFile.close();
					
				} else {
					System.out.println(String.format("Ignoring '%s' file.", jar.getName()));
				}

			}

		} catch (Exception e) {
			System.err.println("Failed to reflectively load command plugin ''.");
			e.printStackTrace();
		}
		System.out.println(" - Finished  - - - - - - - - - - - - - - ");			

	}

	private void loadDefaultCommands() {
		System.out.println("Loading default commands...");
		commands.put("look", new LookCommand("look", "Reveal information on the surrounding room. >look [character]"));
		commands.put("go", new GoCommand("go", "Travel to another room, specifying the exit. >go <room>"));
		commands.put("help", new HelpCommand("help", "Show all available commands. >help"));
		commands.put("pickupitem", new PickUpItemCommand("pickupitem", "Pick up an item in the room. /pickup <item>"));
		commands.put("dropitem",
				new DropItemCommand("dropitem", "Drop item from your inventory into the room. /drop <item>"));
		commands.put("give",
				new GiveCommand("give", "Give an item to a character in the room. /give <item> <character>"));
		commands.put("quit", new QuitCommand("quit", "Quit the game. /quit"));
		commands.put("debug",
				new DebugCommand("debug", "Debug information on game. /debug <rooms|characters|items|general|player>"));
		System.out.println("Loaded default commands.");
	}

	public Collection<Command> commands() {
		return commands.values();
	}

	public void addCommand(Command cmd) {
		if (commands.containsKey(cmd.getName()))
			throw new IllegalArgumentException(
					String.format("'%s'Cannot add command that already exists!", cmd.getName()));
		commands.put(cmd.getName(), cmd);
	}

	public Command getCommand(String name) {
		return commands.get(name);
	}

	public void clearCommands() {
		commands.clear();
	}

}
