package advjava.assessment1.zuul.refactored.character;

import advjava.assessment1.zuul.refactored.PrintableList;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;

public class Player extends Character{

	private static final int DEFAULT_MAX_WEIGHT = 20;
	
	public Player(String name, String description, Room startingRoom, int maxWeight) throws InvalidCharacterNamingException {
		super(name, description, startingRoom, new PrintableList<>(), maxWeight);				
	}
	
	public Player(String name, String description, Room startingRoom ) throws InvalidCharacterNamingException {
		super(name, description, startingRoom, new PrintableList<>(), DEFAULT_MAX_WEIGHT);				
	}
	
	public Player(String name, Room startingRoom) throws InvalidCharacterNamingException {
		super(name, null, startingRoom, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
	}
	
	@Override
	public String toString(){
		return "[Player] " + super.toString();
	}

	@Override
	public final boolean isPlayer() {
		return true;
	}

}
