/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.interfaces;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;

import java.util.Scanner;

/**
 *
 * @author dja33
 */
public class CommandLineInterface implements UserInterface {

    private Scanner reader; // source of command input
    private Game game;
    private String parameters;
    
    /**
     * Create a parser to read from the terminal window.
     */
    public CommandLineInterface() {
        reader = new Scanner(System.in);
    }

    /**
     * Get command that was executed
     *
     * @return The next command from the user.
     */
    public CommandExecution getCommand() {
        print("> "); // print prompt
        // Pass the entire line from scanner to reader
        parameters = reader.nextLine();
        return new CommandExecution(parameters);
    }

    /**
     * Close the scanner
     */
    public void exit() {
        reader.close();
    }
    
    @Override
    public void displayLocale(Object obj) {
        println(InternationalisationManager.im.getMessage(obj.toString()));
    }

    @Override
    public void displaylnLocale(Object obj) {
        println(InternationalisationManager.im.getMessage(obj.toString()));
    }

    @Override
    public void print(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
        
        if(Out.out.isPrintingDebugMessages())
        	Out.out.logln(obj);
    }

    @Override
    public void println() {
        println(null);
    }

    @Override
    public void printErr(Object obj) {
        System.err.print(obj);
        
        if(Out.out.isPrintingDebugMessages())
        	Out.out.logln(obj);
    }

    @Override
    public void printlnErr(Object obj) {
        System.err.println(obj);
        
        if(Out.out.isPrintingDebugMessages())
        	Out.out.logln(obj);
    }

    @Override
    public boolean update(boolean act) {
        CommandExecution command = getCommand();

        // If the command is empty of null
        if (command.isUnknown()) {
            return false;
        }

        // If the command isn't a known command
        if (game.getCommandManager().getCommand(command.getCommandWord()) == null) {
            println(game.getInternationalisationManager().getMessage("game.invalidCmd"));
            return false;
        }

        // Perform the action of the command and return whether
        // the action performed as expected
        return game.getCommandManager().getCommand(command.getCommandWord()).action(game, command);
    }

    @Override
    public void play(Game game) {
        this.game = game;
        
        println();
        println(game.getInternationalisationManager().getMessage("game.welcome1"));
        println(game.getInternationalisationManager().getMessage("game.welcome2"));
        println(game.getInternationalisationManager().getMessage("game.welcome3"));
        println();

        println(game.getPlayer().getCurrentRoom());

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.
        while (!game.hasTerminated()) {
            if (update(true)) {
                game.getCharacterManager().act(game);
            }
        }
        exit();
        System.exit(0);
    }

    @Override
    public void showInventory(boolean override) {
        println(game.getPlayer().getInventory());
    }

    @Override
    public void showCharacters(boolean override) {
        println(game.getPlayer().getCurrentRoom().getCharacters());
    }
    
    public void showRoom(boolean override){
        println(game.getPlayer().getCurrentRoom());
    }

	@Override
	public void showExits(boolean override) {
		println(game.getPlayer().getCurrentRoom().getExits());
	}

	@Override
	public String getCurrentParameters() {
		return parameters;
	}

	@Override
	public String getHelpDescription() {
		StringBuilder sb = new StringBuilder();
		
		// Go through every loaded command in the game.
		game.getCommandManager().commands().stream()
				// Filter based on whether this command is applicable
				.filter(c->c.interfaceAcceptable(game.getInterface()))
				.forEach(c->sb.append(String.format(InternationalisationManager.im.getMessage("help.list"), c.getName(),
					c.getDescription(), System.lineSeparator())));
		
		return sb.toString();
	}

	@Override
	public void resetParameters() {
		// ...
	}

}
