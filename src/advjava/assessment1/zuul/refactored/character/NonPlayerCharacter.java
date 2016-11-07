package advjava.assessment1.zuul.refactored.character;

import java.util.List;

import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.PrintableList;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;

/**
 * AI controlled Character class, implements all functionality provided by it's
 * superclass Character.
 * 
 * @author Daniel
 *
 */
public class NonPlayerCharacter extends Character {

	private static final int DEFAULT_MAX_WEIGHT = 20;

	/**
	 * Default constructor for creating a NPC, provides all creation ability.
	 * 
	 * @param name
	 *            Name of NPC
	 * @param description
	 *            Description of NPC
	 * @param room
	 *            NPC room
	 * @param items
	 *            NPC items in inventory
	 * @param maxWeight
	 *            NPC maxWeight
	 * @throws InvalidCharacterNamingException
	 *             If the NPC's name is null or an empty String
	 */
	public NonPlayerCharacter(String name, String description, Room room, List<Item> items, int maxWeight)
			throws InvalidCharacterNamingException {
		super(name, description, room, items, maxWeight);
	}

	/**
	 * Second constructor, assumes MAX_WEIGHT over defined weight as parameter.
	 * 
	 * @param name
	 *            Name of NPC
	 * @param description
	 *            Description of NPC
	 * @param room
	 *            NPC room
	 * @param items
	 *            NPC items in inventory
	 * @throws InvalidCharacterNamingException
	 *             If the NPC's name is null or an empty String
	 */
	public NonPlayerCharacter(String name, String description, Room room, List<Item> items)
			throws InvalidCharacterNamingException {
		this(name, description, room, items, DEFAULT_MAX_WEIGHT);
	}

	/**
	 * Third constructor, assumes no items are provided or a maximum weight is
	 * provided and such provides an empty list as well as the
	 * DEFAULT_MAX_WEIGHT.
	 * 
	 * @param name
	 *            Name of NPC
	 * @param description
	 *            Description of NPC
	 * @param room
	 *            NPC room
	 * @throws InvalidCharacterNamingException
	 *             If the NPC's name is null or an empty String
	 */
	public NonPlayerCharacter(String name, String description, Room room) throws InvalidCharacterNamingException {
		this(name, description, room, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
	}

	/**
	 * Returns false, as this is a NPC.
	 * 
	 * @return false
	 */
	@Override
	public final boolean isPlayer() {
		return false;
	}

}
