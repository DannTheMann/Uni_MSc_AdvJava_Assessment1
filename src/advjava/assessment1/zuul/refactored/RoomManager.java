package advjava.assessment1.zuul.refactored;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;

/**
 * 
 * The room manager class manages all the room in the game
 * and gives methods to allow access to room in the
 * game.
 * @author dja33
 *
 */
public class RoomManager {

	private final Map<String, Room> rooms;

	public RoomManager() {
		rooms = new HashMap<>();
	}

	/**
	 * Check whether the RoomManager has
	 * a cerain room
	 * @param name Name of room
	 * @return true if manager has room
	 */
	public boolean hasRoom(String name) {
		return rooms.containsKey(name);
	}

	/**
	 * Return room instance from manager
	 * if present
	 * @param name Name of room
	 * @return room instance
	 */
	public Room getRoom(String name) {
		return rooms.get(name);
	}

	/**
	 * Add a room to the room manager, if
	 * the room already exists then it
	 * won't be added
	 * @param room The room to add
	 * @return true if room was added
	 */
	public boolean addRoom(Room room) {
		if (rooms.containsKey(room.getName()))
			return false;
		else
			rooms.put(room.getName(), room);
		return true;
	}

	/**
	 * Clear all rooms stored in map
	 */
	public void clearRooms() {
		rooms.clear();
	}

	/**
	 * Remove a room from the room manager.
	 * @param name Name of room
	 * @return true if removed
	 */
	public boolean removeRoom(String name) {
		if (hasRoom(name)) {
			rooms.remove(name);
			return true;
		} else
			return false;

	}

	/**
	 * Check integrity of all rooms currently loaded
	 * The integrity of each room is based on whether
	 * it has at least one exit
	 * @throws MalformedXMLException if at least one room is non complete
	 */
	public void checkIntegrity() throws MalformedXMLException {

		for (Room room : rooms.values())
			if (!room.isComplete())
				throw new MalformedXMLException(String.format(
						InternationalisationManager.im.getMessage("rm.badXML"),
						 room.getName(), room.getExits().size(), System.lineSeparator()));

	}

	/**
	 * Get all rooms
	 * @return Collection<Room> rooms
	 */
	public Collection<Room> rooms() {
		return rooms.values();
	}

}
