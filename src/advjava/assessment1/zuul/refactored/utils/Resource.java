package advjava.assessment1.zuul.refactored.utils;

import javafx.scene.image.Image;

public abstract class Resource {
    
    private String name;
    private String description;
    private String imageURL;
    private Image image;
    
    public Resource(String name, String description, String imageURL){
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        
        if ( this.imageURL != null){
        
            //image = ResourceManager.getResourceManager().
        
        }
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
    
    

}
