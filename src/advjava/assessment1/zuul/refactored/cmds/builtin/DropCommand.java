package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

/**
 * The Drop is designed to allow the player to drop items from their
 * inventory into the current room.
 * 
 * @author dja33
 *
 */
public class DropCommand extends Command {

	public DropCommand(String name, String description) {
		super(name, description);
	}

	/**
	 * Drop and item from the players inventory and add it to the current room.
	 */
	@Override
	public boolean action(Game game, CommandExecution cmd) {

		// Looking for a command of length > 1 e.g "dropitem apple"
		if (cmd.commandLength() > 1) {

			String itemName = cmd.getWord(1);

			// If the player has the item
			if (game.getPlayer().hasItem(itemName)) {

				// drop item
				Item item = game.getPlayer().getItem(itemName);
				Player player = game.getPlayer();
				player.getCurrentRoom().addItem(item);
				// update weight
				player.setWeight(player.getWeight() - item.getWeight());
				player.removeItem(item);
				System.out.println(String.format(InternationalisationManager.im.getMessage("dropitem.success"), item));

				return true;

			} else {
				// Player does not have this item
				System.out.println(
						String.format(InternationalisationManager.im.getMessage("dropitem.notowned"), itemName));
			}

		} else {
			// Not enough parameters
			System.out.println(InternationalisationManager.im.getMessage("dropitem.noparam"));
		}

		return false;
	}

}
