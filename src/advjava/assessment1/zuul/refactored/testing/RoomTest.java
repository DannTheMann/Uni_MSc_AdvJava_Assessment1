package advjava.assessment1.zuul.refactored.testing;

/* static imports of constants */
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.APPLE_NAME;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.APPLE_WEIGHT;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.EXIT_DIRECTION;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.EXIT_NAME;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.NPC_DESCRIPTION;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.NPC_NAME;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.PEAR_DESCRIPTION;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.PEAR_NAME;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.PEAR_WEIGHT;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.PLAYER_MAX_WEIGHT;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.PLAYER_NAME;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.ROOM_DESCRIPTION;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.ROOM_NAME;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.ROOM_URL;

/* static imports for all variables needed for these test cases */
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.apple;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.exit;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.npc;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.pear;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.player;
import static advjava.assessment1.zuul.refactored.testing.RoomTestSuite.testRoom;

import java.nio.file.Paths;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import advjava.assessment1.zuul.refactored.character.NonPlayerCharacter;
import advjava.assessment1.zuul.refactored.character.Player;
import advjava.assessment1.zuul.refactored.exception.InvalidRoomNamingException;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.room.Room;
import advjava.assessment1.zuul.refactored.utils.Resource;
import javafx.scene.layout.GridPane;
import advjava.assessment1.zuul.refactored.character.Character;

public class RoomTest {

	@BeforeClass
	public static void setUpObjects() {
		RoomTestRunner.println("Creating Test class, creating testRoom, testExit, testItems and testCharacters.");
		testRoom = new Room(ROOM_NAME, ROOM_DESCRIPTION, ROOM_URL);
		exit = new Room(EXIT_NAME, null, null);

		apple = new Item(APPLE_NAME, APPLE_WEIGHT);
		pear = new Item(PEAR_NAME, PEAR_DESCRIPTION, PEAR_WEIGHT);

		player = new Player(PLAYER_NAME, testRoom, PLAYER_MAX_WEIGHT);
		npc = new NonPlayerCharacter(NPC_NAME, NPC_DESCRIPTION, testRoom);
	}

	@Before
	public void setUpTest() {
		testRoom.addCharacter(npc);
		testRoom.addCharacter(player);
		testRoom.addItems(apple, pear);

		testRoom.setExit(exit, EXIT_DIRECTION, false);
	}

	@After
	public void tearDownTest() {
		// ...
	}

	@AfterClass
	public static void tearDownObjects() {
		RoomTestRunner.println("Revoking all objects, invoking GC.");
		testRoom = null;
		exit = null;
		apple = null;
		pear = null;
		player = null;
		npc = null;
		// Ask the JVM Garbage Collector to run.
		System.gc();
		// ...
	}

	/* Room << Resource << Descriptor 
	 * Leading importance of inheritance, where Descriptor
	 * is the top class with resource and room extending
	 * from it.  */

	/**
	 * Tests performed in the Descriptor superclass
	 */

	@Test
	public void updateThemeWhenNull(){
		RoomTestRunner.println("Checking update (theme), using null data.");
		
		Room dummy = new Room("new",null,null,null);
		boolean res = dummy.update("NEW", "NAME", "URL", "THEME");
		Assert.assertTrue(res);
	}
	
	@Test
	public void updateWhenAllValuesAreSet(){
		RoomTestRunner.println("Checking update (all values set), using null data.");
		
		Room dummy = new Room("new", "description", "url", "theme");
		boolean res = dummy.update("NEW", "NAME");
		Assert.assertFalse(res);
	}

	@Test
	public void updateDescriptionWhenNull(){
		RoomTestRunner.println("Checking update (description), using null data.");
		
		Room dummy = new Room("new", null, null, null);
		dummy.update("NEW", "NAME");
		Assert.assertEquals("NAME", dummy.getDescription());
	}
	
	@Test(expected=NullPointerException.class)
	public void updateNameWhenNull(){
		RoomTestRunner.println("Checking update (name), using null data.");
		
		Room dummy = new Room(null, "description", null, null);
		dummy.update("NEW", "NAME");
		Assert.assertEquals("NEW", dummy.getName());
	}
	
