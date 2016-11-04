package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Player;

public class InfoCommand extends Command {

	public InfoCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		Player player = game.getPlayer();
		
		System.out.println(String.format("%s,[%d/%d~Weight]%s -> %s", player.getName(), player.getWeight(), player.getMaxWeight(), System.lineSeparator(), player.getDescription()));
		
		if(!player.getInventory().isEmpty()){
			player.getInventory().stream()
				.forEach(i->String.format("%s%s", i, System.lineSeparator()));
		}else{
			System.out.println("You have no items.");
		}
		
		return true;
	}

	@Override
	public boolean initialise(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

}
