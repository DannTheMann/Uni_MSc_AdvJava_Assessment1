package advjava.assessment1.zuul.refactored.utils;

public abstract class Descriptor {
	
	private String name;
	private String description;
	
	public Descriptor(String name, String descriptor){
		this.name = name;
		this.description = descriptor;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String toString(){
		return name + " -> " + description;
	}

}
