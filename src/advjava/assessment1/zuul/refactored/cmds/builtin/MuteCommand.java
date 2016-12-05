package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.AudioManager;

public class MuteCommand extends Command{

	public MuteCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean action(Game game, CommandExecution cmd) {
		
		AudioManager.am.setMuted(!AudioManager.am.isMuted());
		
		return false;
	}

	@Override
	public boolean interfaceAcceptable(UserInterface ui) {
		return ui instanceof GraphicalInterface;
	}

}
