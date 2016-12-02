package advjava.assessment1.zuul.refactored.interfaces.graphical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.interfaces.FontManager;
import advjava.assessment1.zuul.refactored.utils.Resource;
import java.util.stream.Stream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SidePanel {

	/* Constants for nodes used in gridpanes (indentations) */
	private static final int NODE_VERTICAL_INSET = 10;
	private static final int NODE_HORIZONTAL_INSET = 10;

	/* Constants for spacing offsets between nodes in gridpanes */
	private static final int NODE_LEFT_OFFSET = 0;
	private static final int NODE_TOP_OFFSET = 10;
	private static final int NODE_RIGHT_OFFSET = 10;
	private static final int NODE_BOTTOM_OFFSET = 10;

	private static final int SIDEBAR_IMAGE_WIDTH = 50;
	private static final int SIDEBAR_IMAGE_HEIGHT = 50;
	private static final int MAX_WIDTH_CHAR = 8;

	private final Node root;
	private final TilePane tileHolder;
	private final FontManager fm;
	private final List<PanelNode> grids;
	private final String css;
	private final String emptyMessage;

	public SidePanel(String title, String empty, Stream<Resource> stream, FontManager fm, Game game,
			String cssStyling) {
		this.grids = new ArrayList<>();
		this.fm = fm;
		this.css = cssStyling;
		this.emptyMessage = empty;

		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		tileHolder = new TilePane();
		tileHolder.getStyleClass().add(cssStyling);
		tileHolder.setAlignment(Pos.BASELINE_CENTER);
		tileHolder.setPrefWidth(300);
		tileHolder.setHgap(NODE_HORIZONTAL_INSET);
		tileHolder.setVgap(NODE_VERTICAL_INSET);

		// Insets, in order of
		// top, right, bottom, left
		tileHolder.setPadding(new Insets(NODE_TOP_OFFSET, NODE_RIGHT_OFFSET, NODE_BOTTOM_OFFSET, NODE_LEFT_OFFSET));
		tileHolder.setPrefRows(4);

		Text textTitle = new Text(title);
		textTitle.setStyle("sidebar-title");

		// tileHolder.getChildren().add(textTitle);
		// tileHolder.getChildren().add(new Text());
		
		stream.forEach(i -> tileHolder.getChildren().add(getDisplayItem(i)));
		
		checkIfPanelIsEmpty();

		sp.setContent(tileHolder);

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

	private ImageView createIconImageView(Resource resource) {
		ImageView iv = new ImageView(resource.getImage());
		//iv.setPreserveRatio(true);
		iv.setFitHeight(SIDEBAR_IMAGE_HEIGHT);
		iv.setFitWidth(SIDEBAR_IMAGE_WIDTH);
		return iv;
	}

	private Text createIconText(String txt) {
		Text text = new Text(txt);
		text.setTextAlignment(TextAlignment.CENTER);
		return text;
	}

	private Node getDisplayItem(Resource resource) {

		GridPane grid = createGridPane();

		// Text from the resource, if the resource name exceeds
		// MAX_WIDTH_CHAR then slice off and concat with "..."
		Text text = createIconText(resource.getName().length() > MAX_WIDTH_CHAR
				? " " + resource.getName().substring(0, MAX_WIDTH_CHAR - 2) + "..." : " " + resource.getName());

		// Add the text and create an image of the resource
		grid.add(text, 0, 0);
		grid.add(createIconImageView(resource), 0, 1);
		
		// Will add buttons (commands), text and other nodes
		// based on whether this is an item, character or room.
		// Items will have either drop, give or take
		// Characters will have mouse events applied to them
		// Rooms will have a Go command
		resource.applyInformation(grid, text, resource, css);

		// Apply the description in this manner, makes much
		// more neater and less in our face
		Tooltip.install(grid, GraphicsUtil.createNewToolTip(
				resource.getName() + System.lineSeparator() + System.lineSeparator() + resource.getDescription(),
				fm.getFont("Yu Gothic"), 25));

		// Add to our knowledge of current grids loaded
		grids.add(new PanelNode(grid, resource));

		return grid;
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

}
