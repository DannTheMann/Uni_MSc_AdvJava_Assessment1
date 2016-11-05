package advjava.assessment1.zuul.refactored.character;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.PrintableList;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;

public abstract class Character{

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
		room.addCharacter(this);
	}

	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

	public boolean hasItem(String itemName) {
		return inventory.stream().anyMatch(i -> i.getName().equals(itemName));
	}

	public Item getItem(String itemName) {
		return inventory.stream().filter(i -> i.getName().equals(itemName)).findFirst().orElse(null);
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
		return String.format(InternationalisationManager.im.getMessage("c.toString"), name, description != null ? description : InternationalisationManager.im.getMessage("print.empty"),
				inventory.isEmpty() ? InternationalisationManager.im.getMessage("c.invEmpty") : inventory.toString());
	}
	
	public int getWeight() {
		return weight;
	}

	public int getMaxWeight() {
		return MAX_WEIGHT;
	}

	public abstract boolean isPlayer();

}
