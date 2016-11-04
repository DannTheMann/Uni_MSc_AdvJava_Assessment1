/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Character;

/**
 *
 * @author dja33
 */
public class LookCommand extends Command{

    public LookCommand(String name, String description){
        super(name, description);
    }
   
    public boolean action(Game game, CommandExecution cmd) {
    	
    	if(cmd.commandLength() > 1 ){
    	
    		String characterName = cmd.getWord(1); 		
    		Character npc = game.getPlayer().getCurrentRoom().getCharacter(characterName);
    		
    		if(npc != null){
    			System.out.println(npc);
    			return true;
    		}else{
    			System.out.println(String.format("There is no one here called %s.", characterName));
    		}
    		
    		return false;
    		
    	}else{
    		System.out.println(game.getPlayer().getCurrentRoom());
    	}
    	
        return true;
    } 
    
    @Override
    public String toString(){
        return "";
    }

	@Override
	public boolean initialise(Game game) {
		
		game.getCommandManager().addCommand(this);
		
		return true;
	}
    
}
