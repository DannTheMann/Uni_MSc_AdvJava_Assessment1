package advjava.assessment1.zuul.refactored.utils;

import java.io.File;

import advjava.assessment1.zuul.refactored.Main;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.image.Image;

public class ResourceManager {

    private static ResourceManager rm;
    private Image errorImage;
    private final File resourceDirectory;
    private List<Resource> resources;

    private ResourceManager(String dir) {
        //resources = new HashMap<>();
        resources = new ArrayList<>();
        resourceDirectory = new File(dir);

        if (!resourceDirectory.exists()) {
            Out.out.loglnErr("Directory did not exist! Trying to create it now... ");
            resourceDirectory.mkdir();
            Out.out.logln("Directory created, not loading resources as the directory didn't exist anyway.");
        } else {
            loadResources();
        }
    }

    private void loadResources() {

        Out.out.logln("Loading resources from '" + resourceDirectory.getAbsolutePath() + "'.");
        try {
            File error = new File(
                    resourceDirectory.getAbsolutePath() + File.separator + Main.game.getProperty("noResourceFound"));
            errorImage = new Image(error.toURI().toString());

            
            Main.game.getCharacterManager().characters().stream().forEach(r -> {
                r.loadImage(this, errorImage);
                resources.add(r);
            });

            Main.game.getRoomManager().rooms().stream().forEach(r -> {
                r.loadImage(this, errorImage);
                resources.add(r);
            });

            Main.game.getItemManager().items().stream().forEach(r -> {
                r.loadImage(this, errorImage);
                resources.add(r);
            });

        } catch (Exception e) {
            Out.out.loglnErr("Failed to load resources! " + e.toString());
            Out.out.loglnErr("Exiting game, cannot proceed without resources.");
            System.exit(0);
        }

        //resources.entrySet().stream().forEach(e -> Out.out.logln("Loaded resource: " + e.getKey()));
        //Out.out.logln("Resources loaded = " + resources.size());
    }

    public static void newResourceManager() {
        Out.out.logln("Creating new resource manager @ '" + Main.game.getProperty("resourceDirectory") + "'.");
        rm = new ResourceManager(Main.game.getProperty("resourceDirectory"));
    }

    public static ResourceManager getResourceManager() {
        return rm;
    }

//	public boolean containsImage(String key){
//		return resources.containsKey(key);
//	}
//
    public Image getImage(String key) {
        
        Optional<Resource> res = resources.stream()
                .filter(r->r.getResourceName().equals(key))
                .findFirst();
        
        return res.isPresent() ? res.get().getImage() : errorImage;
    }

    public int getSize() {
        throw new UnsupportedOperationException();
    }

    public boolean containsImage(String resourceName) {
        return false;
    }

}
