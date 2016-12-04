package advjava.assessment1.zuul.refactored.interfaces.graphical;

import java.lang.reflect.Field;

import advjava.assessment1.zuul.refactored.utils.Out;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class GraphicsUtil {
	
	public static Tooltip createNewToolTip(String message, Font font, double delay) {
		Tooltip tp = new Tooltip(message);
		tp.setContentDisplay(ContentDisplay.BOTTOM);
		tp.setFont(font);
		tp.setOpacity(.85);
		modifyTooltipTimer(tp, delay);
		return tp;
	}
	
    public static void modifyTooltipTimer(Tooltip tooltip, double delay) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(delay)));
            
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            Out.out.loglnErr("Failed to modify ToolTip!");
            e.printStackTrace();
        }
    }
    
    public static Rectangle createNewRectangle(String css, int width, int height){
    	Rectangle r = new Rectangle();
    	r.getStyleClass().add(css);
    	r.setX(50);
    	r.setY(50);
    	r.setWidth(width);
    	r.setHeight(height);
    	r.setArcWidth(25);	
    	r.setArcHeight(25);	
    	return r;
    }
    
    public static ImageView createIconImage(Image image, int width, int height){
		ImageView iv = new ImageView(image);
		iv.setFitHeight(height);
		iv.setFitWidth(width);
		iv.setPreserveRatio(true);
		return iv;
    }
    
    public static Text createNewText(String msg, Font font, String css){
    	Text text = new Text(msg);
    	text.setFont(font);
    	text.getStyleClass().add(css);
		text.setTextAlignment(TextAlignment.CENTER);
    	return text;	
    }
    
    public static Text createNewText(String msg, String css){
    	Text text = new Text(msg);
    	text.getStyleClass().add(css);
    	return text;	
    }
    

	public static void showAlert(String title, String mast, String body, AlertType dialogType) {
		Alert alert = new Alert(dialogType, body, ButtonType.OK);
		alert.setTitle(title);
		alert.setHeaderText(mast);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.show();
	}
}
