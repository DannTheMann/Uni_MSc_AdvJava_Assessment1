package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.CommandLineInterface;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.room.Room;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;

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
         * @return 
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
				game.getInterface().resetParameters();
				game.getInterface().println(String.format(InternationalisationManager.im.getMessage("give.notowned"), itemName));
				return false;
			}

			// If the character specified is NOT in the room
			if (character == null) {
				
				game.getInterface()
						.println(String.format(InternationalisationManager.im.getMessage("give.noone"), characterName));
				game.getInterface().resetParameters();
				game.getInterface().showCharacters(true);
				return false;
			}

			// If the character cannot hold anymore weight
			if (item.getWeight() + character.getWeight() > character.getMaxWeight()) {
				game.getInterface().displayLocale(
						String.format(InternationalisationManager.im.getMessage("give.heavy"), character.getName()));
				game.getInterface().showCharacters(true);
				game.getInterface().showInventory(true);
				return false;
			}

			// Add item to the character, remove from player, update weights
			character.addItem(item);
			character.setWeight(character.getWeight() + item.getWeight());
			player.removeItem(item);
			player.setWeight(player.getWeight() - item.getWeight());

			game.getInterface().displayLocale(String.format(InternationalisationManager.im.getMessage("give.success"),
					character.getName(), item.getName()));
			game.getInterface().resetParameters();
			game.getInterface().showCharacters(true);
			
			game.getInterface().update(false);
			return true;

		} else {
			
			// GUI Alternative
			if(game.getInterface() instanceof GraphicalInterface){
				
				if(cmd.commandLength() > 1){
					game.getInterface().showCharacters(false);				
				}else{
					//game.getInterface().showCharacters(false);		
					game.getInterface().showInventory(false);				
				}
				return false;
			}
			
			// Not enough parameters
			game.getInterface().println(InternationalisationManager.im.getMessage("give.noparam"));
		}

		return false;
	}
        
    @Override
    public boolean interfaceAcceptable(UserInterface ui) {
        return ui instanceof CommandLineInterface || ui instanceof GraphicalInterface;
    }

}
