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

			String param = cmd.getWord(1);

			if (param.equalsIgnoreCase("rooms")) {

				System.out.println();
				System.out.println(String.format("Total rooms: %s", game.getRoomManager().rooms().size()));
				game.getRoomManager().rooms().stream().forEach((room) -> {
					System.out
							.println(String.format("Room: %s | exits: %s | items: %s | characters: %s", room.getName(),
									room.getExits().size(), room.getItems().size(), room.getCharacters().size()));
				});
				
				if(cmd.commandLength() > 2){
					
					System.out.println("Printing them characters!");
					game.getRoomManager().getRoom(cmd.getWord(2)).getCharacters().stream()
						.forEach(System.out::print);
					
					
					
				}

			} else if (param.equalsIgnoreCase("characters")) {

				System.out.println();
				System.out.println(String.format("Total players: %s", game.getCharacterManager().characters().size()));
				game.getCharacterManager().characters().stream().forEach((character) -> {
					System.out.println(String.format(
							"Character: %s | room: %s | items: %s | description: %s | weight: %s | MAX_WEIGHT: %s",
							character.getName(), character.getCurrentRoom().getName(), character.getInventory().size(),
							character.getDescription(), character.getWeight(), character.getMaxWeight()));
				});

			} else if (param.equalsIgnoreCase("items")) {

				System.out.println();
				System.out.println(String.format("Total items: %s", game.getItemManager().items().size()));
				game.getItemManager().items().stream().forEach((item) -> {
					System.out.println(String.format("Item: %s | name: %s | description: %s | weight: %s",
							item.getName(), item.getDescription(), item.getWeight()));
				});

			} else if (param.equalsIgnoreCase("player")) {

				Player player = game.getPlayer();

				System.out.println();
				System.out.println(String.format(
						"Player name: %s%sPlayer description: "
								+ "%s%sPlayer weight: %s%sPlayer MAX_WEIGHT: %s%sPlayer Items: %s",
						player.getName(), System.lineSeparator(), player.getDescription(), System.lineSeparator(),
						player.getWeight(), System.lineSeparator(), player.getMaxWeight(), System.lineSeparator(),
						player.getInventory()));
				
			} else {
				System.out.println(String.format("Invalid use of command, %s", getDescription()));
			}

		} else {
			System.out.println("Debug what?");
		}

		return false;

	}

}