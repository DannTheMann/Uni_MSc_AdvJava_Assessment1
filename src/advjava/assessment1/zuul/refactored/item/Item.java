/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.item;

import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;
import advjava.assessment1.zuul.refactored.utils.Resource;
import advjava.assessment1.zuul.refactored.character.Character;

/**
 * 
 * The item class is used to store information regarding to items used in the
 * game such as weight, name and description. Provides methods to create, and
 * return values based on an item.
 * 
 * 
 * @author dja33
 */
public class Item extends Resource{

	private int weight;
	
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
         * @param character The character to compare against
         * @return true if can pick up
         */
        public boolean canPickUp(Character character){
            return character.getWeight()+weight <= character.getMaxWeight();
        }
	
	/**
	 * Override toString and provide a detailed explaination of the item in a
	 * formatted string
         * @return 
	 */
	@Override
	public String toString() {
		return getDescription() == null
				? String.format(InternationalisationManager.im.getMessage("item.toStringNoDesc"), getName(), weight)
				: String.format(InternationalisationManager.im.getMessage("item.toStringWithDesc"), getName(), getDescription(),
						weight);
	}

}