	@Test
	public void getRawDescription(){
		RoomTestRunner.println("Checking getRawName returns ROOM_NAME with '_'");
		String rawDescription = testRoom.getRawDescription();
		Assert.assertEquals(ROOM_DESCRIPTION, rawDescription);
	}
	
	@Test
	public void getRawName(){
		RoomTestRunner.println("Checking getRawName returns ROOM_NAME with '_'");
		String rawName = testRoom.getRawName();
		Assert.assertEquals(ROOM_NAME.replaceAll(" ", "_"), rawName);
	}
	
	@Test
	public void descriptorToString(){
		RoomTestRunner.println("Checking descriptor.classToString returns ROOM_NAME -> ROOM_DESCRIPTION");
		String dts = testRoom.getDescription();
		Assert.assertEquals(ROOM_NAME + " -> " + ROOM_DESCRIPTION, dts);
	}
	
	@Test
	public void getDescription(){
		RoomTestRunner.println("Checking getDescription returns ROOM_DESCRIPTION");
		String des = testRoom.getDescription();
		Assert.assertEquals(ROOM_DESCRIPTION, des);
	}
	
	@Test
	public void getName(){
		RoomTestRunner.println("Checking getName returns ROOM_NAME");
		String name = testRoom.getName();
		Assert.assertEquals(ROOM_NAME, name);
	}
	
	/**
	 * Tests performed in the Resource superclass
	 */
	
	@Test
	public void getRawURL(){
		RoomTestRunner.println("Checking update (url), using URL data.");
		
		Room dummy = new Room("new", "des", "someRawURL");
		Assert.assertEquals("someRawURL", dummy.getRawImageURL());
	}
	
	@Test
	public void updateResourceURL(){
		RoomTestRunner.println("Checking update (url), using URL data.");
		
		Room dummy = new Room("new", "des", null);
		dummy.update("NEW", "NAME", "url");
		Assert.assertEquals(Paths.get("url").getFileName().toString(), dummy.getImageURL());
	}
	
	@Test
	public void updateResourceNullURL(){
		RoomTestRunner.println("Checking update NULL (url), using null data.");
		
		Room dummy = new Room("new", "des", null);
		dummy.update("NEW", "NAME", null);
		Assert.assertEquals(dummy.getImageURL(), null);
	}

	@Test
	public void updateResourceURLWhenAlreadySet(){
		RoomTestRunner.println("Checking update (url), using URL data.");
		
		Room dummy = new Room("new", "des", "url");
		dummy.update("NEW", "NAME", "URL");
		Assert.assertEquals(Paths.get("url").getFileName().toString(), dummy.getImageURL());
	}
	
	@Test(expected=NullPointerException.class)
	public void updateURLWhenNull(){
		RoomTestRunner.println("Checking update (url), using null data.");
		
		Room dummy = new Room(null, null, "url");
		dummy.update("NEW", "NAME", "URL");
		Assert.assertEquals("NEW", dummy.getName());
	}
	
	@Test
	@Ignore
	public void getResourceName() {
		RoomTestRunner.println("Checking getName returns ROOM_NAME");
		String name = testRoom.getResourceName();
		Assert.assertEquals(ROOM_NAME, name);
	}
	
	/**
	 * Tests performed in the Room class
	 */

	@Test(expected = InvalidRoomNamingException.class)
	public void nullParametersConstructor() {
		RoomTestRunner.println("Checking nullParametersConstructor: expecting InvalidRoomNamingException");
		new Room(null, null, null);
	}
	
	@Test
	public void correctUsageConstructor() {
		RoomTestRunner.println("Creating Room with expected details...");
		Room room = new Room(ROOM_NAME, ROOM_DESCRIPTION, ROOM_URL);
		Assert.assertNotNull(room);
		Assert.assertEquals(ROOM_NAME, room.getName());
		Assert.assertEquals(ROOM_DESCRIPTION, room.getDescription());
		Assert.assertEquals(ROOM_URL, room.getRawImageURL());
	}
	
