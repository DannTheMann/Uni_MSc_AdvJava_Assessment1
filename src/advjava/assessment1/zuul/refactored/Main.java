package advjava.assessment1.zuul.refactored;

import advjava.assessment1.zuul.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rej
 */

/*

* Some form of error handling with a log that may be useful to some?

* Items should be their own class as well characters
* Better documentation, some @ tags missing for method headers, for example
 in removeItem in the Room class, should ideally tell us what happens if it 
 cannot find an item in the room,help
does it throw an exception, 
 do nothing, print an error?

*/

/*

Lack of documentation for help command or look command or most commands in general

*/

public class Main {

	/**
	 * Singleton for game
	 */
	public static final Game game = new Game();
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {   	
        game.play();
    }
}
