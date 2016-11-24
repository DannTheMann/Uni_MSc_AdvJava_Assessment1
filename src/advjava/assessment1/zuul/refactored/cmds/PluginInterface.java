package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.ZuulGame;

/**
 * Used for any external jar that wishes to interact with the game without the
 * source code. (See ExampleCommand.jar in Plugins folder)
 * 
 * These jar files have at least one class that implements this interface with
 * which the CommandManager can then reflectively invoke the method.
 * 
 * For example, the provided Jar adds a command to the game without modifying
 * the existing source code.
 * 
 * @author dja33
 *
 */
public interface PluginInterface {

	/**
	 * Called when the Game loads the plugin
	 * 
	 * @param game
	 *            The game object
	 * @return true whether it was initialised correctly
	 */
	public boolean initialise(ZuulGame game);

}
