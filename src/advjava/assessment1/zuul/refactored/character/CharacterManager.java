package advjava.assessment1.zuul.refactored.character;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import advjava.assessment1.zuul.refactored.InternationalisationManager;

/**
 * 
 * The character manager class manages all the characters in the game and gives
 * methods to allow access to characters in the game.
 * 
 * @author dja33
 *
 */
public class CharacterManager {

	private static final Map<String, Character> characters = new HashMap<>();

	/**
	 * Add a new character to the CharacterManager
	 * 
	 * @param character
	 *            to add
	 */
	public void addCharacter(Character character) {
		if (characters.containsKey(character.getName()))
			throw new IllegalArgumentException(
					String.format(InternationalisationManager.im.getMessage("cm.null"), character.getName()));
		characters.put(character.getName(), character);
	}

	/**
	 * Get a character from the CharacterManager
	 * 
	 * @param name
	 *            the name of the character
	 * @return character if present else null
	 */
	public static Character getCharacter(String name) {
		return characters.get(name);
	}

	/**
	 * Remove a character from the CharacterManager
	 * 
	 * @param charName
	 *            the character name of the character to remove
	 */
	public void removeCharacter(String charName) {
		characters.remove(charName);
	}

	/**
	 * Clear all characters from the CharacterManager
	 */
	public static void clearCharacters() {
		characters.clear();
	}

	/**
	 * Return a collection of all characters
	 * 
	 * @return Collection<Character> of characters
	 */
	public Collection<Character> characters() {
		return characters.values();
	}

}
