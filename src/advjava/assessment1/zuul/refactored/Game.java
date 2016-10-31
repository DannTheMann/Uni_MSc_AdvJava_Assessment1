package advjava.assessment1.zuul.refactored;


import advjava.assessment1.zuul.*;
import java.util.ArrayList;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * To play this game, create an instance of this class and call the "play"
 * method.
 *
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */


/*

========
COMMENTS
========

ArrayList is generic, should be list
Items and weights should be their own item, or at the least a map

Rooms should implement mapping between other rooms
-> Can 'printinfo' to avoid large clusters of unwarranted code

printWelcome and goRoom have duplication of code, slim down printWelcome and
use goRoom or vice versa

*/
public class Game {

    private Parser parser;
    private Room currentRoom;
    private int totalWeight;
    private final int MAX_WEIGHT = 10;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
        totalWeight = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room outside, theatre, pub, lab, office;

        // create the rooms
        outside = new Room("north", "outside the main entrance of the university");
        theatre = new Room("west", "in a lecture theatre");
        pub = new Room("south", "in the campus pub");
        lab = new Room("east", "in a computing lab");
        office = new Room("downstairs", "in the computing admin office");

        // initialise room exits
        
        outside.setExits(false, outside, theatre, pub, lab, office);
        outside.addItem("notebook", 2);
//        theatre.setExits(null, null, null, outside);
//        pub.setExits(null, outside, null, null);
//        lab.setExits(outside, office, null, null);
//        office.setExits(null, null, null, lab);

        currentRoom = outside;  // start game outside
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if (currentRoom.northExit != null) {
            System.out.print("north ");
        }
        if (currentRoom.eastExit != null) {
            System.out.print("east ");
        }
        if (currentRoom.southExit != null) {
            System.out.print("south ");
        }
        if (currentRoom.westExit != null) {
            System.out.print("west ");
        }
        System.out.println();
        System.out.print("Items: ");
        if (currentRoom.itemDescription != null) {
            System.out.print(currentRoom.itemDescription
                    + '(' + currentRoom.itemWeight + ')');
        }
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("take")) {
            take(command);
        } else if (commandWord.equals("drop")) {
            drop(command);
        } else if (commandWord.equals("give")) {
            give(command);
        }
        return wantToQuit;
    }

// implementations of user commands:
    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if (direction.equals("north")) {
            nextRoom = currentRoom.northExit;
        }
        if (direction.equals("east")) {
            nextRoom = currentRoom.eastExit;
        }
        if (direction.equals("south")) {
            nextRoom = currentRoom.southExit;
        }
        if (direction.equals("west")) {
            nextRoom = currentRoom.westExit;
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
            if (currentRoom.northExit != null) {
                System.out.print("north ");
            }
            if (currentRoom.eastExit != null) {
                System.out.print("east ");
            }
            if (currentRoom.southExit != null) {
                System.out.print("south ");
            }
            if (currentRoom.westExit != null) {
                System.out.print("west ");
            }
            System.out.println();
            System.out.print("Items: ");
            if (currentRoom.itemDescription != null) {
                System.out.print(currentRoom.itemDescription
                        + '(' + currentRoom.itemWeight + ')');
            }
            System.out.println();
        }
    }

    /**
     * "Look" was entered. Report what the player can see in the room
     */
    private void look() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if (currentRoom.northExit != null) {
            System.out.print("north ");
        }
        if (currentRoom.eastExit != null) {
            System.out.print("east ");
        }
        if (currentRoom.southExit != null) {
            System.out.print("south ");
        }
        if (currentRoom.westExit != null) {
            System.out.print("west ");
        }
        System.out.println();
        System.out.print("Items: ");
        if (currentRoom.itemDescription != null) {
            System.out.print(currentRoom.itemDescription
                    + '(' + currentRoom.itemWeight + ')');
        }
        System.out.println();
    }

    /**
     * Try to take an item from the current room, otherwise print an error
     * message.
     */
    private void take(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

        String item = command.getSecondWord();
        int w = currentRoom.containsItem(item);
        if (w == 0) {
            // The item is not in the room
            System.out.println("No " + item + " in the room");
            return;
        }
        if (totalWeight + w <= MAX_WEIGHT) {
            // The player is carrying too much
            System.out.println(item + " is too heavy");
            return;
        }
        // OK we can pick it up
        currentRoom.removeItem(item);
        items.add(item);
        weights.add(w);
        totalWeight += w;
    }

    /**
     * Try to drop an item, otherwise print an error message.
     */
    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        String item = command.getSecondWord();
        int i = items.indexOf(item);
        if (i == -1) {
            System.out.println("You don't have the " + item);
            return;
        }
        items.remove(i);
        int w = (Integer) weights.remove(i);
        currentRoom.addItem(item, w);
        totalWeight -= w;
    }

    /**
     * Try to drop an item, otherwise print an error message.
     */
    private void give(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to give...
            System.out.println("Give what?");
            return;
        }
        if (!command.hasThirdWord()) {
            // if there is no third word, we don't to whom to give it...
            System.out.println("Give it to who?");
            return;
        }

        String item = command.getSecondWord();
        String whom = command.getThirdWord();

        if (!currentRoom.character.equals(whom)) {
            // cannot give it if the chacter is not here
            System.out.println(whom + " is not in the room");
            return;
        }
        int i = items.indexOf(item);
        if (i == -1) {
            System.out.println("You don't have the " + item);
            return;
        }
        items.remove(i);
        int w = (Integer) weights.remove(i);
        totalWeight -= w;
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }
}
