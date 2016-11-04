package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

public class DropItemCommand extends Command{

	public DropItemCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		if(cmd.commandLength() > 1){
			
			String itemName = cmd.getWord(1);
			
			if(game.getPlayer().hasItem(itemName)){
				
				Item item = game.getPlayer().getItem(itemName);	
				Player player = game.getPlayer();
				player.getCurrentRoom().addItem(item);
				player.setWeight(player.getWeight()-item.getWeight());
				player.removeItem(item);
				System.out.println(String.format("You dropped: %s.", item));
				
				return true;
				
			}else{
				System.out.println(String.format("You don't have: %s", itemName));
			}
		
		}else{
			System.out.println("Drop what?");
		}
		
		return false;
	}

}
