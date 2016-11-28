package advjava.assessment1.zuul.refactored.utils;

import javafx.scene.image.Image;

public abstract class Resource {
    
    private String name;
    private String description;
    private String imageURL;
    private Image image;
    
    public Resource(String name, String description){
		if ("".equals(name) || name == null) {
			throw new NullPointerException(InternationalisationManager.im.getMessage("item.null"));
		}
        this.name = name;
        this.description = description;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public String getImageURL(){
        return imageURL;
    }
    
	@Override
	public String toString() {
		return description == null
				? name
				: name + " -> " + description;
	}
    

}
