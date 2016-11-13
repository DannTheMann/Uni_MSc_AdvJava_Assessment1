/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.item;

import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;

/**
 * 
 * The item class is used to store information regarding to items used in the
 * game such as weight, name and description. Provides methods to create, and
 * return values based on an item.
 * 
 * 
 * @author dja33
 */
public class Item {

	private String name;
	private String description;
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
	public Item(String name, String description, int weight) {
		if (name == "" || name == null) {
			throw new NullPointerException(InternationalisationManager.im.getMessage("item.null"));
		}
		this.name = name.replaceAll(" ", "");
		this.description = description == null || description.equals("") ? null : description;
		this.weight = weight;
	}

	/**
	 * If the item doesn't have a description, alternatively use this
	 * constructor
	 */
	public Item(String name, int weight) {
		this(name, null, weight);
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
	 * Return name of the item
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return description of item
	 * 
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Override toString and provide a detailed explaination of the item in a
	 * formatted string
	 */
	@Override
	public String toString() {
		return description == null
				? String.format(InternationalisationManager.im.getMessage("item.toStringNoDesc"), name, weight)
				: String.format(InternationalisationManager.im.getMessage("item.toStringWithDesc"), name, description,
						weight);
	}

}
