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
    private int weight;
    
    public Item(String name, int weight){
        this.name = name;
        this.weight = weight;
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
        return String.format("%s weighing %d.", name, weight);
    }
    
}
