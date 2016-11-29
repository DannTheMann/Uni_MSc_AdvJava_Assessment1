package advjava.assessment1.zuul.refactored.item;

import java.util.Collection;
import java.util.List;

import advjava.assessment1.zuul.refactored.utils.CollectionManager;
import advjava.assessment1.zuul.refactored.utils.PrintableList;

/**
 * 
 * The item manager class manages all the items in the game and gives methods to
 * allow access to items in the game.
 * 
 * @author dja33
 *
 */
public class ItemManager extends CollectionManager<Item>{


	/**
	 * Add an item to the game if it is not already in the collection
	 * 
	 * @param item
	 *            The item to add
	 * @return true if added the item
	 */
	public boolean addItem(Item item) {
		if (has(item.getName()))
			return false;
		else
			add(item.getName(), item);
		return true;
	}

	/**
	 * Remove an item from the collection
	 * 
	 * @param name
	 *            The name of the item
	 * @return true if item was removed
	 */
	public boolean removeItem(String name) {
		if (has(name)) {
			remove(name);
			return true;
		} else
			return false;

	}

}
