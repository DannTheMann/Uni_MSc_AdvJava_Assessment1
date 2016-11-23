package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;

/**
 * Used to quit the game, calls the game.terminate() method which sets a flag to
 * end the game loop.
 * 
 * @author dja33
 *
 */
public class QuitCommand extends Command {

	public QuitCommand(String name, String description) {
		super(name, description);
	}

	/**
	 * Quit the game.
	 * @return false, pointless as the game will be terminated.
	 */
	@Override
	public boolean action(Game game, CommandExecution cmd) {

		// If the command length is greater than 0, purely used to
		// make sure the player does not call this command by accident
		if (cmd.commandLength() > 1) {
			game.getInterface().println(String.format(InternationalisationManager.im.getMessage("game.quit")));
			game.terminate(); // End the game
		} else {
			// Not enough params
			game.getInterface().println(String.format(InternationalisationManager.im.getMessage("quit.what")));
		}

		return false;
	}

}
