package advjava.assessment1.zuul.refactored.item;

import advjava.assessment1.zuul.refactored.utils.CollectionManager;
import advjava.assessment1.zuul.refactored.utils.ResourceManager;

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

	public Item createNewItem(String name) {
		
		if(has(name)){
			Item original = get(name);
			Item copy = new Item(original.getName(), original.getDescription(), 
					original.getWeight(), original.getRawImageURL());
			if(ResourceManager.isLoaded())
				copy.loadImage(ResourceManager.getResourceManager().getImage(copy.getResourceName()));
			return copy;
		}
		
		return null;
	}

}
