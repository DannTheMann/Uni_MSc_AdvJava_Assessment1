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

	private static final String XML_ITEMS = Main.XML_CONFIGURATION_FILES + File.separator + "items.xml";
	private static final String XML_ROOMS = Main.XML_CONFIGURATION_FILES + File.separator + "rooms.xml";
	private static final String XML_CHARACTERS = Main.XML_CONFIGURATION_FILES + File.separator + "characters.xml";

	public static final void loadRooms(RoomManager rm) throws MalformedXMLException{

		try {
			Document doc = getDocument(XML_ROOMS);
			doc.normalize();
			NodeList nList = doc.getElementsByTagName("room");
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

					if (rm.hasRoom(name)){
						finalRoom = rm.getRoom(name);
					}else{
						finalRoom = new Room(name, desc);
					}
					
					if (eElement.getElementsByTagName("items-in-room") != null) {
						
						innerNode = eElement.getElementsByTagName("items-in-room").item(0);
						String iName = null;
						innerElement = (Element) innerNode;						
						for(int j = 0; j < innerElement.getElementsByTagName("item").getLength(); j++){
							iName = getElement(innerElement, "item", j);							
							finalRoom.addItems(Main.game.getItemManager().getItem(iName));
						}
						
					}

					if (eElement.getElementsByTagName("exits") != null) {
						
						innerNode = eElement.getElementsByTagName("exits").item(0);
						String rName, rDir = null;

						innerElement = (Element) innerNode;
						
						if(innerElement.getElementsByTagName("exit") != null){

							innerNode = eElement.getElementsByTagName("exits").item(0);
							
							for(int j = 0; j < innerElement.getElementsByTagName("exit").getLength(); j++){
								
								rDir = getElement(innerElement, "direction", j);		
								rName = getElement(innerElement, "room-name", j);					
								
								if(rName.equals(name)){
									continue;
								}
								
								if (rm.hasRoom(rName)){
									room = rm.getRoom(rName);
								}else{
									room = new Room(rName, null);
									rm.addRoom(room);
								}
								finalRoom.setExit(room, rDir, true);
								
							}
							
						}else{
							throw new MalformedXMLException(XML_ROOMS, name + " ROOM invalid exits, Malformed XML.");
						}

					} else {
						throw new MalformedXMLException(XML_ROOMS, "No exits specified for room '" + name + "'.");
					}
					
					boolean added = rm.addRoom(finalRoom);
					
					if(added){
						System.out.println(String.format("Loaded room '%s'", finalRoom.getName()));
					}else{
						System.out.println(String.format("Failed to load room '%s', already a room named this exists.", finalRoom.getName()));
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
			Node node, innerNode;
			Element eElement, innerElement = null;
			NonPlayerCharacter npc = null;
			for (int i = 0; i < nList.getLength(); i++) {

				node = nList.item(i);
				String name, desc, roomName, weight;
				Room room = null;
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) node;
					name = getElement(eElement, "name");
					
					if(name == null)
						throw new NullPointerException("Name specified for character was null, Malformed XML?");
					
					desc = getElement(eElement, "description");
					roomName = getElement(eElement, "room");
					room = Main.game.getRoomManager().getRoom(roomName);
					
					if(room == null)
						throw new NullPointerException("Room specified for " + name + " was null, Malformed XML?");
					
					weight = getElement(eElement, "maxweight");
					if(weight != null)
						npc = new NonPlayerCharacter(name, desc, room, null, Integer.parseInt(weight));
					else
						npc = new NonPlayerCharacter(name, desc, room);

					if (eElement.getElementsByTagName("inventory").item(0) != null) {
						
						innerNode = eElement.getElementsByTagName("inventory").item(0);
						String iName = null;
						innerElement = (Element) innerNode;						
						for(int j = 0; j < innerElement.getElementsByTagName("item").getLength(); j++){
							iName = getElement(innerElement, "item", j);	
							if((Main.game.getItemManager().hasItem(iName)))
								npc.addItem( Main.game.getItemManager().getItem(iName));
							else
								throw new NullPointerException("Item does not exist! Have you specified it in the items.xml file?");
						}
						
					}
				}
				
				cm.addCharacter(npc);
				System.out.println(String.format("Added new character '%s'.", npc.getName()));
				
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
					System.out.println(String.format("Added item '%s'", item.getName()));
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new MalformedXMLException("Could not load " + XML_ITEMS + ".");
			}
		 
	 }

	private static final String getElement(Element eElement, String str) {

		if (eElement == null || eElement.getElementsByTagName(str) == null || eElement.getElementsByTagName(str).item(0) == null) {
			return null;
		} else {
			return eElement.getElementsByTagName(str).item(0).getTextContent();
		}
	}
	
	private static final String getElement(Element eElement, String str, int index) {
		
		if(eElement == null) return null;
		
		if (eElement.getElementsByTagName(str) == null) {
			return null;
		} else {
			return eElement.getElementsByTagName(str).item(index).getTextContent();
		}
	}

	private static final Document getDocument(String xmlFile) throws Exception {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlFile));
	}

}
