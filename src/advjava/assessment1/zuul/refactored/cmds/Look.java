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
public class Look extends Command{

    public Look(String name, String description){
        super(name, description);
    }
   
    public boolean action(Game game) {
        return false;
    } 
    
    @Override
    public String toString(){
        return "";
    }

	@Override
	public boolean init(Game game) {
		return false;
	}
    
}
