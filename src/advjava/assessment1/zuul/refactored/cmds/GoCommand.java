package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.character.Player;

public class GoCommand extends Command {

	public GoCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {

		if (cmd.commandLength() > 1) {

			Player player = game.getPlayer();
			Room room = player.getCurrentRoom();
			Room nextRoom = room.getExit(cmd.getWord(1).toLowerCase());

			if (nextRoom == null) {
				System.out.println("There is no exit here!");
				return false;
			}
			
			room.removeCharacter(player);
			player.setCurrentRoom(nextRoom);
			nextRoom.addCharacter(player);
			
			System.out.println(nextRoom);

			return true;

		} else {
			System.out.println("Go where?");
		}

		return false;
	}

	@Override
	public boolean initialise(Game game) {

		game.getCommandManager().addCommand(this);

		return true;
	}

}
