package advjava.assessment1.zuul.refactored;

import java.util.HashMap;
import java.util.Map;

import advjava.assessment1.zuul.refactored.cmds.Command;

class CommandManager {
	
	static final Map<String, Command> commands = new HashMap<>();
	
	public static void addCommand(Command cmd){		
		if(commands.containsKey(cmd.getName()))
			throw new IllegalArgumentException(String.format("'%s'Cannot add command that already exists!", cmd.getName()));
		commands.put(cmd.getName(), cmd);	
	}
	
	public static Command getCommand(String name){
		return commands.get(name);
	}
	
	public static void clearCommands(){
		commands.clear();
	}

}
