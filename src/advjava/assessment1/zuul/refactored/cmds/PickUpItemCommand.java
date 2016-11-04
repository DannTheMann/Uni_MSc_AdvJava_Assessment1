package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.character.Player;

public class PickUpItemCommand extends Command{

	public PickUpItemCommand(String name, String description) {
		super(name, description);
		// TODO Auto-generated constructor stub
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
					System.out.println(String.format("You can't up: %s, you'll become overencumbered. %s Current weight: %d", item, System.lineSeparator(), game.getPlayer().getWeight()));
					return true;
				}
				
				player.addItem(item);
				room.removeItem(item);
				player.setWeight(player.getWeight()+item.getWeight());
				
				System.out.println(String.format("You picked up: %s", item));

				return true;
			}else{
				System.out.println(String.format("There is no %s in the room.", itemName));
			}
			
		}else{
			System.out.println("Pickup what?");
		}
		
		return false;
	}

	@Override
	public boolean initialise(Game game) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
