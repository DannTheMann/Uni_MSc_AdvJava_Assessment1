package advjava.assessment1.zuul.refactored.utils;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public abstract class CollectionManager<T> {
	

	private final Map<String, T> collection;

	public CollectionManager() {
		collection = new TreeMap<>();
	}

	/**
	 * Check whether the RoomManager has a certain room
	 * 
	 * @param name
	 *            Name of room
	 * @return true if manager has room
	 */
	public boolean has(String name) {
		return collection.containsKey(name);
	}

	/**
	 * Return T instance from manager if present
	 * 
	 * @param name
	 *            Name of room
	 * @return T instance
	 */
	public T get(String name) {
		return collection.get(name);
	}

	/**
	 * Add a T to the manager, if the T already exists then it won't
	 * be added
	 * 
	 * @param T
	 *            The T to add
	 * @return true if T was added
	 */
	public void add(String key, T t) {
		collection.putIfAbsent(key, t);
	}

	/**
	 * Clear all T stored in map
	 */
	public void clear() {
		collection.clear();
	}

	/**
	 * Remove a T from the manager.
	 * 
	 * @param name
	 *            Name of T
	 * @return true if removed
	 */
	public boolean remove(String name) {
		if (has(name)) {
			collection.remove(name);
			return true;
		} else
			return false;

	}

	/**
	 * Get all T
	 * 
	 * @return Collection<T> collection
	 */
	public Collection<T> values() {
		return collection.values();
	}

}
