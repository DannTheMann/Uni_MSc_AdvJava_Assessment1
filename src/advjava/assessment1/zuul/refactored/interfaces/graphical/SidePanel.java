package advjava.assessment1.zuul.refactored.interfaces.graphical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	private static final int MAX_WIDTH_CHAR = 8;

	private final ScrollPane root;
	private final TilePane tileHolder;
	private final FontManager fm;
	private final List<PanelNode> grids;
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
		this.grids = new ArrayList<>();
		this.fm = fm;
		this.css = cssStyling;
		this.emptyMessage = empty;
		this.SIDEBAR_IMAGE_HEIGHT = nodeImageHeight;
		this.SIDEBAR_IMAGE_WIDTH = nodeImageWidth;
		this.SIDEBAR_NODE_HEIGHT = nodeHeight;
		this.SIDEBAR_NODE_WIDTH = nodeWidth;
		this.PANEL_WIDTH = 300;

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

		stream.forEach(i -> tileHolder.getChildren().add(getDisplayItem(i)));

		checkIfPanelIsEmpty();
		sp.setContent(tileHolder);
		//sp.setVisible(false);
		root = sp;

	}

	private void checkIfPanelIsEmpty() {
		if (grids.isEmpty()) {
			Text text = new Text(emptyMessage);
			text.setFont(fm.getFont("EMPTY_FONT"));
			grids.add(new PanelNode(text, null));
			tileHolder.getChildren().add(text);
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

	private Node getDisplayItem(Resource resource) {

		Out.out.logln("");

		StackPane root = new StackPane();

		root.getChildren()
				.add(GraphicsUtil.createNewRectangle(css + "-node-root", SIDEBAR_NODE_WIDTH, SIDEBAR_NODE_HEIGHT));

		{

			GridPane grid = createGridPane();

			{
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
			Tooltip.install(grid,
					GraphicsUtil.createNewToolTip(
							(resource.getName().length() > MAX_WIDTH_CHAR ? resource.getName()+ System.lineSeparator() + System.lineSeparator() : "")
								 + resource.getDescription(),
							fm.getFont("Yu Gothic"), 25));

			root.getChildren().add(grid);

		}

		// Add to our knowledge of current grids loaded
		grids.add(new PanelNode(root, resource));

		return root;
	}

	public Node getNode() {
		return root;
	}

	public void update(Collection<Resource> newContents) {

		if (grids.size() > 0) {
			grids.removeIf(node -> {
				if (!node.isValid()) {
					tileHolder.getChildren().remove(node.getNode());
					return true;
				} else {
					return false;
				}
			});
		}

		grids.removeIf(oldPanelNode -> {
			if (!newContents.stream().filter(newResource -> newResource.equals(oldPanelNode.getObjectReference()))
					.findAny().isPresent()) {
				tileHolder.getChildren().remove(oldPanelNode.getNode());
				return true;
			} else {
				return false;
			}
		});

		// Works fine?
		newContents.stream()
				// Filter resources by what's currently
				// loaded in the GridPane
				.filter(newResource ->
				// if NOT the newResource is in the gridpane
				!grids.stream()
						// If the hashcodes of these two objects match
						.filter(oldResource -> oldResource.getObjectReference() == newResource.hashCode()).findFirst()
						.isPresent())
				.forEach(newResource -> {
					PanelNode newNode = new PanelNode(getDisplayItem(newResource), newResource);
					grids.add(newNode);
					tileHolder.getChildren().add(newNode.getNode());
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

		showTransition = new PathTransition();
		showTransition.setDuration(Duration.millis(delay));
		showTransition.setPath(path);
		showTransition.setNode(root);
		showTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Out.out.logln("Shown: " + title + " -> " + getNode().getClass().getName());
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
		hideTransition = new PathTransition();
		hideTransition.setDuration(Duration.millis(delay));
		hideTransition.setPath(path);
		hideTransition.setNode(root);
		hideTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Out.out.logln("Hidden: " + title + " -> " + getNode().getClass().getName());
				root.setVisible(false);
				hideTransition.stop();
			}
		});

	}

	public void hide() {
		Out.out.logln("Hiding: " + title + " -> " + getNode().getClass().getName());

		if (isHidden())
			return;

		hideTransition.play();
	}

	public void show() {
		Out.out.logln("Showing: " + title + " -> " + getNode().getClass().getName());
		getNode().setVisible(true);
		showTransition.play();
	}

	public boolean isHidden() {
		return !getNode().isVisible();
	}

}
