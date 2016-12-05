package advjava.assessment1.zuul.refactored.interfaces.graphical;

import advjava.assessment1.zuul.refactored.utils.Resource;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

/**
 * Wrapper class for Panes and Panels
 * @author Dante
 *
 */
public class PanelNode {
	
	private final Resource objectReference;
	private final Node node;
	private final GridPane gp;
	private final Tooltip tool;
	
	public PanelNode(Node node, GridPane gp, Resource objRef, Tooltip info){
		this.node = node;
		this.objectReference = objRef;
		this.tool = info;
		this.gp = gp;
	}
	
	public boolean isValid(){
		return objectReference != null;
	}
	
	public String getId(){
		return objectReference.getName();//getId(objectReference);
	}
	
	public Node getNode(){
		return node;
	}
	
	public int getObjectReference(){
		return objectReference.hashCode();
	}

	public static String getId(Resource resource) {
		return (resource.getType() + "_" + resource.getName() + "_" + resource.getResourceName()).toUpperCase();
	}
	
	public void update(){
		tool.setText((objectReference.getName().length() > SidePanel.MAX_WIDTH_CHAR ? objectReference.getName()+ System.lineSeparator() + System.lineSeparator() : "")
						 + objectReference.getDescription());
		objectReference.applyInformation(gp, gp.getStyle());
	}

}
