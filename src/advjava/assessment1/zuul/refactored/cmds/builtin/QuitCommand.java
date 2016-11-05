package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

public class QuitCommand extends Command{

	public QuitCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		if(cmd.commandLength() > 1){
			System.out.println(String.format(InternationalisationManager.im.getMessage("game.quit")));	
			game.terminate();
			return true;
		}else{
			System.out.println(String.format(InternationalisationManager.im.getMessage("quit.what")));
		}
		
		return false;
	}

}
