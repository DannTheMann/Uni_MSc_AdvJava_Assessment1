package advjava.assessment1.zuul;

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
 cannot find an item in the room, does it throw an exception, 
 do nothing, print an error?

*/

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        for(Object i : System.getProperties().keySet())
            System.out.println(i + " -> " + System.getProperty((String)i));
        
        int x = 10;
        
        new Game().play();
    }
}
