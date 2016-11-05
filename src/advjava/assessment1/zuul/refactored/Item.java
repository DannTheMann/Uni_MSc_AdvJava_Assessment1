/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored;

/**
 *
 * @author dja33
 */
public class Item {
    
    private String name;
    private String description;
    private int weight;
    
    public Item(String name, String description, int weight){
    	if(name == "" || name == null)
    		throw new NullPointerException(InternationalisationManager.im.getMessage("item.null"));
        this.name = name;
        this.description = description.equals("") ? null : description;
        this.weight = weight;
    }
    
    public Item(String name, int weight){
        this(name, null, weight);
    }
    
    public int getWeight(){
        return weight;
    }
    
    public String getName(){
        return this.name;
    }

    public String getDescription(){
    	return this.description;
    }
    
    @Override
    public String toString(){
        return description == null ? String.format(InternationalisationManager.im.getMessage("item.toStringNoDesc"), name, weight) 
        		: String.format(InternationalisationManager.im.getMessage("item.toStringWithDesc"), name, description, weight);
    }
    
}
