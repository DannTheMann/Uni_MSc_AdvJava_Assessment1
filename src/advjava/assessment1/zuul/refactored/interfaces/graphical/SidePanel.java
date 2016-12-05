package advjava.assessment1.zuul.refactored.interfaces.graphical;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.interfaces.FontManager;
import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.Resource;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SidePanel {

	/* Constants for nodes used in gridpanes (indentations) */
	private static final int NODE_VERTICAL_INSET = 10;
	private static final int NODE_HORIZONTAL_INSET = 10;

	/* Constants for spacing offsets between nodes in gridpanes */
	private static final int NODE_LEFT_OFFSET = 3;
	private static final int NODE_TOP_OFFSET = 5;
	private static final int NODE_RIGHT_OFFSET = 3;
	private static final int NODE_BOTTOM_OFFSET = 5;

	private int PANEL_WIDTH;
	// private final int PANEL_HEIGHT;

	private final int SIDEBAR_IMAGE_WIDTH;
	private final int SIDEBAR_IMAGE_HEIGHT;
	private final int SIDEBAR_NODE_HEIGHT;
	private final int SIDEBAR_NODE_WIDTH;
	public static final int MAX_WIDTH_CHAR = 8;

	private final ScrollPane root;
	private final TilePane tileHolder;
	private final FontManager fm;
	private final Text emptyText;
	private final Map<Integer, PanelNode> grids;
	private final String title;
	private final String css;
	private final String emptyMessage;

	public SidePanel(String title, String empty, Stream<Resource> stream, FontManager fm, Game game, String cssStyling,
			int nodeImageWidth, int nodeImageHeight, int nodeWidth, int nodeHeight, int width, int height) {
		this(title, empty, stream, fm, game, cssStyling, nodeImageWidth, nodeImageHeight, nodeWidth, nodeHeight);
		// this.PANEL_HEIGHT = height;
		this.PANEL_WIDTH = width;
	}

	public SidePanel(String title, String empty, Stream<Resource> stream, FontManager fm, Game game, String cssStyling,
			int nodeImageWidth, int nodeImageHeight, int nodeWidth, int nodeHeight) {
		this.title = title;
		this.grids = new TreeMap<>();
		this.fm = fm;
		this.css = cssStyling;
		this.emptyMessage = empty;
		this.SIDEBAR_IMAGE_HEIGHT = nodeImageHeight;
		this.SIDEBAR_IMAGE_WIDTH = nodeImageWidth;
		this.SIDEBAR_NODE_HEIGHT = nodeHeight;
		this.SIDEBAR_NODE_WIDTH = nodeWidth;
		this.PANEL_WIDTH = 300;

		this.emptyText = new Text(emptyMessage);
		this.emptyText.setFont(fm.getFont("EMPTY_FONT"));
		
		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		tileHolder = new TilePane();
		tileHolder.getStyleClass().add(cssStyling);
		tileHolder.setAlignment(Pos.CENTER);
		tileHolder.setPrefWidth(PANEL_WIDTH);
		// tileHolder.setPrefHeight(PANEL_HEIGHT);
		tileHolder.setHgap(NODE_HORIZONTAL_INSET);
		tileHolder.setVgap(NODE_VERTICAL_INSET);

		// Insets, in order of
		// top, right, bottom, left
		tileHolder.setPadding(new Insets(NODE_TOP_OFFSET, NODE_RIGHT_OFFSET, NODE_BOTTOM_OFFSET, NODE_LEFT_OFFSET));
		// tileHolder.setPrefRows(4);

		Text textTitle = new Text(title);
		textTitle.setStyle("sidebar-title");

		stream.forEach(i -> tileHolder.getChildren().add(getDisplayItem(i).getNode()));

		checkIfPanelIsEmpty();
		sp.setContent(tileHolder);
		sp.setVisible(false);
		root = sp;

	}

	private void checkIfPanelIsEmpty() {
		if(tileHolder.getChildren().size() == 0){
			tileHolder.getChildren().add(emptyText);
		}else if(tileHolder.getChildren().contains(emptyText)){
			tileHolder.getChildren().remove(emptyText);
		}
	}

	private GridPane createGridPane() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(NODE_TOP_OFFSET, NODE_LEFT_OFFSET, NODE_BOTTOM_OFFSET, NODE_RIGHT_OFFSET));
		grid.setHgap(NODE_RIGHT_OFFSET);
		grid.setVgap(NODE_BOTTOM_OFFSET);
		grid.setAlignment(Pos.CENTER);
		return grid;

	}

	private PanelNode getDisplayItem(Resource resource) {

		StackPane root = new StackPane();
		GridPane grid = null;
		Tooltip info = null;
		
		root.getChildren()
				.add(GraphicsUtil.createNewRectangle(css + "-node-root", SIDEBAR_NODE_WIDTH, SIDEBAR_NODE_HEIGHT));

		{

			grid = createGridPane();

			{
				/* Create and display a visual representation of the resource */
				StackPane cover = new StackPane();
				cover.getChildren().add(GraphicsUtil.createNewRectangle("node-image", SIDEBAR_IMAGE_WIDTH + 15,
						SIDEBAR_IMAGE_HEIGHT + 15));
				cover.getChildren().add(
						GraphicsUtil.createIconImage(resource.getImage(), SIDEBAR_IMAGE_WIDTH, SIDEBAR_IMAGE_HEIGHT));

				cover.setAlignment(Pos.CENTER);

				grid.add(cover, 0, 1);
				grid.add(new StackPane(GraphicsUtil.createNewText(resource.getName().length() > MAX_WIDTH_CHAR
						? " " + resource.getName().substring(0, MAX_WIDTH_CHAR - 2) + "..." : " " + resource.getName(),
						"node-text")), 0, 0);
			}

			// Will add buttons (commands), text and other nodes
			// based on whether this is an item, character or room.
			// Items will have either drop, give or take
			// Characters will have mouse events applied to them
			// Rooms will have a Go command
			resource.applyInformation(grid, css);

			// Apply the description in this manner, makes much
			// more neater and less in our face
			
			info = GraphicsUtil.createNewToolTip(
					(resource.getName().length() > MAX_WIDTH_CHAR ? resource.getName()+ System.lineSeparator() + System.lineSeparator() : "")
						 + resource.getDescription(),
					fm.getFont("Yu Gothic"), 25);
			
			Tooltip.install(grid, info);

			root.getChildren().add(grid);

		}

		// Add to our knowledge of current grids loaded
		PanelNode pn = new PanelNode(root, grid, resource, info);
		grids.put(resource.hashCode() , pn);

		return pn;
	}

	public Node getNode() {
		return root;
	}

	public void update(Collection<Resource> newContents) {
		
		// For all new resources
		newContents.stream()
			// For all current resources
		.filter(newResource->!grids.entrySet().stream()
			// If new resource does not match old resource
			.filter(oldResource->newResource.hashCode()
				== oldResource.getKey())
			.findAny()
			.isPresent())
		// For all resources filtered, add them
		.forEach(newResource->{
			PanelNode pn = getDisplayItem(newResource);
			tileHolder.getChildren().add(pn.getNode());
			}
		);
		
		
		// Update Node information
		grids.values().stream()
			.forEach(PanelNode::update);
		
		// Remove resources that are no longer present 
		grids.entrySet().removeIf(oldResource->{
			// For every new resource
			if(!newContents.stream()
					// Compare hashcode against current resource
					.filter(newResource->newResource.hashCode()
							== oldResource.getKey())
					.findAny() 
					.isPresent()){
				// If not found, then remove old resource
				tileHolder.getChildren()
				.remove(oldResource.getValue().getNode());
				return true;
			}else{
				return false;
			}		
		});
		
		checkIfPanelIsEmpty();
	}

	public String getCSS() {
		return css;
	}

	private PathTransition hideTransition;
	private PathTransition showTransition;

	public void setSlideAnimation(BorderPane rootPane, SlideAnimation type, int delay) {
		setSlideAnimation(root, rootPane, type, delay);
	}
	
