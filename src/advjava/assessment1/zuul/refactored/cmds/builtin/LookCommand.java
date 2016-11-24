/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.CommandLineInterface;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;

/**
 * The look command reprints the rooms current information, or if a parameter is
 * specified it will try to find a character in the room to print details on
 * otherwise return an error to the player.
 * 
 * @author dja33
 */
public class LookCommand extends Command {

	public LookCommand(String name, String description) {
		super(name, description);
	}

	/**
	 * Print details about the current room, of a character if mentioned
	 * 
	 * @return false regardless, this is a passive command should not step
	 *         forward the loop
	 */
	public boolean action(Game game, CommandExecution cmd) {

		// If the command length is greater than 1, we're looking for a
		// character
		// e.g "look Tom"
		if (cmd.commandLength() > 1) {

			String characterName = cmd.getWord(1);
			Character npc = game.getPlayer().getCurrentRoom().getCharacter(characterName);

			// If we found the character
			if (npc != null) {
				game.getInterface().println(npc);
			} else {
				// print an error, no character found
				game.getInterface()
						.println(String.format(InternationalisationManager.im.getMessage("look.noone"), characterName));
			}

		} else {
			// print room details
			game.getInterface().println(game.getPlayer().getCurrentRoom());
		}

		return false;
	}
        
    @Override
    public boolean interfaceAcceptable(UserInterface ui) {
        return ui instanceof CommandLineInterface || ui instanceof GraphicalInterface;
    }

}
