package advjava.assessment1.zuul.refactored.character;

import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.PrintableList;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;

/**
 * The player class represents the current player(s) and stores all information
 * about them. Extending functionality from Character superclass, it offers
 * little on top aside a few methods.
 * 
 * @author dja33
 *
 */
public class Player extends Character {

	private static final int DEFAULT_MAX_WEIGHT = 20;

	/**
	 * Default Constructor for creating a player, provides functionality to
	 * completely design player on parameters
	 * 
	 * @param name
	 *            Name of the Player
	 * @param description
	 *            Description of the player
	 * @param startingRoom
	 *            The Starting Room for the player
	 * @param maxWeight
	 *            The Maximum weight for the player
	 * @throws InvalidCharacterNamingException
	 *             The name of the player cannot be null or an empty String
	 */
	public Player(String name, String description, Room startingRoom, int maxWeight)
			throws InvalidCharacterNamingException {
		super(name, description, startingRoom, new PrintableList<>(), maxWeight);
	}

	/**
	 * Secondary Constructor for creating a player, assumes DEFAULT_MAX_WEIGHT
	 * and an empty inventory
	 * 
	 * @param name
	 *            Name of the Player
	 * @param description
	 *            Description of the player
	 * @param startingRoom
	 *            The Starting Room for the player
	 * @throws InvalidCharacterNamingException
	 *             The name of the player cannot be null or an empty String
	 */
	public Player(String name, String description, Room startingRoom) throws InvalidCharacterNamingException {
		super(name, description, startingRoom, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
	}

	/**
	 * Third Constructor for creating a player, assumes DEFAULT_MAX_WEIGHT an
	 * empty inventory, and no description
	 * 
	 * @param name
	 *            Name of the Player
	 * @param startingRoom
	 *            The Starting Room for the player
	 * @throws InvalidCharacterNamingException
	 *             The name of the player cannot be null or an empty String
	 */
	public Player(String name, Room startingRoom) throws InvalidCharacterNamingException {
		super(name, null, startingRoom, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
	}

	/**
	 * Override
	 * 
	 * Return Character toString prefixed with the player tag
	 */
	@Override
	public String toString() {
		return InternationalisationManager.im.getMessage("p.tag") + super.toString();
	}

	/**
	 * Returns true as this is a player
	 * 
	 * @return true
	 */
	@Override
	public final boolean isPlayer() {
		return true;
	}

}
