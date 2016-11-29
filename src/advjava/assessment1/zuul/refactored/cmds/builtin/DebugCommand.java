/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.CommandLineInterface;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;

/**
 *
 * A debug command added for the benefit of myself the creator.
 * 
 * Ideally don't mark this as it's purely just me extending my own
 * implementation to test the game itself.
 * 
 * Otherwise...
 * 
 * This command provides the utilities to test and debug information stored
 * across the game in managers and on individual aspects of the game.
 *
 * @author dja33
 */
public class DebugCommand extends Command {

	public DebugCommand(String name, String description) {
		super(name, description);
	}

	/**
	 * Provides multiple parameters for the command
	 * 
	 * Such that, you can look for information on rooms/characters/items/player
	 * @return false always, this is a debug command after all
	 */
	@Override
	public boolean action(Game game, CommandExecution cmd) {

		if (cmd.commandLength() > 1) {

			if(interfaceAcceptable(game.getInterface())){
				game.getInterface().showCharacters();
				return true;
			}
			
			String param = cmd.getWord(1);

			if (param.equalsIgnoreCase("rooms")) {

				game.getInterface().println();
				game.getInterface().println(String.format("Total rooms: %s", game.getRoomManager().rooms().size()));
				game.getRoomManager().values().stream().forEach((room) -> {
					game.getInterface()
							.println(String.format("Room: %s | exits: %s | items: %s | characters: %s", room.getName(),
									room.getExits().size(), room.getItems().size(), room.getCharacters().size()));
				});
				
				if(cmd.commandLength() > 2){
					
					game.getInterface().println("Printing them characters!");
					game.getRoomManager().get(cmd.getWord(2)).getCharacters().stream()
						.forEach(game.getInterface()::print);
					
					
					
				}

			} else if (param.equalsIgnoreCase("characters")) {

				game.getInterface().println();
				game.getInterface().println(String.format("Total players: %s", game.getCharacterManager().characters().size()));
				game.getCharacterManager().characters().stream().forEach((character) -> {
					game.getInterface().println(String.format(
							"Character: %s | room: %s | items: %s | description: %s | weight: %s | MAX_WEIGHT: %s",
							character.getName(), character.getCurrentRoom().getName(), character.getInventory().size(),
							character.getDescription(), character.getWeight(), character.getMaxWeight()));
				});

			} else if (param.equalsIgnoreCase("items")) {

				game.getInterface().println();
				game.getInterface().println(String.format("Total items: %s", game.getItemManager().items().size()));
				game.getItemManager().items().stream().forEach((item) -> {
					game.getInterface().println(String.format("Item: %s | name: %s | description: %s | weight: %s",
							item.getName(), item.getDescription(), item.getWeight()));
				});

			} else if (param.equalsIgnoreCase("player")) {

				Player player = game.getPlayer();

				game.getInterface().println();
				game.getInterface().println(String.format(
						"Player name: %s%sPlayer description: "
								+ "%s%sPlayer weight: %s%sPlayer MAX_WEIGHT: %s%sPlayer Items: %s",
						player.getName(), System.lineSeparator(), player.getDescription(), System.lineSeparator(),
						player.getWeight(), System.lineSeparator(), player.getMaxWeight(), System.lineSeparator(),
						player.getInventory()));
				
			} else {
				game.getInterface().println(String.format("Invalid use of command, %s", getDescription()));
			}

		} else {
			game.getInterface().println("Debug what?");
		}

		return false;

	}

   @Override
    public boolean interfaceAcceptable(UserInterface ui) {
        return ui instanceof CommandLineInterface || ui instanceof GraphicalInterface;
    }

}