	@Test
	public void veryLongRoomName() {
		RoomTestRunner.println("Creating Room with a very long name...");
		Room room = new Room(ROOM_NAME+ROOM_NAME+ROOM_NAME+ROOM_NAME+ROOM_NAME, ROOM_DESCRIPTION, ROOM_URL);
		Assert.assertNotNull(room);
		Assert.assertEquals(ROOM_NAME+ROOM_NAME+ROOM_NAME+ROOM_NAME+ROOM_NAME, room.getName());
	}

	@Test(expected = InvalidRoomNamingException.class)
	public void nullParametersSecondConstructor() {
		RoomTestRunner.println("Checking nullParametersConstructor: expecting InvalidRoomNamingException");
		new Room(null, null, null, null);
	}
	
	@Test
	public void correctUsageSecondConstructor() {
		RoomTestRunner.println("Creating Room with expected details...");
		Room room = new Room(ROOM_NAME, ROOM_DESCRIPTION, ROOM_URL, "THEME");
		Assert.assertNotNull(room);
		Assert.assertEquals(ROOM_NAME, room.getName());
		Assert.assertEquals(ROOM_DESCRIPTION, room.getDescription());
		Assert.assertEquals(ROOM_URL, room.getRawImageURL());
		Assert.assertEquals("THEME", room.getTheme());
	}
	
	@Test(expected = NullPointerException.class)
	public void setExitWithNullDirection() {
		RoomTestRunner.println("Checking nullParametersConstructor: expecting NullPointerException");
		testRoom.setExit(exit, null, false);
	}

	@Test(expected = NullPointerException.class)
	public void setExitWithNullRoom() {
		RoomTestRunner.println("Setting exit with a null room: expecting NullPointerException");
		testRoom.setExit(null, EXIT_DIRECTION, false);
	}

	@Test
	public void setExit() {
		RoomTestRunner.println("Setting dummy exit 'a' and evaluating whether it was added.");
		Room dummy = new Room("a", "b", "c");
		boolean result = testRoom.setExit(dummy, "na", false);
		Assert.assertTrue(result);
	}

	@Test
	public void overrideExitIfExists() {
		RoomTestRunner.println("Adding existing exit, testing whether we override the existing with the new.");
		boolean result = testRoom.setExit(exit, EXIT_DIRECTION, true);
		Assert.assertTrue(result);
	}

	@Test
	public void doNotOverrideExitIfExists() {
		RoomTestRunner.println("Adding existing exit, testing whether we DON'T override the existing with the new.");
		boolean result = testRoom.setExit(exit, EXIT_DIRECTION, false);
		Assert.assertFalse(result);
	}

	@Test
	public void removeExitByDirection() {
		RoomTestRunner.println("Adding a dummy exit, then removing it using by direction.");
		createAndSetDummyRoom();
		boolean result = testRoom.removeExitByDirection("direction");
		Assert.assertTrue(result);
	}

	@Test
	public void removeExitByRoom() {
		RoomTestRunner.println("Adding a dummy exit, then removing it using by room reference.");
		Room dummy = createAndSetDummyRoom();
		boolean result = testRoom.removeExitByRoom(dummy);
		Assert.assertTrue(result);
	}

	@Test
	public void removeExitByRoomName() {
		RoomTestRunner.println("Adding a dummy exit, then removing it using by room name.");
		Room dummy = createAndSetDummyRoom();
		boolean result = testRoom.removeExitByRoomName(dummy.getName());
		Assert.assertTrue(result);
	}

	private Room createAndSetDummyRoom() {
		Room dummy = new Room("room1", "direction", "c");
		testRoom.setExit(dummy, "direction", true);
		return dummy;
	}

	@Test
	public void getExit() {
		RoomTestRunner.println("Getting an exit we added earlier, checking whether it's null.");
		Room room = testRoom.getExit(EXIT_DIRECTION);
		Assert.assertNotNull(room);
	}

	@Test
	public void getInvalidExit() {
		RoomTestRunner.println("Getting an invalidExit, checking whether it returns null.");
		Room room = testRoom.getExit("invalidExit");
		Assert.assertNull(room);
	}

	@Test(expected = NullPointerException.class)
	public void getNullInputExit() {
		RoomTestRunner.println("Getting an invalidExit, passing null: expecting NullPointerException");
		testRoom.getExit(null);
	}

	@Test
	public void stringRepresentation() {
		RoomTestRunner.println("Evaluating room to String, checking whether is null.");
		String toString = testRoom.toString();
		Assert.assertNotNull(toString);
	}

