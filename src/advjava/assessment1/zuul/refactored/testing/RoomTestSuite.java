package advjava.assessment1.zuul.refactored.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import advjava.assessment1.zuul.refactored.character.NonPlayerCharacter;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.room.Room;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   RoomTest.class,
})


public class RoomTestSuite {
	
	/* static references to be used by tests */
	public static Room testRoom;
	public static Room exit;
	public static Item apple;
	public static Item pear;
	public static Player player;
	public static NonPlayerCharacter npc;
	
	/* static constants to be used by tests */
	public static final String ROOM_NAME = "example room";
	public static final String ROOM_DESCRIPTION = "this is the description";
	public static final String ROOM_URL = "here is a url";
	
	public static final String EXIT_NAME = "exit";
	public static final String EXIT_DIRECTION = "east";
	
	public static final String APPLE_NAME = "apple";
	public static final int    APPLE_WEIGHT = 10;
	
	public static final String PEAR_NAME = "pear";
	public static final int    PEAR_WEIGHT = 5;
	public static final String PEAR_DESCRIPTION = "this is a pear";
	
	public static final String PLAYER_NAME = "player bob";
	public static final int    PLAYER_MAX_WEIGHT = 15;
	
	public static final String NPC_NAME = "npc carol";
	public static final String NPC_DESCRIPTION = "carol is cool";

}
