package advjava.assessment1.zuul.refactored.cmds;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	
	static final Map<String, Command> commands = new HashMap<>();
	
	public static void addCommand(Command cmd){		
		if(commands.containsKey(cmd.getName()))
			throw new IllegalArgumentException("'" + cmd.getName() + "'Cannot add command that already exists!");
		commands.put(cmd.getName(), cmd);	
	}
	
	public static Command getCommand(String name){
		return commands.get(name);
	}
	
	public static void clearCommands(){
		commands.clear();
	}

}
