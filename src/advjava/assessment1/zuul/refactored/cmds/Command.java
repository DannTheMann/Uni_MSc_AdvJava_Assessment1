/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.cmds;

import advjava.assessment1.zuul.refactored.Game;

/**
 *
 * @author dja33
 */
public abstract class Command implements CommandExecutor{
    
    private String description;
    
    public Command(String name, String description){
        this.description = description;
    }
    
    @Override
    public abstract boolean action(Game game);
    
    @Override
    public String toString(){
		return "";
        
    }
    
}
