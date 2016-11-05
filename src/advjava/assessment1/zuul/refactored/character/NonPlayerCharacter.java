package advjava.assessment1.zuul.refactored.character;

import java.util.List;

import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.PrintableList;
import advjava.assessment1.zuul.refactored.Room;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;

public class NonPlayerCharacter extends Character {
		
	private static final int DEFAULT_MAX_WEIGHT = 20;
	
	public NonPlayerCharacter(String name, String description, Room room, List<Item> items, int maxWeight) throws InvalidCharacterNamingException {
		super(name, description, room, items, maxWeight);
	}

	public NonPlayerCharacter(String name, String description, Room room, List<Item> items) throws InvalidCharacterNamingException {
		super(name, description, room, items, DEFAULT_MAX_WEIGHT);
	}
	
	public NonPlayerCharacter(String name, String description, Room room) throws InvalidCharacterNamingException {
		super(name, description, room, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
	}

	@Override
	public final boolean isPlayer() {
		return false;
	}

}