	@Test
	public void hasItem() {
		RoomTestRunner.println("Testing whether room has item 'apple', by name and reference.");
		boolean result = testRoom.hasItem(apple);
		Assert.assertTrue(result);
		result = testRoom.hasItem(apple.getName());
		Assert.assertTrue(result);
	}

	@SuppressWarnings("null")
	@Test(expected=NullPointerException.class)
	public void doesNotHaveNullItem() {
		RoomTestRunner.println("Checking to see if hasItem returns false if the item specified is not in the room, by name and reference.");
		Item dummy = null;
		boolean result = testRoom.hasItem(dummy);
		Assert.assertFalse(result);
		result = testRoom.hasItem(dummy.getName());
		Assert.assertTrue(result);
	}
	
	@Test
	public void doesNotHaveItem() {
		RoomTestRunner.println("Checking to see if hasItem returns false if the item specified is not in the room, by name and reference.");
		Item dummy = new Item("a", "b", 1);
		boolean result = testRoom.hasItem(dummy);
		Assert.assertFalse(result);
		result = testRoom.hasItem(dummy.getName());
		Assert.assertTrue(result);
	}

	@Test
	public void addItem() {
		RoomTestRunner.println("Checking whether AddItem works, adding dummy item.");	
		Item dummy = new Item("a", null, 1);
		testRoom.addItem(dummy);
		boolean result = testRoom.hasItem(dummy);
		Assert.assertTrue(result);
	}

	@Test
	public void addItems() {
		RoomTestRunner.println("Checking whether AddItems works, adding dummy items.");	
		Item dummy = new Item("b", null, 1);
		Item dummy2 = new Item("c", null, 1);
		testRoom.addItems(dummy, dummy2);
		boolean result = testRoom.hasItem(dummy) && testRoom.hasItem(dummy2);
		Assert.assertTrue(result);
	}

	@Test
	public void removeNullItem() {
		RoomTestRunner.println("Checking whether remove item works, adding item, removing and using the hasItem method.");	
		Item dummy = null;
		testRoom.addItem(dummy);
		boolean result = testRoom.hasItem(dummy);
		Assert.assertFalse(result);
	}
	
	@Test
	public void removeItem() {
		RoomTestRunner.println("Checking whether remove item works, adding item, removing and using the hasItem method.");	
		Item dummy = new Item("d", null, 1);
		testRoom.addItem(dummy);
		boolean result = testRoom.hasItem(dummy);
		Assert.assertTrue(result);
		testRoom.removeItem(dummy);
		result = testRoom.hasItem(dummy);
		Assert.assertFalse(result);
	}
	
	@Test
	public void getInvalidNullItem() {
		RoomTestRunner.println("getItem, using invalidItemName.");	
		Item item = testRoom.getItem(null);
		Assert.assertNull(item);
	}

	@Test
	public void getItem() {
		RoomTestRunner.println("Checking getItem, by name and reference.");	
		Item item = testRoom.getItem(apple.getName());
		Assert.assertNotNull(item);
		Assert.assertEquals(item, apple);
	}

	@Test
	public void getInvalidItem() {
		RoomTestRunner.println("getItem, using invalidItemName.");	
		Item item = testRoom.getItem("invalidItem");
		Assert.assertNull(item);
	}

	@Test
	public void getItems() {
		RoomTestRunner.println("getItems, testing whether 'apple' is stored in results.");	
		Collection<Resource> items = testRoom.getItems();
		testResourceCollection(items, apple);
	}

	@Test
	public void getCharacters() {
		RoomTestRunner.println("getCharacters, testing whether 'npc' is stored in results.");	
		Collection<Resource> characters = testRoom.getCharacters();
		testResourceCollection(characters, npc);
	}

	@Test
	public void getNonPlayerCharacters() {
		RoomTestRunner.println("getNonPlayerCharacters, testing whether 'npc' is stored in results.");	
		Collection<Resource> npcs = testRoom.getNonPlayerCharacters();
		testResourceCollection(npcs, npc);
	}

