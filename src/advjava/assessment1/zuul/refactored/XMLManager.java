package advjava.assessment1.zuul.refactored;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import advjava.assessment1.zuul.refactored.character.CharacterManager;
import advjava.assessment1.zuul.refactored.character.NonPlayerCharacter;
import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;

/**
 * The XMLManager is designed to load and interact with all other management
 * across the game to add rooms/items/characters into the game.
 * 
 * It's robust so that certain features of characters/items/rooms can be left
 * out but neccesary features will be noted if missing and errors will be thrown
 * in accordance to this.
 * 
 * @author Daniel
 *
 */
public abstract class XMLManager {

	// Constants for directories
	private static final String XML_ITEMS = Main.XML_CONFIGURATION_FILES + File.separator + "items.xml";
	private static final String XML_ROOMS = Main.XML_CONFIGURATION_FILES + File.separator + "rooms.xml";
	private static final String XML_CHARACTERS = Main.XML_CONFIGURATION_FILES + File.separator + "characters.xml";

	/**
	 * Load all rooms found in the XML file and add them to the room manager
	 * 
	 * @param rm
	 *            The room manager
	 * @throws MalformedXMLException
	 *             If XML is malformed or is missing key nodes
	 */
	public static final void loadRooms(RoomManager rm) throws MalformedXMLException {

		try {
			Document doc = getDocument(XML_ROOMS);
			doc.normalize(); // Normalise to handle any badly formatted XML
			// Prepare all variables
			NodeList nList = doc.getElementsByTagName("room");
			Node node, innerNode;
			Element eElement, innerElement = null;
			Room room, finalRoom = null;

			// Loop through every 'room' node
			for (int i = 0; i < nList.getLength(); i++) {
				node = nList.item(i);
				String name, desc = null;

				// If room is indeed an element node
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) node;
					name = getElement(eElement, "name");
					desc = getElement(eElement, "description");

					// If the room has already been created
					// then load it from the room manager
					if (rm.hasRoom(name)) {
						finalRoom = rm.getRoom(name);
					} else {
						// create new room otherwise
						finalRoom = new Room(name, desc);
					}

					// If the room has any items, loop through and add them
					if (eElement.getElementsByTagName("items-in-room") != null) {

						innerNode = eElement.getElementsByTagName("items-in-room").item(0);
						String iName = null;
						innerElement = (Element) innerNode;

						// for every item add it
						for (int j = 0; j < innerElement.getElementsByTagName("item").getLength(); j++) {
							iName = getElement(innerElement, "item", j);
							finalRoom.addItems(Main.game.getItemManager().getItem(iName));
						}

					}

					// Find any exits provided for the room
					if (eElement.getElementsByTagName("exits") != null) {

						// get the list of exit nodes
						innerNode = eElement.getElementsByTagName("exits").item(0);
						String rName, rDir = null;

						// get the contents of the exit node
						innerElement = (Element) innerNode;

						// get individual exit from
						if (innerElement.getElementsByTagName("exit") != null) {

							innerNode = eElement.getElementsByTagName("exits").item(0);

							// Go through every exit for this room
							for (int j = 0; j < innerElement.getElementsByTagName("exit").getLength(); j++) {

								// Get room direction and name from nodes
								rDir = getElement(innerElement, "direction", j);
								rName = getElement(innerElement, "room-name", j);

								if (rName.equals(name)) {
									// If the exit points to the current room
									// then ignore it
									continue;
								}

								// Need to check whether this room exists in
								// order
								// to use it as a reference for an exit
								if (rm.hasRoom(rName)) {
									// it does exit, then load it
									room = rm.getRoom(rName);
								} else {
									// create new room otherwise and fill it in
									// later
									room = new Room(rName, null);
									rm.addRoom(room);
								}
								// Set the exit
								finalRoom.setExit(room, rDir, true);

							}

						} else {
							// No exits provided, must have at least one exit
							throw new MalformedXMLException(XML_ROOMS,
									String.format(InternationalisationManager.im.getMessage("xml.invalidExits"), name));
						}

					} else {
						// No exits provided, must have at least one exit
						throw new MalformedXMLException(XML_ROOMS,
								String.format(InternationalisationManager.im.getMessage("xml.noExits"), name));
					}

					// Was the room added? Stop duplication of room names
					boolean added = rm.addRoom(finalRoom);

					// If the room is new or has a final implementation of it
					if (added || (!added && finalRoom.isComplete())) {
						System.out.println(String.format(InternationalisationManager.im.getMessage("xml.loadRoom"),
								finalRoom.getName()));
					} else {
						// Duplicated room
						System.out.println(String.format(InternationalisationManager.im.getMessage("xml.roomExists"),
								finalRoom.getName()));
					}
				}

			}

