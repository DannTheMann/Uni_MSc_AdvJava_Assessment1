package advjava.assessment1.zuul.refactored;

import java.util.HashMap;
import java.util.Map;

import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;

class RoomManager {
	
	private final Map<String, Room> rooms;
	
	public RoomManager(){
		rooms = new HashMap<>();
	}
	
	public boolean hasRoom(String name){
		return rooms.containsKey(name);
	}
	
	public Room getRoom(String name){
		return rooms.get(name);
	}
	
	public boolean addRoom(Room room){
		if(rooms.containsKey(room.getName()))
			return false;
		else
			rooms.put(room.getName(), room);
		return true;
	}
	
	public void clearRooms(){
		rooms.clear();
	}
	
	public boolean removeRoom(String name){
		if(hasRoom(name)){
			rooms.remove(name);
			return true;
		}else
			return false;
			
	}
	
	public void checkIntegrity() throws MalformedXMLException{
		
		for(Room room : rooms.values())
			if(!room.isComplete())
				throw new MalformedXMLException("XML_ROOMS", "There are some rooms that are non-persistant and only mentioned by reference. Make sure all rooms are definite! ");
		
	}

}
