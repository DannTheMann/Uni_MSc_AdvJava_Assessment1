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
		System.out.println(InternationalisationManager.im.getMessage("loadingPlugins"));
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
							System.err.println(String.format(InternationalisationManager.im.getMessage("failedLoadPlugins"), jar.getName()));
						}
						System.out.println(String.format(InternationalisationManager.im.getMessage("loadedClass"), c.getName()));

					}
					jarFile.close();
					
				} else {
					System.out.println(String.format(InternationalisationManager.im.getMessage("ignoreFile"), jar.getName()));
				}

			}

		} catch (Exception e) {
			System.err.println(String.format(InternationalisationManager.im.getMessage("failedReflection")));
			e.printStackTrace();
		}
		System.out.println(InternationalisationManager.im.getMessage("loadingPluginsFinished"));			

	}

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

	public Collection<Command> commands() {
		return commands.values();
	}

	public void addCommand(Command cmd) {
		if (commands.containsKey(cmd.getName()))
			throw new IllegalArgumentException(
					String.format(InternationalisationManager.im.getMessage("addCommandException"), cmd.getName()));
		commands.put(cmd.getName(), cmd);
	}

	public Command getCommand(String name) {
		return commands.get(name);
	}

	public void clearCommands() {
		commands.clear();
	}

}
