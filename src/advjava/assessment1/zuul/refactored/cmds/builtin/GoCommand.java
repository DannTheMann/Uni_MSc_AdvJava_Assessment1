package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

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
				System.out.println(InternationalisationManager.im.getMessage("go.noexit"));
				return false;
			}

			// Change room
			room.removeCharacter(player);
			player.setCurrentRoom(nextRoom);
			nextRoom.addCharacter(player);

			// Print room information
			System.out.println();
			System.out.println(nextRoom);

			return true;

		} else {
			// Not enough params
			System.out.println(InternationalisationManager.im.getMessage("go.noparam"));
		}

		return false;
	}

}
