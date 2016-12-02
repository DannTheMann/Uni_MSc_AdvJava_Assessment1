/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.interfaces.graphical;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.interfaces.FontManager;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import static advjava.assessment1.zuul.refactored.utils.Out.out;

import advjava.assessment1.zuul.refactored.utils.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 *
 * @author dja33
 */
public class CentralPanel extends SidePanel{

	/* Constants for spacing offsets between nodes in gridpanes */
	private static final int NODE_LEFT_OFFSET = 0;
	private static final int NODE_RIGHT_OFFSET = 10;
	private final HBox root;
	private final FontManager fm;
	private final Game game;
	private final Map<String, SidePanel> panels;

	public CentralPanel(String title, Stream<Resource> stream, FontManager fm, Game game, String cssStyling) {
		super(title, null, stream, fm, game, cssStyling);
		this.panels = new HashMap<>();
		this.game = game;
		this.fm = fm;

		root = new HBox();
		root.setAlignment(Pos.TOP_CENTER);
		root.setMaxSize(GraphicalInterface.WINDOW_MAX_WIDTH, GraphicalInterface.WINDOW_MAX_HEIGHT);
		root.setPadding(new Insets(0, NODE_RIGHT_OFFSET, 0, NODE_LEFT_OFFSET));
		root.setSpacing(NODE_RIGHT_OFFSET);
		// SplitPane.setResizableWithParent(root, Boolean.FALSE);
		//
		//
		// root = sp;

	}

	public boolean addPanel(String panelName, String empty, Stream<Resource> stream) {

		if(panels.containsKey(panelName))
			return false;
		
		SidePanel sp = new SidePanel(panelName, empty, stream, fm, game, getCSS());

		root.getChildren().add(sp.getNode());
		
		panels.put(panelName, sp);

		return true;
	}

	public Node getNode() {
		return root;
	}

	public void update(String name, Collection<Resource> newContents){
		
		Optional<SidePanel> panel = panels.entrySet().stream()
			.filter(panelName->panelName.getKey().equals(name))
			.map(Entry::getValue)
			.findFirst();
		
		if(panel.isPresent()){
			panel.get().update(newContents);
		}else{
			out.loglnErr("Could not find panel '" + name + "' when updating CentralPanel.");
		}
		
	}
	
//	public void update(String name, Collection<Resource> newContents) {
//		
//		// Stream every panel
//		panels.entrySet().stream()
//				// Filter if we find the panel we want
//				.filter(n -> n.getKey().getName().equals(name))
//				// For every PanelNode in this TilePane
//				.forEach(res -> {
//					res.getValue().removeIf(resif -> {
//						// remove if the newcontents does not match
//						if (!newContents.stream().filter(ncres -> resif.getId().equals(PanelNode.getId(ncres)))
//								.findAny().isPresent()) {
//							// Remove from TilePane
//							res.getKey().getRoot().getChildren().remove(resif.getNode());
//							return true;
//						} else
//							return false;
//					});
//				});
//
//		// Stream new contents to be added, add any resources
//		// not currently in the collection
//		newContents.stream().forEach(item -> {
//			// Stream all panels
//			panels.entrySet().stream()
//					// find the right one
//					.filter(n -> n.getKey().getName().equals(name))
//					// Stream all PanelNodes in collection
//					.forEach(pnlist -> { 
//						// Check whether this collection contains the current
//						// item
//						if (!pnlist.getValue().stream().filter(pn -> pn.getId().equals(PanelNode.getId(item))).findAny()
//								.isPresent()) {
//							// Add to tilePane and to our collection
//							PanelNode newNode = new PanelNode(name, getDisplayItem(item, name));
//							out.log("Adding -> " + item.getName());
//							pnlist.getValue().add(newNode); // Add to collection
//							pnlist.getKey().getRoot().getChildren().add(newNode.getNode()); // Add
//																							// to
//																							// TilePane
//
//						}
//					});
//		});
//
//	}

}
