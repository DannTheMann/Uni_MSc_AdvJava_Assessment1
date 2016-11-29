package advjava.assessment1.zuul.refactored.utils;

public abstract class Descriptor {
	
        public static final int MAX_CHARACTER_PER_LINE = 25;
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
