package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.room.Room;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;

/**
 * The GiveCommand is designed to allow the player to give items from their
 * inventory to other characters in the game.
 * 
 * @author dja33
 *
 */
public class GiveCommand extends Command {

	public GiveCommand(String name, String description) {
		super(name, description);
	}

	/**
	 * Give another character an item you have.
	 */
	@Override
	public boolean action(Game game, CommandExecution cmd) {

		// If the command length is greater than 2, e.g "give apple tom"
		if (cmd.commandLength() > 2) {

			String itemName = cmd.getWord(1);
			String characterName = cmd.getWord(2);

			Player player = game.getPlayer();
			Room room = player.getCurrentRoom();
			Character character = room.getCharacter(characterName);
			Item item = player.getItem(itemName);

			// If the item does NOT exist in the players inventory
			if (item == null) {
				System.out.println(String.format(InternationalisationManager.im.getMessage("give.notowned"), itemName));
				return false;
			}

			// If the character specified is NOT in the room
			if (character == null) {
				System.out
						.println(String.format(InternationalisationManager.im.getMessage("give.noone"), characterName));
				return false;
			}

			// If the character cannot hold anymore weight
			if (item.getWeight() + character.getWeight() > character.getMaxWeight()) {
				System.out.println(
						String.format(InternationalisationManager.im.getMessage("give.heavy"), character.getName()));
				return false;
			}

			// Add item to the character, remove from player, update weights
			character.addItem(item);
			character.setWeight(character.getWeight() + item.getWeight());
			player.removeItem(item);
			player.setWeight(player.getWeight() - item.getWeight());

			System.out.println(String.format(InternationalisationManager.im.getMessage("give.success"),
					character.getName(), item.getName()));
			return true;

		} else {
			// Not enough parameters
			System.out.println(InternationalisationManager.im.getMessage("give.noparam"));
		}

		return false;
	}

}
