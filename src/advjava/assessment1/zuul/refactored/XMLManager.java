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
							throw new MalformedXMLException(XML_ROOMS, String.format(InternationalisationManager.im.getMessage("xml.invalidExits"), name));
						}

					} else {
						throw new MalformedXMLException(XML_ROOMS, String.format(InternationalisationManager.im.getMessage("xml.noExits"), name));
					}
					
					boolean added = rm.addRoom(finalRoom);
					
					if(added){
						System.out.println(String.format(InternationalisationManager.im.getMessage("xml.loadRoom"), finalRoom.getName()));
					}else{
						System.out.println(String.format(InternationalisationManager.im.getMessage("xml.roomExists"), finalRoom.getName()));
					}
				}

			}

			rm.checkIntegrity();

		} catch (Exception e) {
			e.printStackTrace();
			throw new MalformedXMLException(String.format(InternationalisationManager.im.getMessage("xml.fail"), XML_ROOMS));
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
						throw new NullPointerException(InternationalisationManager.im.getMessage("xml.charNameNull"));
					
					desc = getElement(eElement, "description");
					roomName = getElement(eElement, "room");
					room = Main.game.getRoomManager().getRoom(roomName);
					
					if(room == null)
						throw new NullPointerException(String.format(InternationalisationManager.im.getMessage("xml.charRoomNull"), roomName, name));
					
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
								throw new NullPointerException(InternationalisationManager.im.getMessage("xml.charItemNull"));
						}
						
					}
				}
				
				cm.addCharacter(npc);
				System.out.println(String.format(InternationalisationManager.im.getMessage("xml.charAdd"), npc.getName()));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MalformedXMLException(String.format(InternationalisationManager.im.getMessage("xml.fail"), XML_CHARACTERS));
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
					System.out.println(String.format(InternationalisationManager.im.getMessage("xml.addItem"), item.getName()));
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new MalformedXMLException(InternationalisationManager.im.getMessage("xml.fail"));
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
