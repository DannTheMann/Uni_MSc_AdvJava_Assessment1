/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.utils.Resource;

/**
 * Command class, all commands extend this framework to build upon.
 * 
 * It allows a name and description and provides getters for these fields.
 * 
 * All subclasses of Command must implement the action method so that they can
 * act when executed.
 * 
 * @author dja33
 */
public abstract class Command extends Resource{

	/**
	 * Create a new command.
	 * 
	 * @param name
	 *            Name of command
	 * @param description
	 *            Description of what the command does
	 */
	public Command(String name, String description) {
		super(name, description);
	}

	/**
	 * Overrided
	 * 
	 * Provides a String of the name and description on concatenated together
	 * 
	 * @return String "%name -> %description"
	 */
	@Override
	public String toString() {
		return getName() + " -> " + getDescription();
	}

	/**
	 * Overhead method, designed for subclasses to utilise for interacting with
	 * the game when the command is executed.
	 * 
	 * @param game
	 *            The game object
	 * @param cmd
	 *            The command execution
	 * @return Boolean as to whether the command was successful or incorrectly
	 *         executed
	 */
	public abstract boolean action(Game game, CommandExecution cmd);
        
    public abstract boolean interfaceAcceptable(UserInterface ui);

}
