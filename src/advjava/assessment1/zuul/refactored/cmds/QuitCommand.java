package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;

public class QuitCommand extends Command{

	public QuitCommand(String name, String description) {
		super(name, description);
		// TODO Auto-generated constructor stub
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

	@Override
	public boolean initialise(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

}
