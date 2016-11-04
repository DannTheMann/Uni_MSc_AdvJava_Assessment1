package advjava.assessment1.zuul.refactored;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.DropItemCommand;
import advjava.assessment1.zuul.refactored.cmds.GiveCommand;
import advjava.assessment1.zuul.refactored.cmds.GoCommand;
import advjava.assessment1.zuul.refactored.cmds.HelpCommand;
import advjava.assessment1.zuul.refactored.cmds.InfoCommand;
import advjava.assessment1.zuul.refactored.cmds.LookCommand;
import advjava.assessment1.zuul.refactored.cmds.PickUpItemCommand;
import advjava.assessment1.zuul.refactored.cmds.QuitCommand;

public class CommandManager {
	
	static final Map<String, Command> commands = new HashMap<>();
	
	public CommandManager(){
		loadDefaultCommands();
		
		try{
			// reflection here
		}catch(Exception e){
			System.err.println("Failed to reflectively load command plugin ''.");
			e.printStackTrace();
		}
	}
	
	private void loadDefaultCommands(){
		System.out.println("Loading default commands...");
		commands.put("look", new LookCommand("look", "Reveal information on the surrounding room. >look [character]"));
		commands.put("go", new GoCommand("go", "Travel to another room, specifying the exit. >go <room>"));
		commands.put("help", new HelpCommand("help", "Show all available commands. >help"));
		commands.put("pickupitem", new PickUpItemCommand("pickupitem", "Pick up an item in the room. /pickup <item>"));
		commands.put("dropitem", new DropItemCommand("dropitem", "Drop item from your inventory into the room. /drop <item>"));
		commands.put("give", new GiveCommand("give", "Give an item to a character in the room. /give <item> <character>"));
		commands.put("quit", new QuitCommand("quit", "Quit the game. /quit"));
		commands.put("info", new InfoCommand("info", "Reveal information on yourself. /info"));
		System.out.println("Loaded default commands.");
	}
	
	public Collection<Command> commands(){
		return commands.values();
	}
	
	public void addCommand(Command cmd){		
		if(commands.containsKey(cmd.getName()))
			throw new IllegalArgumentException(String.format("'%s'Cannot add command that already exists!", cmd.getName()));
		commands.put(cmd.getName(), cmd);	
	}
	
	public Command getCommand(String name){
		return commands.get(name);
	}
	
	public void clearCommands(){
		commands.clear();
	}

}
