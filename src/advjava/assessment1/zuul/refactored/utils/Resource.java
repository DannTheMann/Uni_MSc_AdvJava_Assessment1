package advjava.assessment1.zuul.refactored.utils;

import java.io.File;
import java.nio.file.Paths;

import advjava.assessment1.zuul.refactored.Main;
import javafx.scene.image.Image;

public abstract class Resource extends Descriptor{
    
	private String resourceName;
    private String imageURL;
    private Image image;
    
    public Resource(String name, String description, String url){
    	super(name, description);
    	imageURL = url;
    	if(imageURL != null){
        	imageURL = Main.RESOURCE_FILES + File.separator + url;
    		if(imageURL.endsWith(".jpg") || imageURL.endsWith(".png") || imageURL.endsWith(".jpeg")){
    			resourceName = Paths.get(imageURL).getFileName().toString();
    		}else{
    			Out.out.loglnErr("Invalid imageURL given, cannot load resource: '" + imageURL + "'.");
    			imageURL = null;
    		}
    	}
    }
  
    public String getResourceName(){
    	return resourceName;
    }
    
    public String getImageURL(){
        return imageURL;
    }
    
    public Image getImage(){
    	return image;
    }

    public boolean loadImage(ResourceManager resourceManager){
    	
    	if(imageURL == null)
    		return false;
    	
    	Out.out.logln("Loading resource: " + resourceName + "...");
    	try{
    		
    		File f = new File(imageURL);
    		if(!f.exists()){
    			Out.out.loglnErr("Failed to load resource [NOT FOUND] : " + resourceName + " @ " + imageURL);
    			return false;
    		}
    		
    		if(resourceManager.containsImage(resourceName)){
    			image = resourceManager.getImage(resourceName);
    			return false;
    		}
    		
    		image = new Image(f.toURI().toString());
    		resourceManager.add(resourceName, image);
    	}catch(Exception e){
    		e.printStackTrace();
    		Out.out.loglnErr("Failed to load resource: " + resourceName + " @ " + imageURL);
    		return false;
    	}
    	
    	return true;
    	
    }

}
