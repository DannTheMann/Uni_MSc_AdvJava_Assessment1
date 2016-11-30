package advjava.assessment1.zuul.refactored.character;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.utils.CollectionManager;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;
import advjava.assessment1.zuul.refactored.utils.PrintableList;

/**
 * 
 * The character manager class manages all the characters in the game and gives
 * methods to allow access to characters in the game.
 * 
 * @author dja33
 *
 */
public class CharacterManager extends CollectionManager<Character>{

	/**
	 * Retrieves the player in the CharacterManager
	 * with the given name provided, if not found
	 * returns null.
	 * @param playerName Player name to look for
	 * @return Player if found
	 */
	public Player getPlayer(String playerName){
		Character player =  values().stream()
				.filter(Character::isPlayer)
				.filter(c->c.getName().equals(playerName))
				.findFirst()
				.orElse(null);
		return player != null ? (Player) player : null;
	}
	
	/**
	 * Get the first player, or player one if 
	 * you like. 
	 * @return The first player added, if none exist return null
	 */
	public Player getFirstPlayer(){
		Character player =  values().stream()
				.filter(Character::isPlayer)
				.findFirst()
				.orElse(null);
		return player != null ? (Player) player : null;
	}
	
	/**
	 * Add a new character to the CharacterManager
	 * 
	 * @param character
	 *            to add
	 */
	public void addCharacter(Character character) {
		if (has(character.getName()))
			throw new IllegalArgumentException(
					String.format(InternationalisationManager.im.getMessage("cm.null"), character.getName()));
		add(character.getName(), character);
	}

	/**
	 * Return a collection of all characters
	 * 
	 * @return Collection<Character> of characters
	 */
	public Collection<Character> characters() {
		return values();
	}
	
	/**
	 * Get a list of all players within the character manager
	 * @return List of players
	 */
	public List<Character> players(){
		return values().stream()
				.filter(Character::isPlayer)
				.collect(Collectors.toList());
	}
	
	/**
	 * Get a list of all non player characters within the character manager
	 * @return List of non player characters
	 */
	public List<Character> nonPlayerCharacters(){
		return values().stream()
				.filter(c->!c.isPlayer())
				.collect(Collectors.toList());
	}

	/**
	 * For every NonPlayerCharacter in the game,
	 * invoke their act method and let them do
	 * something within the game world.
	 * @param game The game instance
	 */
	public void act(Game game) {
		values().stream()
			.filter(c->!c.isPlayer())
			.forEach(c->((NonPlayerCharacter) c).act(game));
	}

}
