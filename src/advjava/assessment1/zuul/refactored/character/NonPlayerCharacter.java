package advjava.assessment1.zuul.refactored.character;

import java.util.List;

import advjava.assessment1.zuul.refactored.Item;
import advjava.assessment1.zuul.refactored.PrintableList;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;

public class NonPlayerCharacter extends Character {
		
	private static final int DEFAULT_MAX_WEIGHT = 20;
	
	public NonPlayerCharacter(String name, String description, List<Item> list, int maxWeight) throws InvalidCharacterNamingException {
		super(name, description, list, maxWeight);
	}

	public NonPlayerCharacter(String name, String description) throws InvalidCharacterNamingException {
		super(name, description, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
	}
	
	public NonPlayerCharacter(String name) throws InvalidCharacterNamingException {
		super(name, null, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

}
