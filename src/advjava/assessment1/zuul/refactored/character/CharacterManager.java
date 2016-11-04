package advjava.assessment1.zuul.refactored.character;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CharacterManager {
	
	private static final Map<String, Character> characters = new HashMap<>();
	
	public void addCharacter(Character c){		
		if(characters.containsKey(c.getName()))
			throw new IllegalArgumentException("'" + c.getName() + "'Cannot add Character that already exists!");
		characters.put(c.getName(), c);	
	}
	
	public static Character getCharacter(String name){
		return characters.get(name);
	}
	
	public static void clearCharacters(){
		characters.clear();
	}

	public Collection<Character> characters() {
		return characters.values();
	}

}
