package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.CommandLineInterface;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;

/**
 * The Drop is designed to allow the player to drop items from their inventory
 * into the current room.
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
	 * 
	 * @return true if command executed correctly
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
				game.getInterface()
						.println(String.format(InternationalisationManager.im.getMessage("dropitem.success"), item));
				game.getInterface().update(true);

				return true;

			} else {
				// Player does not have this item
				game.getInterface().println(
						String.format(InternationalisationManager.im.getMessage("dropitem.notowned"), itemName));
			}

		} else {
			// Not enough parameters
			if (game.getInterface() instanceof CommandLineInterface) {
				game.getInterface().println(InternationalisationManager.im.getMessage("dropitem.noparam"));
			} else {
				game.getInterface().showInventory(false);
			}

		}

		return false;
	}

	@Override
	public boolean interfaceAcceptable(UserInterface ui) {
		return ui instanceof CommandLineInterface || ui instanceof GraphicalInterface;
	}

}
