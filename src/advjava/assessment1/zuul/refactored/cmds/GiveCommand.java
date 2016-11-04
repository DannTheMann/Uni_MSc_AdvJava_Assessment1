package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.character.Player;

public class GiveCommand extends Command{

	public GiveCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		if(cmd.commandLength() > 2){
			
			String itemName = cmd.getWord(1);
			String characterName = cmd.getWord(2);
			
			Player player = game.getPlayer();
			Room room = player.getCurrentRoom();
			Character character = room.getCharacter(characterName);
			Item item = player.getItem(itemName);
			
			if(item == null){
				System.out.println(String.format("You don't have '%s'.", itemName));
				return false;
			}
			
			if(character == null){
				System.out.println(String.format("No one called'%s' is in the room.", characterName));
				return false;			
			}
			
			if(item.getWeight()+character.getWeight() > character.getMaxWeight()){
				System.out.println(String.format("%s can't carry anymore weight.", character.getName()));
				return true;
			}
			
			character.addItem(item);
			character.setWeight(character.getWeight()+item.getWeight());
			player.removeItem(item);
			player.setWeight(player.getWeight()-item.getWeight());
			
			System.out.println(String.format("You gave %s a %s.", character.getName(), item.getName()));
			return true;
		
		}else{
			System.out.print("Give what to who?");
		}
		
		return false;
	}

	@Override
	public boolean initialise(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

}
