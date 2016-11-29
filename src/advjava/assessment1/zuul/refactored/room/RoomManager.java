package advjava.assessment1.zuul.refactored.room;

import java.util.Collection;

import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;
import advjava.assessment1.zuul.refactored.utils.CollectiveManager;
import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;

/**
 * 
 * The room manager class manages all the room in the game and gives methods to
 * allow access to room in the game.
 * 
 * @author dja33
 *
 */
public class RoomManager extends CollectiveManager<Room>{

	public RoomManager() {
		super();
	}

        /**
	 * Add a room to the room manager, if the room already exists then it won't
	 * be added
	 * 
	 * @param room
	 *            The room to add
	 * @return true if room was added
	 */
	public boolean add(Room room) {
		return super.add(room.getName(), room);
	}
        
	/**
	 * Check integrity of all rooms currently loaded The integrity of each room
	 * is based on whether it has at least one exit
	 * 
	 * @throws MalformedXMLException
	 *             if at least one room is non complete
	 */
	public void checkIntegrity() throws MalformedXMLException {

		for (Room room : values()){
			if (!room.isComplete()){
				throw new MalformedXMLException(String.format(InternationalisationManager.im.getMessage("rm.badXML"),
						room.getName(), room.getExits().size(), System.lineSeparator()));
			}
		}
	}

	/**
	 * Get all rooms
	 * 
	 * @return Collection<Room> rooms
	 */
	public Collection<Room> rooms() {
		return super.values();
	}

}
