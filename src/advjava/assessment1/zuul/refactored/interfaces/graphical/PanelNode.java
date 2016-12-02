package advjava.assessment1.zuul.refactored.interfaces.graphical;

import advjava.assessment1.zuul.refactored.utils.Resource;
import javafx.scene.Node;

public class PanelNode {
	
	private final Resource objectReference;
	private final Node node;
	
	public PanelNode(Node node, Resource objRef){
		this.node = node;
		this.objectReference = objRef;
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

}
