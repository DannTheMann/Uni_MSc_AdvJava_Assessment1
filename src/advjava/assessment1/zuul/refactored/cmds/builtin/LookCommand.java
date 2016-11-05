/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.InternationalisationManager;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

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
    			System.out.println(String.format(InternationalisationManager.im.getMessage("look.noone"), characterName));
    		}
    		
    		return false;
    		
    	}else{
    		System.out.println(game.getPlayer().getCurrentRoom());
    	}
    	
        return true;
    } 

    
}
