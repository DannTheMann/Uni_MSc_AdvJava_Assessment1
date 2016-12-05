package advjava.assessment1.zuul.refactored.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

import advjava.assessment1.zuul.refactored.Main;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public abstract class Resource extends Descriptor{

	private String resourceName;
	private String imageURL;
	private String rawURL;
	private Image image;
	private static final String[] IMAGE_FORMATS = { "png", "svg", "tiff", "jpg", "jpeg" };

	public Resource(String name, String description, String url) {
		super(name, description);
		imageURL = url;
		rawURL = url;
		setImage();
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getRawImageURL(){
		return rawURL;
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public Image getImage() {
		return image;
	}

	public boolean loadImage() {

		if (imageURL == null)
			return false;

		try {

			File f = new File(imageURL);
			if (!f.exists()) {
				Out.out.loglnErr("Failed to load resource [NOT FOUND] : " + resourceName + " @ " + imageURL);
				return false;
			}

			image = new Image(f.toURI().toString());

		} catch (Exception e) {
			e.printStackTrace();
			Out.out.loglnErr("Failed to load resource: " + resourceName + " @ " + imageURL);
			return false;
		}

		return true;

	}

	public void loadImage(Image img) {
		if(img == null)
			throw new NullPointerException("Image cannot be null.");
		image = img;
	}

	public boolean update(String name, String description, String url) {
		super.update(name, description);
		if (this.imageURL == null) {
			this.imageURL = url;
			return setImage();
		}
		return false;
	}

	private boolean setImage() {
		if (imageURL != null && image == null) {
				imageURL = Main.RESOURCE_FILES + File.separator + imageURL;
			if (Arrays.stream(IMAGE_FORMATS).anyMatch(i -> imageURL.endsWith("." + i))) {
				resourceName = Paths.get(imageURL).getFileName().toString();
				return true;
			} else {
				Out.out.logln("Invalid imageURL given, cannot load resource: '" + imageURL + "'.");
				imageURL = null;
			}
		}
		return false;
	}

	public String getImageFileURL() {
		return new File(imageURL).toURI().toString();
	}
	
	public String getType() {
		return getClass().getName().toUpperCase();
	}

	public abstract void applyInformation(GridPane grid, String css);

}
