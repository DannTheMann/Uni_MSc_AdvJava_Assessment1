package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.CommandLineInterface;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.room.Room;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.AudioManager;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;

/**
 * The Go Command is designed to move the player from the existing room to
 * another room that is an exit from the current room.
 * 
 * @author Daniel
 *
 */
public class GoCommand extends Command {

	public GoCommand(String name, String description) {
		super(name, description);
	}

	/**
	 * Move to another room from the existing room.
	 * 
	 * @return true if command executed correctly
	 */
	@Override
	public boolean action(Game game, CommandExecution cmd) {

		// If the command length is greater than 1, e.g "go east"
		if (cmd.commandLength() > 1) {

			Player player = game.getPlayer();
			Room room = player.getCurrentRoom();
			Room nextRoom = room.getExit(cmd.getWord(1).toLowerCase());

			// If the direction specified returned no exit
			if (nextRoom == null) {
				game.getInterface().println(InternationalisationManager.im.getMessage("go.noexit"));
				return false;
			}

			// Change room
			room.removeCharacter(player);
			player.setCurrentRoom(nextRoom);
			nextRoom.addCharacter(player);

			// Print room information
			game.getInterface().println();
			game.getInterface().println(nextRoom);
			game.getInterface().update(true);
			
			AudioManager.am.playSong(nextRoom);

			return true;

		} else {

			// GUI Alternative
			if (game.getInterface() instanceof GraphicalInterface) {
				game.getInterface().showExits(false);
				return false;
			}

			// Not enough params
			game.getInterface().println(InternationalisationManager.im.getMessage("go.noparam"));
		}

		return false;
	}

	@Override
	public boolean interfaceAcceptable(UserInterface ui) {
		return ui instanceof CommandLineInterface || ui instanceof GraphicalInterface;
	}

}