			// Validate integrity of all rooms
			rm.checkIntegrity();

		} catch (Exception e) {
			e.printStackTrace();
			throw new MalformedXMLException(
					String.format(InternationalisationManager.im.getMessage("xml.fail"), XML_ROOMS));
		}

	}

	/**
	 * Load all characters found in the XML file and add them to the character
	 * manager
	 * 
	 * @param rm
	 *            The character manager
	 * @throws MalformedXMLException
	 *             If XML is malformed or is missing key nodes
	 */
	public static final void loadCharacters(CharacterManager cm) throws MalformedXMLException {

		try {

			Document doc = getDocument(XML_CHARACTERS);
			doc.normalize(); // Normalise to handle any badly formatted XML

			// Prepare all variables
			NodeList nList = doc.getElementsByTagName("npc");
			Node node, innerNode;
			Element eElement, innerElement = null;
			NonPlayerCharacter npc = null;
			List<Item> items = null;
			String name, desc, roomName, weight, iName;
			Room room = null;

			// loop through every 'npc' node
			for (int i = 0; i < nList.getLength(); i++) {

				node = nList.item(i);

				// If room is indeed an element node
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) node;
					name = getElement(eElement, "name");

					// If the characters name is null
					if (name == null) {
						throw new NullPointerException(InternationalisationManager.im.getMessage("xml.charNameNull"));
					}

					// get default details on this character
					desc = getElement(eElement, "description");
					roomName = getElement(eElement, "room");
					room = Main.game.getRoomManager().getRoom(roomName);

					// If the room they belong to is null
					if (room == null) {
						throw new NullPointerException(String
								.format(InternationalisationManager.im.getMessage("xml.charRoomNull"), roomName, name));
					}

					weight = getElement(eElement, "maxweight");

					items = new PrintableList<Item>();

					// If they have an inventory, load through it and set their
					// items
					if (eElement.getElementsByTagName("inventory").item(0) != null) {

						// get inner nodes for inventory
						innerNode = eElement.getElementsByTagName("inventory").item(0);
						innerElement = (Element) innerNode;

						// Go through every instance of 'item' in the nodes
						for (int j = 0; j < innerElement.getElementsByTagName("item").getLength(); j++) {
							iName = getElement(innerElement, "item", j);
							// if the item exists, add it
							if ((Main.game.getItemManager().hasItem(iName))) {
								items.add(Main.game.getItemManager().getItem(iName));
							} else {
								// else throw exception, can't add an item that
								// doesn't exist
								throw new NullPointerException(
										InternationalisationManager.im.getMessage("xml.charItemNull"));
							}
						}

					}

					// If the weight for this character was specified, set their
					// max weight
					if (weight != null) {
						npc = new NonPlayerCharacter(name, desc, room, items, Integer.parseInt(weight));
					} else {
						// else just create an NPC with DEFAULT_MAX_WEIGHT
						npc = new NonPlayerCharacter(name, desc, room, items);
					}
				}

				// Add character
				cm.addCharacter(npc);
				System.out.println(
						String.format(InternationalisationManager.im.getMessage("xml.charAdd"), npc.getName()));

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MalformedXMLException(
					String.format(InternationalisationManager.im.getMessage("xml.fail"), XML_CHARACTERS));
		}

	}

	/**
	 * Load all rooms found in the XML file and add them to the room manager
	 * 
	 * @param rm
	 *            The room manager
	 * @throws MalformedXMLException
	 *             If XML is malformed or is missing key nodes
	 */
	public static final void loadItems(ItemManager im) throws MalformedXMLException {

		try {
			Document doc = getDocument(XML_ITEMS);
			doc.normalize(); // Normalise to handle any badly formatted XML

			// Prepare all variables
			NodeList nList = doc.getElementsByTagName("item");
			Node node;
			Element eElement;
			Item item = null;
			String name, desc = null;
			int weight = 0;

			// loop through every 'item' node
			for (int i = 0; i < nList.getLength(); i++) {

				node = nList.item(i);

				// If room is indeed an element node
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) node;
					name = getElement(eElement, "name");
					desc = getElement(eElement, "description");
					weight = Integer.parseInt(getElement(eElement, "weight"));

					// create new item
					item = new Item(name, desc, weight);

				}

				// add item
				im.addItem(item);
				System.out.println(
						String.format(InternationalisationManager.im.getMessage("xml.addItem"), item.getName()));

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MalformedXMLException(InternationalisationManager.im.getMessage("xml.fail"));
		}

	}

	/**
	 * Shorthand method to handle getting of elements from an Element object,
	 * fills in a lot of duplicated code otherwise and handles any null cases.
	 * 
	 * @param eElement
	 *            The element to check inside
	 * @param str
	 *            The text of the element we're looking for
	 * @return text if present or null
	 */
	private static final String getElement(Element eElement, String str) {
		if (eElement == null || eElement.getElementsByTagName(str) == null
				|| eElement.getElementsByTagName(str).item(0) == null) {
			return null;
		} else {
			return eElement.getElementsByTagName(str).item(0).getTextContent();
		}
	}

	/**
	 * Shorthand method to handle getting of elements from an Element object,
	 * fills in a lot of duplicated code otherwise and handles any null cases.
	 * If multiple indexs are present in the node, use this method to specify
	 * which index.
	 * 
	 * @param eElement
	 *            The element to check inside
	 * @param str
	 *            The text of the element we're looking for
	 * @param index
	 *            The index to look at
	 * @return text if present or null
	 */
	private static final String getElement(Element eElement, String str, int index) {
		if (eElement == null)
			return null;
		if (eElement.getElementsByTagName(str) == null) {
			return null;
		} else {
			return eElement.getElementsByTagName(str).item(index).getTextContent();
		}
	}

	/**
	 * Get the document we wish to parse through
	 * 
	 * @param xmlFile
	 *            The file to parse
	 * @return Document the instance of the Document
	 * @throws Exception
	 *             If something goes wrong I/O
	 */
	private static final Document getDocument(String xmlFile) throws Exception {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlFile));
	}

}
