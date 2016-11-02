package advjava.assessment1.zuul.refactored;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import advjava.assessment1.zuul.refactored.character.CharacterManager;
import advjava.assessment1.zuul.refactored.character.NonPlayerCharacter;
import advjava.assessment1.zuul.refactored.exception.MalformedXMLException;

public abstract class XMLManager {

	private static final String XML_ITEMS = Main.XML_CONFIGURATION_FILES + System.lineSeparator() + "";
	private static final String XML_ROOMS = Main.XML_CONFIGURATION_FILES + System.lineSeparator() + "";
	private static final String XML_CHARACTERS = Main.XML_CONFIGURATION_FILES + System.lineSeparator() + "";

	public static final void loadRooms(RoomManager rm) throws MalformedXMLException {

		try {
			Document doc = getDocument(XML_ROOMS);
			doc.normalize();
			NodeList nList = doc.getElementsByTagName("room");
			NodeList innerList;
			Node node, innerNode;
			Element eElement, innerElement = null;
			Room room, finalRoom = null;

			for (int i = 0; i < nList.getLength(); i++) {
				node = nList.item(i);
				String name, desc = null;

				// Looking at room
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) node;
					name = getElement(eElement, "name");
					desc = getElement(eElement, "description");

					if (rm.hasRoom(name))
						finalRoom = rm.getRoom(name);
					else
						finalRoom = new Room(name, desc);

					if (eElement.getElementsByTagName("items-in-room") != null) {
						innerList = eElement.getElementsByTagName("items-in-room");
						String iName = null;
						for (int j = 0; j < innerList.getLength(); j++) {
							innerNode = innerList.item(i);
							innerElement = (Element) innerNode;
							iName = getElement(innerElement, "item");
							finalRoom.addItems(Main.game.getItemManager().getItem(iName));
							// ...
						}
					}

					if (eElement.getElementsByTagName("exits") != null) {
						innerList = eElement.getElementsByTagName("exits");
						String rName, rDir = null;
						for (int j = 0; j < innerList.getLength(); j++) {
							innerNode = innerList.item(j);
							innerElement = (Element) innerNode;
							rName = getElement(innerElement, "room-name");
							rDir = getElement(innerElement, "direction");
							room = rm.getRoom(rName);
							if (room == null)
								rm.addRoom(new Room(rName, null));
							else {
								finalRoom.setExit(room, rDir, true);
							}
							// ...
						}
					} else {
						throw new MalformedXMLException(XML_ROOMS, "No exits specified for room '" + name + "'.");
					}

				}

			}

			rm.checkIntegrity();

		} catch (Exception e) {
			e.printStackTrace();
			throw new MalformedXMLException("Could not load " + XML_ROOMS + ".");
		}

	}

	public static final void loadCharacters(CharacterManager cm) throws MalformedXMLException {

		try {
			Document doc = getDocument(XML_CHARACTERS);
			doc.normalize();
			NodeList nList = doc.getElementsByTagName("npc");
			NodeList innerList;
			Node node, innerNode;
			Element eElement, innerElement = null;
			NonPlayerCharacter npc = null;
			for (int i = 0; i < nList.getLength(); i++) {

				node = nList.item(i);
				String name, desc;
				Room room = null;
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) node;
					name = getElement(eElement, "name");
					
					if(name == null)
						throw new NullPointerException("Name specified for character was null, Malformed XML?");
					
					desc = getElement(eElement, "description");
					room = Main.game.getRoomManager().getRoom(getElement(eElement, "room"));
					
					if(room == null)
						throw new NullPointerException("Room specified for " + name + " was null, Malformed XML?");
					
					npc = new NonPlayerCharacter(name, desc, room);

					Integer.parseInt(getElement(eElement, "maxweight"));

					if (eElement.getElementsByTagName("inventory") != null) {
						innerList = eElement.getElementsByTagName("inventory");
						for (int j = 0; j < innerList.getLength(); j++) {
							innerNode = innerList.item(i);
							innerElement = (Element) innerNode;
							Item item = Main.game.getItemManager().getItem(getElement(innerElement, "item"));
							if(item == null)
								throw new NullPointerException("Item specified does not exist, are you sure it's named correctly? XML Malformed?");
							npc.addItem(item);
							
							// ...
						}
					}
					
					// ...
				}
				
				cm.addCharacter(npc);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MalformedXMLException("Could not load " + XML_CHARACTERS + ".");
		}

	}

	public static final void loadItems(ItemManager im) throws MalformedXMLException{
	
			try {
				Document doc = getDocument(XML_ITEMS);
				doc.normalize();
				NodeList nList = doc.getElementsByTagName("item");
				Node node;
				Element eElement;
				Item item = null;

				for (int i = 0; i < nList.getLength(); i++) {

					node = nList.item(i);
					String name,desc = null;
					int weight = 0;

					if (node.getNodeType() == Node.ELEMENT_NODE) {

						eElement = (Element) node;
						name = getElement(eElement, "name");
						desc = getElement(eElement, "description");
						weight = Integer.parseInt(getElement(eElement, "weight"));

						item = new Item(name, desc, weight);
						
					}
					
					im.addItem(item);

				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new MalformedXMLException("Could not load " + XML_ITEMS + ".");
			}
		 
	 }

	private static String getElement(Element eElement, String str) {
		if (eElement.getElementsByTagName(str) == null) {
			return null;
		} else {
			return eElement.getElementsByTagName(str).item(0).getTextContent();
		}
	}

	private static final Document getDocument(String xmlFile) throws Exception {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlFile));
	}

}
