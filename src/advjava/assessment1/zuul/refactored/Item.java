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
    		throw new NullPointerException("An Item name cannot be null or an empty String, Malformed XML?");
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
    public Item(String name, int weight){
        this(name, null, weight);
    }
    
    public int getWeight(){
        return weight;
    }
    
    public void setWeight(int weight){
        this.weight = weight;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return description == null ? String.format("%s (%d)", name, weight) : String.format("%s, %s. (%d)", name, description, weight);
    }
    
}
