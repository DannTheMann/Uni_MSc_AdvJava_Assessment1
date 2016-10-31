package advjava.assessment1.zuul.refactored;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.exception.InvalidRoomNamingException;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via exits. The exits are labelled north, east, south, west.
 * For each direction, the room stores a reference to the neighboring room, or
 * null if there is no exit in that direction.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/*

* Map to store adjacent rooms
* List of characters in this room 
* Set exits has fixed amount of exits, what if we wanted to go upstairs?

 */
public class Room {

	private String description;

    // Exits from the room
    private Map<String, Room> rooms;
    private List<Item> items;

    // Characters in the room
    private List<Character> characters;
    private String direction;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param description The room's description.
     * @throws InvalidRoomNamingException 
     */
    public Room(String direction, String description) throws InvalidRoomNamingException {
    	if(direction == null || direction.equals(""))
    		throw new InvalidRoomNamingException();
    	this.direction = direction;
        this.description = description;
        this.items = new ArrayList<>();
        this.rooms = new HashMap<>();
    }

    public String getDirection() {
        return direction;
    }

    public void setExit(Room room, boolean override) {
        if ( (override && rooms.containsKey(room.getDirection()))
                || (!rooms.containsKey(room.getDirection()))) 
            rooms.put(room.getDirection(), room);
    }
    
    public void setExits(boolean override, Room... rooms){
    	Arrays.stream(rooms).forEach(e->setExit(e, override));
    }
    
    public Room getExit(String direction){
        return rooms.get(direction);
    }
    
    @Override
    public String toString(){
        return direction + " -> " + description + 
                System.lineSeparator() +
                items.stream().map(i->i.toString()).collect(Collectors.joining(",")); 
    }
    
    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }
    
    public boolean hasItem(Item item){
    	return items.contains(item);
    }
    
    public boolean hasItem(String itemName){
    	return items.stream()
    			.anyMatch(i->i.equals(itemName));
    }
    

    public void addItems(Item... items){
        Arrays.stream(items).forEach(i->this.items.add(i));
    }
    
    public boolean removeItem(Item item){
    	return items.remove(item);
    }
    
    public List<Item> getItems(String itemName){
        return items.stream()
        		.filter(i->i.getName().equals(itemName))
        		.collect(Collectors.toList());
    }
    
    public Item getItem(String itemName){
        return items.stream()
        		.filter(i->i.getName().equals(itemName))
        		.findFirst()
        		.orElse(null);
    }

	public void removeCharacter(Character character) {
		characters.remove(character);		
	}

	public void addCharacter(Character character) {
		characters.add(character);
	}

    /**
     * Does the room contain an item
     *
     * @param description the item
     * @ return the item's weight or 0 if none
     */
//    public int containsItem(String description) {
//        if (itemDescription.equals(description)) {
//            return itemWeight;
//        } else {
//            return 0;
//        }
//    }

    /**
     * Remove an item from the Room
     */
//    public String removeItem(String description) {
//        if (itemDescription.equals(description)) {
//            String tmp = itemDescription;
//            itemDescription = null;
//            return tmp;
//        } else {
//            System.out.println("This room does not contain" + description);
//            return null;
//        }
//    }

}
