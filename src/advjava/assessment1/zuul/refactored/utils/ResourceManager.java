package advjava.assessment1.zuul.refactored.utils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import advjava.assessment1.zuul.refactored.Main;
import javafx.scene.image.Image;

public class ResourceManager {
	
	private static ResourceManager rm;
	private File resourceDirectory;
	private Map<String, Image> resources; 
	
	private ResourceManager(String dir){
		resources = new HashMap<String, Image>();
		resourceDirectory = new File(dir);
		
		if(!resourceDirectory.exists()){
			resourceDirectory.mkdirs();
		}
		
		loadResources();
	}
	
	private void loadResources() {
		
		Out.out.logln("Loading resources from '" + resourceDirectory.getAbsolutePath() + "'.");
		resources.put("error", new Image(Main.game.getProperty("noResourceFound")));
		Arrays.stream(resourceDirectory.listFiles())
				.filter(f->f.getName().endsWith(".jpg") 
						|| f.getName().endsWith(".jpeg") 
						|| f.getName().endsWith(".png"))
				.forEach(f->resources.put(f.getName().split(".")[0], new Image(f.getAbsolutePath())));
		Out.out.logln("Resources loaded = " + resources.size());
	}

	public static void newResourceManager(){
		Out.out.logln("Creating new resource manager.");
		
		System.out.println("RM = " + Main.game.getProperty("resourceDirectory"));
		//rm = new ResourceManager(Main.game.getProperty("resourceDirectory"));
	}
	
	public static ResourceManager getResourceManager(){
		return rm;
	}
	
	public Image getImage(String key){
		Out.out.logln("Trying to retrieve '" + key + "' from ResourceManager.");
		return resources.entrySet().stream()
				.filter(k->k.equals(key))
				.map(v->v.getValue())
				.findFirst()
				.orElse(resources.get("error"));	
	}

}
