package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.ZuulGame;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;

/**
 * The Help Command is designed to print every command currently loaded in the
 * CommandManager including their description and name.
 * 
 * @author dja33
 *
 */
public class HelpCommand extends Command {

	public HelpCommand(String name, String description) {
		super(name, description);
	}

	/**
	 * Show all commands in the game.
	 * @return true if command is to move the game loop forward
	 */
	@Override
	public boolean action(Game game, CommandExecution cmd) {

		if(game instanceof ZuulGame){
		
			StringBuilder sb = new StringBuilder();
	
			// Go through every loaded command in the game.
			for (Command c : game.getCommandManager().commands()) {
				// Add the command name and description to the stringbuilder
				sb.append(String.format(InternationalisationManager.im.getMessage("help.list"), c.getName(),
						c.getDescription(), System.lineSeparator()));
			}
	
			// Print out the concatenated list of commands
			game.getInterface().println(String.format(InternationalisationManager.im.getMessage("help.intro"),
					((ZuulGame) game).getProperty("helpIntroductionText"), System.lineSeparator()));
			game.getInterface().println(String.format(InternationalisationManager.im.getMessage("help.print"),
					System.lineSeparator(), sb.toString()));
			
		}

		return false;
	}

}
