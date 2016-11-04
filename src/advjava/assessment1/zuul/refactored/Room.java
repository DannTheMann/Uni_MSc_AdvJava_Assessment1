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
    private String name;
	private String description;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param description The room's description.
     * @throws InvalidRoomNamingException 
     */
    public Room(String name, String description) throws InvalidRoomNamingException {
    	if(name == null || name.equals(""))
    		throw new InvalidRoomNamingException();
    	this.name = name;
        this.description = description;
        this.items = new PrintableList<>();
        this.characters = new PrintableList<>();
        this.rooms = new HashMap<>();
    }

    public void setExit(Room room, String direction, boolean override) {
    	
    	if(direction == null || direction == "")
    		throw new NullPointerException("Direction is null! Malformed XML?");
    	
        if ( (override && rooms.containsKey(direction))
                || (!rooms.containsKey(direction))) {
            rooms.put(direction, room);
        }
    }
    
    @Deprecated
    public void setExits(boolean override, String direction, Room... rooms){
    	Arrays.stream(rooms).forEach(e->setExit(e, direction, override));
    }
    
    public Room getExit(String direction){
        return rooms.get(direction);
    }
    
    public Collection<Room> getExits(){
    	return rooms.values();
    }
    
    @Override
    public String toString(){

    	StringBuilder out = new StringBuilder();
    	
    	out.append(String.format(" You arrive @ %s. %s %s%s%s"
    			, name, System.lineSeparator()
    			, description, 
    			(characters.isEmpty() ? " You're alone." : ""),
    			System.lineSeparator()));
    	
    	out.append(" Exits: " + System.lineSeparator() + "  " + rooms.entrySet().stream()
    			.map(e->e.getKey().toUpperCase()+" -> "+e.getValue().name + System.lineSeparator())
    			.collect(Collectors.joining("  ")));
    	
    	out.append( ( !items.isEmpty() ? " Items: " + items.stream()
		.map(i->i.toString())
		.collect(Collectors.joining(", ")) :  " There are no items here") );

    	
    	out.append(".");
    	if(!characters.isEmpty()){
    		out.append(String.format("%s Characters: %s   %s", System.lineSeparator(), System.lineSeparator(), characters.stream()
    		.filter((c->!c.isPlayer()))
    		.map(i->i.toString())
    		.collect(Collectors.joining(System.lineSeparator() + "   "))));
	    	out.substring(0, out.length()-2);
    	}
    	
    	return out.toString();
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

    public void addItem(Item item) {
	items.add(item);
    }
    
    public void addItems(Item... items){
        Arrays.stream(items).forEach(i->this.items.add(i));
    }
    
    public boolean removeItem(Item item){
    	return items.remove(item);
    }
    
    public Collection<Item> getItems(){
        return items;
    }
    
    public Collection<Character> getCharacters(){
        return characters;
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

	public String getName() {
		return name;
	}

	public boolean isComplete() {
		return !rooms.isEmpty();
	}

	public Character getCharacter(String characterName) {
		return characters.stream()
				.filter(c->c.getName().equals(characterName))
				.findFirst()
				.orElse(null);
	}

}
