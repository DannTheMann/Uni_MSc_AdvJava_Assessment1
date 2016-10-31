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
public interface CommandExecutor {
    
    /**
     * Perform a role as a command
     * @return Whether the command executed correctly
     */
    public boolean action(Game game);
    
}
