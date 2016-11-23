/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.interfaces;

import advjava.assessment1.zuul.refactored.cmds.CommandExecution;

/**
 *
 * @author dja33
 */
public interface UserInterface {
    
    public void print(Object obj);
    public void println(Object obj);
    public void println();
    public void printErr(Object obj);
    public void printlnErr(Object obj);
    public void exit();
    public CommandExecution getCommand();
     
}
