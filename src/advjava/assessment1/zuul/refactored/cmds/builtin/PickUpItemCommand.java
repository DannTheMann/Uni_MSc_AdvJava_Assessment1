package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

public class PickUpItemCommand extends Command{

	public PickUpItemCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		if(cmd.commandLength() > 1){
			
			String itemName = cmd.getWord(1);
			
			Room room = game.getPlayer().getCurrentRoom();
			
			if(room.hasItem(itemName)){
				Item item = room.getItem(itemName);
				Player player = game.getPlayer();
				
				if(player.getWeight()+item.getWeight() > player.getMaxWeight()){
					System.out.println(String.format(InternationalisationManager.im.getMessage("pickup.heavy"), item, System.lineSeparator(), game.getPlayer().getWeight()));
					return true;
				}
				
				player.addItem(item);
				room.removeItem(item);
				player.setWeight(player.getWeight()+item.getWeight());
				
				System.out.println(String.format(InternationalisationManager.im.getMessage("pickup.success"), item));

				return true;
			}else{
				System.out.println(String.format(InternationalisationManager.im.getMessage("pickup.noone"), itemName));
			}
			
		}else{
			System.out.println(InternationalisationManager.im.getMessage("pickup.noparam"));
		}
		
		return false;
	}

	

}
