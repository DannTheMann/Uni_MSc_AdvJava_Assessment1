package advjava.assessment1.zuul.refactored.character;

import advjava.assessment1.zuul.refactored.PrintableList;
import advjava.assessment1.zuul.refactored.exception.InvalidCharacterNamingException;

public class Player extends Character{

	private static final int DEFAULT_MAX_WEIGHT = 20;
	private boolean currentPlayer;
	
	public Player(String name, String description, boolean currentPlayer) throws InvalidCharacterNamingException {
		super(name, description, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
		this.currentPlayer = currentPlayer; 				
	}
	
	public Player(String name, boolean currentPlayer) throws InvalidCharacterNamingException {
		super(name, null, new PrintableList<>(), DEFAULT_MAX_WEIGHT);
		this.currentPlayer = currentPlayer; 
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public String toString(){
		return "[Player] " + super.toString();
	}

	@Override
	public boolean isPlayer() {
		return currentPlayer;
	}

}
