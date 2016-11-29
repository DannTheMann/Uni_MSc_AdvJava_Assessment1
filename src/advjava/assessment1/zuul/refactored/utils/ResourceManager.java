package advjava.assessment1.zuul.refactored.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import advjava.assessment1.zuul.refactored.Main;
import javafx.scene.image.Image;

public class ResourceManager {

	private static ResourceManager rm;
	private File resourceDirectory;
	private Map<String, Resource> loadedResources;
	private Collection<Resource> rawResources;
	private Image error;

	private ResourceManager(String dir, Collection<Resource> rawResources) {
		this.rawResources = rawResources; 
		this.loadedResources = new TreeMap<>();
		resourceDirectory = new File(dir);

		if(this.rawResources == null){
			throw new NullPointerException("Raw resources cannot be null!");
		}
		
		if (!resourceDirectory.exists()) {
			Out.out.loglnErr("Directory did not exist! Trying to create it now... ");
			resourceDirectory.mkdir();
			Out.out.loglnErr("Not loading resources, directory didn't exist - therefore no resources can exist.");
		}else{
			loadResources();
		}
	}

	private void loadResources() {

		Out.out.logln("Loading resources from '" + resourceDirectory.getAbsolutePath() + "'.");
		try {
			File error = new File(
					resourceDirectory.getAbsolutePath() + File.separator + Main.game.getProperty("noResourceFound"));
			this.error = new Image(error.toURI().toString());

			this.rawResources.stream()
				.forEach(res -> {
					
					if(res.getResourceName() != null && !loadedResources.containsKey(res.getResourceName())){
						Out.out.logln(String.format("Loading '%s' [%d/%d].", res.getResourceName(), loadedResources.size(), rawResources.size()));
						if(!res.loadImage()){
							res.loadImage(this.error);
						}else{
							loadedResources.put(res.getResourceName(), res);
						}
					}else if(res.getResourceName() != null && loadedResources.containsKey(res.getResourceName())){
						res.loadImage(loadedResources.get(res.getResourceName()).getImage());
					}else{
						res.loadImage(this.error);
					}
					
				});
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Out.out.loglnErr("Failed to load resources! " + e.toString());
//			Out.out.loglnErr("Exiting game, cannot proceed without resources.");
//			System.exit(0);
		}

		//resources.entrySet().stream().forEach(e -> Out.out.logln("Loaded resource: " + e.getKey()));
		Out.out.logln("Resources loaded = " + this.loadedResources.size());
	}

	public static void newResourceManager() {
		Out.out.logln("Creating new resource manager @ '" + Main.game.getProperty("resourceDirectory") + "'.");
		rm = new ResourceManager(Main.game.getProperty("resourceDirectory"), Main.game.loadAllResources());
	}

	public static ResourceManager getResourceManager() {
		return rm;
	}
	
	public boolean hasImage(String key){
		return loadedResources.containsKey(key);
	}

	public Image getImage(String key) {
		Out.out.logln("Trying to retrieve '" + key + "' from ResourceManager.");
		Optional<Resource> res = loadedResources.entrySet().stream()
				.filter(k -> key.equals(k.getKey()))
				.map(v -> v.getValue())
				.findFirst();
		
		return res.isPresent() ? res.get().getImage() : loadedResources.containsKey("error") ? getImage("error") : null;
		
	}

//	public boolean add(String resourceName, Image image) {
//		if(!loadedResources.containsKey(resourceName)){
//			loadedResources.put(resourceName, image);
//			return true;
//		}
//		return false;
//	}

}
