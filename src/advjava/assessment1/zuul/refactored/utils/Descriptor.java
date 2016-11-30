package advjava.assessment1.zuul.refactored.utils;

public abstract class Descriptor {
	
	private static final int MAX_CHARS_PER_LINE = 75;
	private String name;
	private String description;
	
	public Descriptor(String name, String descriptor){
		this.name = name;
		this.description = descriptor;
		
		if(this.description != null && this.description.length() > MAX_CHARS_PER_LINE){
			this.description = this.description.replaceAll(System.lineSeparator(), "").replaceAll("(.{"+MAX_CHARS_PER_LINE+"})", "$1-\n");
		}
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

	public void update(String name, String description){
		if(this.name == null){
			this.name = name;
		}
		if(this.description == null){
			this.description = description;
		}
	}
	
}
