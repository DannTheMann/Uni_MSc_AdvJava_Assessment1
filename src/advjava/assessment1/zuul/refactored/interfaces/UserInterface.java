/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.interfaces;

import advjava.assessment1.zuul.refactored.Game;

/**
 *
 * @author dja33
 */
public interface UserInterface {
    
	public String getCurrentParameters();
	
    public void displayLocale(Object obj);
    public void displaylnLocale(Object obj);
    public void print(Object obj);
    public void println(Object obj);
    public void println();
    public void printErr(Object obj);
    public void printlnErr(Object obj);
    
    public void exit();  
    public boolean update(boolean actCharacters);
    public void play(Game zuulGame);

    public void showInventory();
    public void showCharacters();
    public void showRoom();
	void showExits();

	public String getHelpDescription();
     
     
}
