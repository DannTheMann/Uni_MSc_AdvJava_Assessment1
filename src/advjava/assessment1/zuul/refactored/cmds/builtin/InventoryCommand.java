/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.cmds.builtin;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Main;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.interfaces.UserInterface;

/**
 *
 * @author dja33
 */
public class InventoryCommand extends Command{

    public InventoryCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean action(Game game, CommandExecution cmd) {
    
        game.getInterface().showInventory();
        
        return true;
    }

    @Override
    public boolean interfaceAcceptable(UserInterface ui) {
        return Main.game.getInterface() instanceof GraphicalInterface;
    }
    
}