	@Test
	public void getExits() {
		RoomTestRunner.println("getExits, testing whether 'exit' is stored in results.");	
		Collection<Resource> exits = testRoom.getExits();
		testResourceCollection(exits, exit);
	}

	private void testResourceCollection(Collection<Resource> collection, Resource toCheckFor) {	
		Assert.assertNotNull(collection);
		Assert.assertTrue(collection.size() > 0);
		Assert.assertTrue(collection.contains(toCheckFor));
	}

	@Test
	public void addNullCharacter() {
		RoomTestRunner.println("addCharacter, testing on dummy.");
		int size = testRoom.getCharacters().size();
		NonPlayerCharacter dummy = null;
		testRoom.addCharacter(dummy);
		int newSize = testRoom.getCharacters().size();
		Assert.assertNotEquals(size, newSize);
	}
	
	@Test
	public void addCharacter() {
		RoomTestRunner.println("addCharacter, testing on dummy.");
		NonPlayerCharacter dummy = new NonPlayerCharacter("a", testRoom);
		testRoom.addCharacter(dummy);
		Character result = testRoom.getCharacter(dummy.getName());
		Assert.assertNotNull(result);
		Assert.assertEquals(result, dummy);
	}

	@Test
	public void removeNullCharacter() {
		boolean result = testRoom.removeCharacter(null);
		Assert.assertTrue(result);
	}
	
	@Test
	public void removeCharacter() {
		RoomTestRunner.println("removeCharacter, testing on dummy, adding them removing.");
		NonPlayerCharacter dummy = new NonPlayerCharacter("a", testRoom);
		testRoom.addCharacter(dummy);
		boolean result = testRoom.removeCharacter(dummy);
		Assert.assertTrue(result);
	}

	@Test
	public void getCharacter() {
		RoomTestRunner.println("getCharacter, testing on npc, using their name.");
		Character result = testRoom.getCharacter(npc.getName());
		Assert.assertNotNull(result);
		Assert.assertEquals(result, npc);
	}

	@Test
	public void getInvalidCharacter() {
		RoomTestRunner.println("getCharacter, using invalid data, expecting null.");
		Character result = testRoom.getCharacter("invalidCharacter");
		Assert.assertNull(result);
	}
	
	@Test
	public void getInvalidNullCharacter() {
		RoomTestRunner.println("getCharacter, using null, expecting null.");
		Character result = testRoom.getCharacter(null);
		Assert.assertNull(result);
	}
	
	@Test
	public void getExitFromRoomNameByNullData() {
		RoomTestRunner.println("getExitFromRoomName, using EXIT_DIRECTION from exit.");
		String direction = testRoom.getExitFromRoomName(null);
		Assert.assertNotNull(direction);
		Assert.assertEquals(direction, EXIT_DIRECTION);
	}

	@Test
	public void getExitFromRoomName() {
		RoomTestRunner.println("getExitFromRoomName, using EXIT_DIRECTION from exit.");
		String direction = testRoom.getExitFromRoomName(exit.getName());
		Assert.assertNotNull(direction);
		Assert.assertEquals(direction, EXIT_DIRECTION);
	}

	@Test
	public void getInvalidExitFromRoomName() {
		RoomTestRunner.println("getExitFromRoomName, using invalidExit for the exit.");
		String direction = testRoom.getExitFromRoomName("invalidExit");
		Assert.assertNull(direction);
	}

	@Test
	public void isComplete() {
		RoomTestRunner.println("isComplete, testing whether there are more than exits, when no exits returns false.");
		Assert.assertTrue(testRoom.getExits().size() > 0);
		Assert.assertTrue(testRoom.isComplete());
		testRoom.getExits().clear();
		Assert.assertFalse(testRoom.isComplete());
		testRoom.setExit(exit, EXIT_DIRECTION, true);
	}

	@Test(expected = NullPointerException.class)
	public void applyInformationNullGridPane() {
		RoomTestRunner.println("applyInformation, using null GridPane expecting: NullPointerException.");
		testRoom.applyInformation(null, "someCss");
	}

	@Test(expected = NullPointerException.class)
	public void applyInformationNullCSS() {
		RoomTestRunner.println("applyInformation, using null css expecting: NullPointerException.");
		testRoom.applyInformation(new GridPane(), null);
	}

}
