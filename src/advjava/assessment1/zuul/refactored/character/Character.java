package advjava.assessment1.zuul.refactored.character;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import advjava.assessment1.zuul.refactored.Main;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.room.Room;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;
import advjava.assessment1.zuul.refactored.utils.PrintableList;
import advjava.assessment1.zuul.refactored.utils.Resource;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Represents a superclass for any Characters in the game NonPlayerCharacter and
 * Player both extend the functionality provided by the Character Class.
 * 
 * Characters are given a weight, a declared MAX_WEIGHT, name, description,
 * inventory (Comprised as a collection of Items) and their current room that
 * they are allocated to.
 * 
 * Methods are provided to alter non-constant information such as current room,
 * inventory and weight.
 * 
 * @author dja33
 *
 */
public abstract class Character extends Resource {

	// Constant for MAX_WEIGHT, declared on constructor
	private final int MAX_WEIGHT;

	// Fields for information for each Character
	private int weight;
	private List<Item> inventory;
	private Room currentRoom;

	/**
	 * Create a new character, called from subclasses takes a series of data
	 * regarding the player.
	 * 
	 * Not all information has to be up to date.
	 * 
	 * However name must NOT be null or an empty string otherwise an error will
	 * be thrown.
	 * 
	 * @param name
	 *            The name of the Character
	 * @param description
	 *            The description of the Character
	 * @param room
	 *            The current room of the Character
	 * @param items
	 *            The items the Character is holding
	 * @param maxWeight
	 *            The maximum weight of the Character
	 * @throws InvalidCharacterNamingException
	 *             If name is null or an empty String
	 */
	public Character(String name, String description, Room room, List<Item> items, int maxWeight, String url) {
		super(name.replaceAll("", ""), description, url);
		this.inventory = items;
		this.currentRoom = room;
		this.MAX_WEIGHT = maxWeight;

		// Check to see if the user has tried to load a configuration in
		// which this character has more items of combined weight than their
		// maximum weight
		if (!inventory.isEmpty()) {
			for (Item item : inventory) {
				weight += item.getWeight();
				if (weight > MAX_WEIGHT) {
					throw new IllegalArgumentException(
							String.format(InternationalisationManager.im.getMessage("c.defaultItemsOverMaxWeight"),
									name, weight, MAX_WEIGHT));
				}
			}
		}

		room.addCharacter(this);
	}

	/**
	 * Get the characters current room
	 * 
	 * @return current room
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Set the current for the character, if null does nothing
	 * 
	 * @param nextRoom
	 *            The room to set the character too
	 */
	public void setCurrentRoom(Room nextRoom) {
		if (nextRoom == null) {
			return;
		}
		currentRoom = nextRoom;
	}

	/**
	 * Check whether a character has an item
	 * 
	 * @param item
	 *            The item
	 * @return true if they have this item
	 */
	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

	/**
	 * Check whether a character has an item
	 * 
	 * @param itemName
	 *            the name of the item
	 * @return true if they have this item
	 */
	public boolean hasItem(String itemName) {
		return inventory.stream().anyMatch(i -> i.getName().equals(itemName));
	}

	/**
	 * Get an item from the players inventory, return null if not existing
	 * 
	 * @param itemName
	 *            the name of the item
	 * @return the item else null
	 */
	public Item getItem(String itemName) {
		return inventory.stream().filter(i -> i.getName().equals(itemName)).findFirst().orElse(null);
	}

	/**
	 * Set the weight of the character, if a negative value set to 0, otherwise
	 * add weight to existing weight.
	 * 
	 * If the weight exceeds the maximum weight then set weight to MAX_WEIGHT.
	 * 
	 * @param weight
	 *            The weight to add to weight
	 */
	public void setWeight(int weight) {
		if (weight < 0) {
			this.weight = 0;
		} else {
			this.weight = weight;
			if (this.weight > MAX_WEIGHT)
				this.weight = MAX_WEIGHT;
		}
	}

	/**
	 * Return the inventory of this character as a collection
	 * 
	 * @return Collection of items
	 */
	public Collection<Resource> getInventory() {
		return inventory.stream().collect(Collectors.toCollection(PrintableList::new));
	}

	/**
	 * Remove an item from the characters inventory
	 * 
	 * @param itemName
	 *            The name of the item
	 * @return whether the item was removed
	 */
	public boolean removeItem(String itemName) {
		return inventory.contains(itemName) ? inventory.removeIf(i -> i.getName().equals(itemName)) : false;
	}

	/**
	 * Remove an item from the characters inventory
	 * 
	 * @param item
	 *            The item to remove
	 * @return whether the item was removed
	 */
	public boolean removeItem(Item item) {
		return inventory.remove(item);
	}

	/**
	 * Add an item to the characters inventory
	 * 
	 * @param item
	 *            the item to add
	 * @return true if item was added
	 */
	public boolean addItem(Item item) {
		return inventory.add(item);
	}

	/**
	 * Add a series of items to the characters inventory Accepts a varargs
	 * argument
	 * 
	 * @param items
	 *            the series of items to add
	 */
	public void addItems(Item... items) {
		Arrays.stream(items).forEach(i -> inventory.add(i));
	}

	public int getWeight() {
		return weight;
	}

	public int getMaxWeight() {
		return MAX_WEIGHT;
	}

	/**
	 * Override
	 * 
	 * Return a formatted String about this character, including a list of their
	 * inventory.
	 */
	@Override
	public String toString() {
		return String.format(InternationalisationManager.im.getMessage("c.toString"), getName(),
				getDescription() != null ? getDescription() : InternationalisationManager.im.getMessage("print.empty"),
				inventory.isEmpty() ? InternationalisationManager.im.getMessage("c.invEmpty") : inventory.toString());
	}

	/**
	 * Whether this a NonPlayerCharacter of a Player Character
	 * 
	 * @return true if Player Character
	 */
	public abstract boolean isPlayer();

	@Override
	public void applyInformation(GridPane grid, String css) {

		if(css.equals("sidepanel-room"))
			return;
		
		grid.setOnMouseClicked(GraphicalInterface.getCommandEvent(

				" " + getName(),

				Main.game.getCommandManager().getCommand("Give")));

	}

}
