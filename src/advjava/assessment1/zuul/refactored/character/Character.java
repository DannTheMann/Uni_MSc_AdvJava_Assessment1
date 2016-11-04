package advjava.assessment1.zuul.refactored.character;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.PrintableList;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterItemException;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterMoveException;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;

public abstract class Character implements Actor {

	private static final String DEFAULT_DESCRIPTION = "No description available.";
	private final int MAX_WEIGHT;
	private int weight;
	private String name;
	private String description;
	private List<Item> inventory;
	private Room currentRoom;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room nextRoom) {
		if(nextRoom == null){
			return;
		}
		currentRoom = nextRoom;
	}

	public Character(String name, String description, Room room, List<Item> items, int maxWeight)
			throws InvalidCharacterNamingException {
		if (name == null || name.equals(""))
			throw new InvalidCharacterNamingException();
		this.name = name;
		this.description = description;
		this.inventory = new PrintableList<>();
		this.currentRoom = room;
		this.MAX_WEIGHT = maxWeight;
	}

	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

	public boolean hasItem(String itemName) {
		return inventory.stream().anyMatch(i -> i.equals(itemName));
	}

	public Item getItem(String itemName) {
		return inventory.stream().filter(i -> i.equals(itemName)).findFirst().orElse(null);
	}

	public void setWeight(int weight) {
		if (weight < 0) {
			this.weight = 0;
		} else {
			this.weight = weight;
			if (this.weight > MAX_WEIGHT)
				this.weight = MAX_WEIGHT;
		}
	}

	public Collection<Item> getInventory() {
		return inventory;
	}

	public boolean removeItem(String itemName) {
		return inventory.contains(itemName) ? inventory.removeIf(i -> i.getName().equals(itemName)) : false;
	}

	public boolean removeItem(Item item) {
		return inventory.remove(item);
	}

	public boolean addItem(Item item) {
		return inventory.add(item);
	}

	public void addItems(Item... items) {
		Arrays.stream(items).forEach(i -> inventory.add(i));
	}

	@Override
	public String toString() {
		return name + (description != null ? " -> " + description : "") + ". "
				+ (inventory.isEmpty() ? "They are carrying nothing." : inventory.toString() + ".");
	}
	
	public int getWeight() {
		return weight;
	}

	public int getMaxWeight() {
		return MAX_WEIGHT;
	}

	public abstract boolean isPlayer();

	@Override
	public abstract void act();

}
