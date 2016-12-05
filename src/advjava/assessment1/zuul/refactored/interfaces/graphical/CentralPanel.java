/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.interfaces.graphical;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.interfaces.FontManager;
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
import javafx.scene.layout.VBox;

/**
 *
 * @author dja33
 */
public class CentralPanel extends SidePanel{

	/* Constants for spacing offsets between nodes in gridpanes */
	private static final int NODE_LEFT_OFFSET = 0;
	private static final int NODE_RIGHT_OFFSET = 10;
	private final VBox root;
	private final FontManager fm;
	private final Game game;
	private final Map<String, SidePanel> panels;

	public CentralPanel(String title, Stream<Resource> stream, FontManager fm, Game game, String cssStyling) {
		super(title, null, stream, fm, game, cssStyling, 0, 0, 0, 0);
		this.panels = new HashMap<>();
		this.game = game;
		this.fm = fm;

		root = new VBox();
		root.setAlignment(Pos.CENTER);
		//root.setMaxSize(GraphicalInterface.WINDOW_MAX_WIDTH, GraphicalInterface.WINDOW_MAX_HEIGHT);
		root.setPadding(new Insets(0, NODE_RIGHT_OFFSET, 0, NODE_LEFT_OFFSET));
		root.setSpacing(20);

	}

	/**
	 * Add a panel to the central panel
	 * @param panelName The name of the panel
	 * @param empty It's empty string to show if empty
	 * @param stream The resources to add
	 * @param imageWidth The height of the images
	 * @param imageHeight The width of the images
	 * @param nodeWidth The width of the nodes
	 * @param nodeHeight The height of the nodes
	 * @return boolean whether it was added or not
	 */
	public boolean addPanel(String panelName, String empty, Stream<Resource> stream, int imageWidth, int imageHeight, int nodeWidth, int nodeHeight) {

		if(panels.containsKey(panelName)){
			out.loglnErr("Could not add panel '" + panelName + "'. Already exists.");
			return false;
		}
		
		SidePanel sp = new SidePanel(panelName, empty, stream, fm, game, getCSS(), imageWidth, imageHeight, nodeWidth, nodeHeight);
		
		root.getChildren().add(sp.getNode());
		
		sp.getNode().setVisible(true);
		
		panels.put(panelName, sp);

		return true;
	}

	/**
	 * Return this Panel as a VBox
	 */
	public VBox getNode() {
		return root;
	}
	
	/**
	 * Set all children of the central panel to be visible or not
	 * @param visible
	 */
	public void setVisible(boolean visible){
		root.setVisible(visible);
		root.getChildren().stream().forEach(n->n.setVisible(visible));
	}

	/**
	 * Update all children of this panel
	 * @param name The name of the SidePanel to update
	 * @param newContents The newcontents of the panel
	 */
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
	
}
