package advjava.assessment1.zuul.refactored.utils;

public abstract class Descriptor {
	
	private static final int MAX_CHARS_PER_LINE = 70;
	private String name;
	private String description;
	
	public Descriptor(String name, String descriptor){
		this.name = name.replaceAll(" ", "_");
		this.description = descriptor;
		
		if(this.description != null && this.description.length() > MAX_CHARS_PER_LINE){
			this.description = this.description.replaceAll("\n", "").replaceAll("(.{"+MAX_CHARS_PER_LINE+"})", "$1\n");
		}
	}
	
	public String getName(){
		return name.replaceAll("_", " ");
	}
	
	public String getRawName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String toString(){
		return name + " -> " + description;
	}

	public boolean update(String name, String description){
		boolean flag = false;
		if(this.name == null){
			this.name = name;
			flag = true;
		}
		if(this.description == null){
			this.description = description;
			flag = true;
		}
		return flag;
	}

	public String getRawDescription() {
		return description;
	}
	
}
