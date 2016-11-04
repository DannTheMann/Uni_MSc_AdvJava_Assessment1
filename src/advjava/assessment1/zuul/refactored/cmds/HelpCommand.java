package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;

public class HelpCommand extends Command{

	public HelpCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		StringBuilder sb = new StringBuilder();
		
		for(Command c : game.getCommandManager().commands()){			
			sb.append(String.format("%s -> %s%s", c.getName(), c.getDescription(), System.lineSeparator()));		
		}
			
		System.out.println(String.format("%s%s", game.getProperty("helpIntroductionText"), System.lineSeparator()));
        System.out.println(String.format("Your command words are: %s%s", System.lineSeparator(), sb.toString()));	
		
		return true;
	}

	@Override
	public boolean initialise(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

}