//	private boolean contains(Resource r){
//		return grids.stream().filter(res->res.getObjectReference() == r.hashCode()).findFirst().isPresent();
//	}

	public void setSlideAnimation(Node root, Pane rootPane, SlideAnimation type, int delay) {

		Out.out.logln("Setting animation: " + title);

		int startX = 0;
		int startY = 0;
		int moveTo = 0;

		switch (type) {

		case LEFT:
			startX = -(PANEL_WIDTH / 2);
			startY = (int) (rootPane.getHeight() / 3);
			moveTo = PANEL_WIDTH - (PANEL_WIDTH / 2);
			break;

		case RIGHT:
			startX = (int) rootPane.getWidth();
			startY = (int) (rootPane.getHeight() / 3);
			moveTo = (int) PANEL_WIDTH - (PANEL_WIDTH / 2);
			break;

		case TOP:
			startX = (int) rootPane.getHeight() - 80;
			startY = (int) -rootPane.getWidth();
			moveTo = (int) 80;
			break;
		case BOTTOM:
			startX = (int) (rootPane.getWidth() / 2);
			startY = (int) (rootPane.getHeight());
			moveTo = (int) rootPane.getHeight() / 4;
			break;
		default:
			return;

		}

		/* Show animation */
		Path path = new Path();
		path.getElements().add(new MoveTo(startX, startY));

		if (type == SlideAnimation.LEFT || type == SlideAnimation.RIGHT) {
			path.getElements().add(new HLineTo(moveTo));
		} else {
			path.getElements().add(new VLineTo(moveTo));
		}

		showTransition = createPathTransition(delay, path, root);
		showTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Out.out.logln("Shown: " + title + " -> " + getNode().getClass().getName());
				showTransition.stop();
			}
		});

		/* Hide animation */
		path = new Path();

		if (type == SlideAnimation.LEFT || type == SlideAnimation.RIGHT) {
			path.getElements().add(new MoveTo(moveTo, startY));
			path.getElements().add(new HLineTo(startX));
		} else {
			path.getElements().add(new MoveTo(startX, moveTo));
			path.getElements().add(new VLineTo(startY));
		}
		hideTransition = createPathTransition(delay, path, root);
		hideTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Out.out.logln("Hidden: " + title + " -> " + getNode().getClass().getName());
				root.setVisible(false);
				hideTransition.stop();
			}
		});

	}
	
	private PathTransition createPathTransition(int delay, Path path, Node node){
		PathTransition newTransition = new PathTransition();
		newTransition.setDuration(Duration.millis(delay));
		newTransition.setPath(path);
		newTransition.setNode(root);
		return newTransition;
	}

	public void hide() {
		//Out.out.logln("Hiding: " + title + " -> " + getNode().getClass().getName());

		if (isHidden())
			return;

		hideTransition.play();
	}

	public void show() {
		//Out.out.logln("Showing: " + title + " -> " + getNode().getClass().getName());
		getNode().setVisible(true);
		showTransition.play();
	}

	public boolean isHidden() {
		return !getNode().isVisible();
	}

}
