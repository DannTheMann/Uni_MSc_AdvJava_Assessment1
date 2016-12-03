package advjava.assessment1.zuul.refactored.character;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.room.Room;
import advjava.assessment1.zuul.refactored.utils.PrintableList;
import advjava.assessment1.zuul.refactored.utils.Resource;

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
	 * Passing a maxWeight <= 0 will establish the DEFAULT_MAX_WEIGHT for the
	 * NPC instead.
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
	public NonPlayerCharacter(String name, String description, Room room, List<Item> items, int maxWeight, String url)
			throws InvalidCharacterNamingException {
		super(name, description, room, items, maxWeight <= 0 ? DEFAULT_MAX_WEIGHT : maxWeight, url);
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
		this(name, description, room, items, DEFAULT_MAX_WEIGHT, null);
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
	 * @param url 
	 * @param weight 
	 * @param items 
	 * @throws InvalidCharacterNamingException
	 *             If the NPC's name is null or an empty String
	 */
	public NonPlayerCharacter(String name, String description, Room room, List<Item> items, int weight) throws InvalidCharacterNamingException {
		this(name, description, room, new PrintableList<>(), DEFAULT_MAX_WEIGHT, null);
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

	@Override
	public String getDescription() {
		return super.getDescription() + System.lineSeparator() + (getInventory().isEmpty() ? "They have no items."
				: "Weight [" + getWeight() + " / " + getMaxWeight() + "]" + System.lineSeparator() +

						"They have: " + System.lineSeparator() + " > " + getInventory().stream().map(Resource::getName)
								.collect(Collectors.joining(System.lineSeparator() + " > ")));

	}

	// For moving between rooms
	private static Random randomGenerator = new Random();

	/**
	 * Let the NPC act out a behaviour, by default this method will generate a
	 * random value to determine whether this NPC should move rooms. To
	 * implement different behaviour create a subclass of NonPlayerCharacter and
	 * Override this functionality.
	 * 
	 * @param game
	 *            The game instance
	 */
	public void act(Game game) {

		// 50/50 chance to move rooms
		if (randomGenerator.nextDouble() >= 0.5) {

			Room room = getCurrentRoom();
			Room exit = (Room) room.getExits().toArray()[randomGenerator.nextInt(room.getExits().size())];
			if (exit == null) {
				return;
			}
			room.removeCharacter(this);
			setCurrentRoom(exit);
			exit.addCharacter(this);

		}
		// else do nothing

	}

}
