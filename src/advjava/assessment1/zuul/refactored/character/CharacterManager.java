package advjava.assessment1.zuul.refactored.character;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import advjava.assessment1.zuul.refactored.InternationalisationManager;


public class CharacterManager {
	
	private static final Map<String, Character> characters = new HashMap<>();
	
	public void addCharacter(Character c){		
		if(characters.containsKey(c.getName()))
			throw new IllegalArgumentException(String.format(InternationalisationManager.im.getMessage("cm.null"), c.getName()));
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
