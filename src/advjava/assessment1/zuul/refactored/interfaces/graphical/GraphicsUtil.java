package advjava.assessment1.zuul.refactored.interfaces.graphical;

import java.lang.reflect.Field;

import advjava.assessment1.zuul.refactored.utils.Out;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
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
}
