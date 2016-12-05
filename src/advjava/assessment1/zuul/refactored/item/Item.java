/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.item;

import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.utils.Resource;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;
import javafx.scene.layout.GridPane;

/**
 * 
 * The item class is used to store information regarding to items used in the
 * game such as weight, name and description. Provides methods to create, and
 * return values based on an item.
 * 
 * 
 * @author dja33
 */
public class Item extends Resource {

	private final int weight;

	/**
	 * Create a new item
	 * 
	 * @param name
	 *            The name of the item
	 * @param description
	 *            Item description
	 * @param weight
	 *            The weight of the item
	 * @throws NullPointerException
	 *             if name is null
	 */
	public Item(String name, String description, int weight, String url) {
		super(name.replaceAll(" ", ""), description == null || description.equals("") ? null : description, url);
		this.weight = weight;
	}

	/**
	 * If the item doesn't have a description, alternatively use this
	 * constructor
	 * 
	 * @param name
	 * @param description
	 * @param weight
	 */
	public Item(String name, String description, int weight) {
		this(name, description, weight, null);
	}

	/**
	 * If the item doesn't have a description, alternatively use this
	 * constructor
	 * 
	 * @param name
	 * @param weight
	 */
	public Item(String name, int weight) {
		this(name, null, weight, null);
	}

	/**
	 * Return weight of the item
	 * 
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Can the Character pick up this item
	 * 
	 * @param character
	 *            The character to compare against
	 * @return true if can pick up
	 */
	public boolean canPickUp(Character character) {
		return character.getWeight() + weight <= character.getMaxWeight();
	}

	@Override
	public String getDescription() {
		return "Weight: " + getWeight() + System.lineSeparator() + super.getDescription();
	}

	@Override
	public String getRawDescription() {
		return super.getDescription();
	}

	/**
	 * Override toString and provide a detailed explanation of the item in a
	 * formatted string
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return getDescription() == null
				? String.format(InternationalisationManager.im.getMessage("item.toStringNoDesc"), getName(), weight)
				: String.format(InternationalisationManager.im.getMessage("item.toStringWithDesc"), getName(),
						getDescription(), weight);
	}

	@Override
	public void applyInformation(GridPane grid, String css) {
		
		grid.setOnMouseClicked(GraphicalInterface.getVariableCommandEvent(getRawName()));

//		if (css.equals("sidepanel-room")) {
//
//			// Create take button
//			grid.add(new StackPane(GraphicalInterface.newCommandButton("take " + getName(),
//					Main.game.getCommandManager().getCommand("Take"), ".sidebar-button")), 0, 2);
//
//		} else {
//
////			// create drop and give button, wrap them in a VBox
////			VBox buttonHolder = new VBox();
////			buttonHolder.getChildren().add(GraphicalInterface.newCommandButton("drop " + getName(),
////					Main.game.getCommandManager().getCommand("Drop"), ".sidebar-button"));
////			buttonHolder.getChildren().add(GraphicalInterface.newCommandButton("give " + getName(),
////					Main.game.getCommandManager().getCommand("Give"), ".sidebar-button"));
////			buttonHolder.setSpacing(12);
////
////			grid.add(buttonHolder, 1, 1);
//
//		}

	}

}
