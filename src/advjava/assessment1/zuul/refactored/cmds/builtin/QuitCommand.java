package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

public class QuitCommand extends Command{

	public QuitCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		if(cmd.commandLength() > 1){
			System.out.println(String.format("Thank you for playing. Goodbye."));	
			game.terminate();
			return true;
		}else{
			
		}
		
		return false;
	}

}
