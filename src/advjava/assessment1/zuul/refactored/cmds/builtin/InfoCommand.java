package advjava.assessment1.zuul.refactored.cmds.builtin;

import java.util.stream.Collectors;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

public class InfoCommand extends Command {

	public InfoCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		Player player = game.getPlayer();
		
		System.out.println(String.format("%s,[%d/%d~Weight]%s -> %s", player.getName(), player.getWeight(), player.getMaxWeight(), System.lineSeparator(), player.getDescription()));
		
		if(!player.getInventory().isEmpty()){
			
			System.out.println(String.format("Items: %s", player.getInventory().stream()
					.map(Item::toString)
					.collect(Collectors.joining(", "))));
		}else{
			System.out.println("You have no items.");
		}
		
		return true;
	}


}
