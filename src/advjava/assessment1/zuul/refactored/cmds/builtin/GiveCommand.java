package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

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
				System.out.println(String.format(InternationalisationManager.im.getMessage("give.notowned"), itemName));
				return false;
			}
			
			if(character == null){
				System.out.println(String.format(InternationalisationManager.im.getMessage("give.noone"), characterName));
				return false;			
			}
			
			if(item.getWeight()+character.getWeight() > character.getMaxWeight()){
				System.out.println(String.format(InternationalisationManager.im.getMessage("give.heavy"), character.getName()));
				return true;
			}
			
			character.addItem(item);
			character.setWeight(character.getWeight()+item.getWeight());
			player.removeItem(item);
			player.setWeight(player.getWeight()-item.getWeight());
			
			System.out.println(String.format(InternationalisationManager.im.getMessage("give.success"), character.getName(), item.getName()));
			return true;
		
		}else{
			System.out.println(InternationalisationManager.im.getMessage("give.noparam"));
		}
		
		return false;
	}

}
