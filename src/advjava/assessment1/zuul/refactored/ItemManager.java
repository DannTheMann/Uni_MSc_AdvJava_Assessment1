package advjava.assessment1.zuul.refactored;

import java.util.Collection;
import java.util.List;

/**
 * 
 * The item manager class manages all the items in the game and gives methods to
 * allow access to items in the game.
 * 
 * @author dja33
 *
 */
public class ItemManager {

	private final List<Item> items;

	public ItemManager() {
		items = new PrintableList<>();
	}

	/**
	 * Whether the game has the item
	 * 
	 * @param name
	 *            Name of item
	 * @return true if game has item
	 */
	public boolean hasItem(String name) {
		return items.stream().filter(i -> i.getName().equals(name)).findAny().isPresent();
	}

	/**
	 * Get item from the game
	 * 
	 * @param name
	 *            Name of item
	 * @return item if found else null
	 */
	public Item getItem(String name) {
		return items.stream().filter(i -> i.getName().equals(name)).findFirst().orElse(null);
	}

	/**
	 * Add an item to the game if it is not already in the collection
	 * 
	 * @param item
	 *            The item to add
	 * @return true if added the item
	 */
	public boolean addItem(Item item) {
		if (hasItem(item.getName()))
			return false;
		else
			items.add(item);
		return true;
	}

	/**
	 * Clear all items currently stored
	 */
	public void clearItems() {
		items.clear();
	}

	/**
	 * Remove an item from the collection
	 * 
	 * @param name
	 *            The name of the item
	 * @return true if item was removed
	 */
	public boolean removeItem(String name) {
		if (hasItem(name)) {
			items.removeIf(i -> i.getName().equals(name));
			return true;
		} else
			return false;

	}

	/**
	 * Get all items
	 * 
	 * @return collection of items
	 */
	public Collection<Item> items() {
		return items;
	}

}
