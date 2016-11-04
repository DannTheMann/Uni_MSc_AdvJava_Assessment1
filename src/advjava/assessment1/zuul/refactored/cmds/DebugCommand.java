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
public class DebugCommand extends Command{

    public DebugCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean action(Game game, CommandExecution cmd) {
        
        if(cmd.commandLength() > 1){
            
            String param = cmd.getWord(1);
            
            if(param.equalsIgnoreCase("rooms")){
            
                System.out.println();
                System.out.println(String.format("Total rooms: %s" 
                       , game.getRoomManager().rooms().size()));
                game.getRoomManager().rooms().stream().forEach((room) -> {
                    System.out.println(
                            String.format("Room: %s | exits: %s | items: %s | characters: %s",
                                    room.getName(), room.getExits().size(),
                                    room.getItems().size() , room.getCharacters().size()));
                });
                
            }
            
            
            return true;
        }
        
        return false;
        
    }

    @Override
    public boolean initialise(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
