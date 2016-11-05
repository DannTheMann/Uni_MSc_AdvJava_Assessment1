package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

public class HelpCommand extends Command{

	public HelpCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		StringBuilder sb = new StringBuilder();
		
		for(Command c : game.getCommandManager().commands()){			
			sb.append(String.format(InternationalisationManager.im.getMessage("help.list"), c.getName(), c.getDescription(), System.lineSeparator()));		
		}
			
		System.out.println(String.format(InternationalisationManager.im.getMessage("help.intro"), game.getProperty("helpIntroductionText"), System.lineSeparator()));
        System.out.println(String.format(InternationalisationManager.im.getMessage("help.print"), System.lineSeparator(), sb.toString()));	
		
		return true;
	}

}
