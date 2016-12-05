package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.ZuulGame;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.CommandLineInterface;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.interfaces.graphical.GraphicsUtil;
import javafx.scene.control.Alert.AlertType;

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
			
			if(game.getInterface() instanceof GraphicalInterface){
				
				GraphicsUtil.showAlert(getName(), "All commands available to you.", game.getInterface().getHelpDescription(), AlertType.INFORMATION);
				
			}else{
	
				// Print out the concatenated list of commands
				game.getInterface().println(game.getInterface().getHelpDescription());
			
			}
		}

		return false;
	}

	@Override
	public boolean interfaceAcceptable(UserInterface ui) {
		return ui instanceof CommandLineInterface || ui instanceof GraphicalInterface;
	}

}
