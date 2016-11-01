package advjava.assessment1.zuul.refactored;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.character.NonPlayerCharacter;
import advjava.assessment1.zuul.refactored.character.Player;
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

    // Exits from the room
    private Map<String, Room> rooms;
    private List<Item> items;

    // Characters in the room
    private List<Character> characters;
    private String direction;
    private String name;
	private String description;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param description The room's description.
     * @throws InvalidRoomNamingException 
     */
    public Room(String direction, String name, String description) throws InvalidRoomNamingException {
    	if(direction == null || direction.equals(""))
    		throw new InvalidRoomNamingException();
    	this.name = name;
    	this.direction = direction;
        this.description = description;
        this.items = new PrintableList<>();
        this.characters = new PrintableList<>();
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
    
    public Collection<Room> getExits(){
    	return rooms.values();
    }
    
    @Override
    public String toString(){
    	// map.entrySet().stream().map(e->e.getKey()+"->"+e.getValue()).collect(Collectors.joining(",","[","]");
    	String out = "";
    	
    	out+=String.format(" You arrive @ %s. %s %s%s%s"
    			, name, System.lineSeparator()
    			, description, 
    			(characters.isEmpty() ? " You're alone." : ""),
    			System.lineSeparator());
    	
    	out+=" Exits: " + System.lineSeparator();
    	for(String k : rooms.keySet())
    		out+= String.format("   %s -> %s%s", k.toUpperCase(), rooms.get(k).name, System.lineSeparator()); 
    	if(!items.isEmpty()){
    		out+=String.format(" Items: %s   > ", System.lineSeparator());	
	    	for(Item i : items)
	    		out+=String.format("%s, ", i);
	    	out=out.substring(0, out.length()-2)+".";
    	}else{
    		out+=" There are no items here.";
    	}
    	if(!characters.isEmpty()){
    		out+=String.format("%s Characters: %s", System.lineSeparator(), System.lineSeparator());
    		for(Character c : characters)
    			if(!c.isPlayer())
    				out+=String.format("    %s%s", c, System.lineSeparator());
    		out=out.substring(0, out.length()-2);
    	}
    	
    	return out;
    	//rooms.forEach((k,v)->out+="   " + k + " -> " + v +System.lineSeparator());
    	
//        return direction + " -> " + description + 
//                System.lineSeparator() +
//                rooms.entrySet().stream()
//                	.map(Map.Entry::getValue)
//                	.collect(Collectors.toList()).toString() + 
//                System.lineSeparator() + 
//                items.stream()
//                	.map(i->i.toString())
//                	.collect(Collectors.toList()).toString()
//                		+
//                System.lineSeparator() +
//                characters.stream()
//                		.map(i->i.toString())
//                		.collect(Collectors.toList()).toString();
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

	public void printDetails() {
		System.out.println(toString());
	}

	public void addCharacter(Character...characters) {
		for(Character c : characters)
			addCharacter(c);
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
