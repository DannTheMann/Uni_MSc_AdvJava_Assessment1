package advjava.assessment1.zuul.refactored.room;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.exception.InvalidRoomNamingException;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;
import advjava.assessment1.zuul.refactored.utils.PrintableList;

/**
 * The Room class stores all the references to exits, characters and items
 * within it. It has a name and a description as well.
 * 
 * Methods are provided to retrieve information about the room. A room must not
 * have a null name, although it can have a null description.
 * 
 * @author Daniel
 *
 */
public class Room {

	// Exits from the room
	private Map<String, Room> rooms;
	private List<Item> items;

	// Characters in the room
	private List<Character> characters;

	// Room details
	private String name;
	private String description;

	/**
	 * Create a room with a name and description, an example might be "Kitchen"
	 * and "An old kitchen full of empty pans.".
	 * 
	 * The room's name cannot be null or an empty string, otherwise a
	 * InvalidRoomNamingException is thrown.
	 *
	 * @param name
	 *            The room's name.
	 * @param description
	 *            The room's description.
	 * @throws InvalidRoomNamingException
	 *             if name is null or empty
	 */
	public Room(String name, String description) throws InvalidRoomNamingException {
		if (name == null || name.equals(""))
			throw new InvalidRoomNamingException();
		this.name = name.replaceAll(" ", "");
		this.description = description;
		this.items = new PrintableList<>();
		this.characters = new PrintableList<>();
		this.rooms = new HashMap<>();
	}

	/**
	 * Set an exit in the room, an exit is another room object with a direction
	 * to exit by
	 * 
	 * @param room
	 *            The room to exit to
	 * @param direction
	 *            The direction the room is in
	 * @param override
	 *            Replace a room with the same direction if present
	 * @throws NullPointerException
	 *             if direction is null or empty
	 */
	public void setExit(Room room, String direction, boolean override) {

		if (direction == null || direction == "")
			throw new NullPointerException(InternationalisationManager.im.getMessage("room.badSetExit"));

		if ((override && rooms.containsKey(direction)) || (!rooms.containsKey(direction))) {
			rooms.put(direction, room);
		}
	}

	/**
	 * Set multiple exits use varargs.
	 * 
	 * @param override
	 *            Replace a room with the same direction if present
	 * @param direction
	 *            The direction the room is in
	 * @param room
	 *            The rooms to exit to
	 */
	@Deprecated
	public void setExits(boolean override, String direction, Room... rooms) {
		Arrays.stream(rooms).forEach(e -> setExit(e, direction, override));
	}

	/**
	 * Get an exit based on it's key value direction
	 * 
	 * @param direction
	 *            The direction the exit is in
	 * @return The room facing this direction
	 */
	public Room getExit(String direction) {
		return rooms.get(direction);
	}

	/**
	 * Return all exits in this room as collection
	 * 
	 * @return Collection<Room> all exits
	 */
	public Collection<Room> getExits() {
		return rooms.values();
	}

	/**
	 * Overrides toString()
	 * 
	 * Provides a String containing the details of the room such as it's name,
	 * description, how many items/characters/exits there are and calling their
	 * toString methods also.
	 * 
	 * @return out A formatted String containing the details of the room
	 */
	@Override
	public String toString() {

		StringBuilder out = new StringBuilder();

		out.append(String.format(InternationalisationManager.im.getMessage("room.desc1"), name,
				description != null ? System.lineSeparator() + description : "",
				(characters.isEmpty() ? InternationalisationManager.im.getMessage("room.desc2") : ""),
				System.lineSeparator()));

		out.append(InternationalisationManager.im.getMessage("room.desc3")).append(System.lineSeparator()).append("  ").append(rooms.entrySet().stream()
                        .map(e -> e.getKey().toUpperCase() + " -> " + e.getValue().name + System.lineSeparator())
                        .collect(Collectors.joining("  ")));

		out.append((!items.isEmpty()
				? InternationalisationManager.im.getMessage("room.desc4")
						+ items.stream().map(i -> i.toString()).collect(Collectors.joining(", "))
				: InternationalisationManager.im.getMessage("room.desc5")));

		out.append(".");
		if (!characters.isEmpty() && characters.stream().filter(c->!c.isPlayer()).count() > 0) {
			out.append(String.format(InternationalisationManager.im.getMessage("room.desc6"), System.lineSeparator(),
					System.lineSeparator(), characters.stream().filter((c -> !c.isPlayer())).map(i -> i.toString())
							.collect(Collectors.joining(System.lineSeparator() + "   "))));
		}

		return out.toString();
	}

	/**
	 * Description of the room
	 * 
	 * @return The description of the room.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Checks whether the room contains an item
	 * 
	 * @param item
	 *            The item instance
	 * @return true if room has item
	 */
	public boolean hasItem(Item item) {
		return items.contains(item);
	}

	/**
	 * Checks whether the room contains an item
	 * 
	 * @param itemName
	 *            The item name
	 * @return true if room has item
	 */
	public boolean hasItem(String itemName) {
		return items.stream().anyMatch(i -> i.getName().equals(itemName));
	}

	/**
	 * Add an item to the room
	 * 
	 * @param item
	 *            the item to add
	 */
	public void addItem(Item item) {
		items.add(item);
	}

	/**
	 * Add a varargs amount of items
	 * 
	 * @param items
	 *            The items to add
	 */
	public void addItems(Item... items) {
		Arrays.stream(items).forEach(i -> this.items.add(i));
	}

	/**
	 * Remove an item from the room
	 * 
	 * @param item
	 *            The item instance
	 * @return true if removed
	 */
	public boolean removeItem(Item item) {
		return items.remove(item);
	}

	/**
	 * Get all items in the room
	 * 
	 * @return Collection<Item> items in room
	 */
	public Collection<Item> getItems() {
		return items;
	}

	/**
	 * Get all characters in the room
	 * 
	 * @return Collection<Character> characters in room
	 */
	public Collection<Character> getCharacters() {
		return characters;
	}

	/**
	 * Get an item in the room
	 * 
	 * @param itemName
	 *            name of item
	 * @return item if present else null
	 */
	public Item getItem(String itemName) {
		return items.stream().filter(i -> i.getName().equals(itemName)).findFirst().orElse(null);
	}

	/**
	 * Remove a character from the room
	 * 
	 * @param character
	 *            to remove
	 * @return true if character was removed
	 */
	public boolean removeCharacter(Character character) {
		return characters.remove(character);
	}

	/**
	 * Add character to the room
	 * 
	 * @param character
	 *            to add
	 */
	public void addCharacter(Character character) {
		characters.add(character);
	}

	/**
	 * Add a list of characters to a room using varargs
	 * 
	 * @param characters
	 *            characters to add
	 */
	public void addCharacter(Character... characters) {
		for (Character c : characters)
			addCharacter(c);
	}

	/**
	 * Get the name of the room
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Verifies whether the room is 'complete' this checks whether the rooms
	 * collection is not empty as a room without exits cannot be accessed or
	 * used properly in the game.
	 * 
	 * @return true if no exits in the collection
	 */
	public boolean isComplete() {
		return !rooms.isEmpty();
	}

	/**
	 * Get a character in the room from their name
	 * 
	 * @param characterName
	 *            name of character
	 * @return
	 */
	public Character getCharacter(String characterName) {
		return characters.stream().filter(c -> c.getName().equals(characterName)).findFirst().orElse(null);
	}

}
