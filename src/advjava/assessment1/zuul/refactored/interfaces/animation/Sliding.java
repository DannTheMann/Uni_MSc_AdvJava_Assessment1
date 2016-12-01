package advjava.assessment1.zuul.refactored.interfaces.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Sliding {
//
//	private Rectangle2D boxBounds = new Rectangle2D(100, 100, 300, 180);
//	private double ACTION_BOX_HGT = 30;
//	private SimpleBooleanProperty isExpanded = new SimpleBooleanProperty();
//	private Rectangle clipRect;
//	
//	public static void setAnimation(Node node){
//		/* Initial position setting for Top Pane*/
////		clipRect = new Rectangle();
////        node.setWidth(boxBounds.getWidth());
////        node.setHeight(ACTION_BOX_HGT);
////        node.translateXProperty().set(node.getParent().getTranslateX());
//			
//		Timeline timelineLeft;
//	 	Timeline timelineRight;
//		
//		/* Animation for bouncing effect. */
//		final Timeline timelineDown1 = new Timeline();
//		timelineDown1.setCycleCount(2);
//		timelineDown1.setAutoReverse(true);
//		final KeyValue kv1 = new KeyValue(node.translateXProperty(), (translateXProperty.getWidth()-15));
//		final KeyValue kv2 = new KeyValue(node.translateXProperty(), 15);
//		final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2);
//		timelineDown1.getKeyFrames().add(kf1);
//		
//		/* Event handler to call bouncing effect after the scroll down is finished. */
//		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//            	timelineDown1.play();
//            }
//        };
//        
//        timelineRight = new Timeline();
//        timelineLeft = new Timeline();
//        
//        /* Animation for scroll down. */
//		timelineRight.setCycleCount(1);
//		timelineRight.setAutoReverse(true);
//		final KeyValue kvDwn1 = new KeyValue(clipRect.widthProperty(), boxBounds.getWidth());
//		final KeyValue kvDwn2 = new KeyValue(clipRect.translateXProperty(), 0);
//		final KeyFrame kfDwn = new KeyFrame(Duration.millis(200), onFinished, kvDwn1, kvDwn2);
//		timelineRight.getKeyFrames().add(kfDwn);
//		
//		/* Animation for scroll up. */
//		timelineLeft.setCycleCount(1); 
//		timelineLeft.setAutoReverse(true);
//		final KeyValue kvUp1 = new KeyValue(clipRect.widthProperty(), ACTION_BOX_HGT);
//		final KeyValue kvUp2 = new KeyValue(clipRect.translateXProperty(), boxBounds.getWidth()-ACTION_BOX_HGT);
//		final KeyFrame kfUp = new KeyFrame(Duration.millis(200), kvUp1, kvUp2);
//		timelineLeft.getKeyFrames().add(kfUp);
//	}
	
}
