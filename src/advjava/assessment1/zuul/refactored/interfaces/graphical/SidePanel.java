package advjava.assessment1.zuul.refactored.interfaces.graphical;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.character.Character;
import advjava.assessment1.zuul.refactored.interfaces.FontManager;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.room.Room;
import advjava.assessment1.zuul.refactored.utils.Resource;
import java.util.stream.Stream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

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

	private Node root;
	private TilePane tileHolder;
	private FontManager fm;
	private Game game;
	private List<Node> grids;

	public SidePanel(String title, Stream<Resource> stream, FontManager fm, Game game) {
		this.grids = new ArrayList<>();
		this.game = game;
		this.fm = fm;

		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		tileHolder = new TilePane();
		tileHolder.setAlignment(Pos.BASELINE_CENTER);
		tileHolder.setPrefWidth(300);
		tileHolder.setHgap(NODE_HORIZONTAL_INSET);
		tileHolder.setVgap(NODE_VERTICAL_INSET);

		// Insets, in order of
		// top, right, bottom, left
		tileHolder.setPadding(new Insets(NODE_TOP_OFFSET, NODE_RIGHT_OFFSET, NODE_BOTTOM_OFFSET, NODE_LEFT_OFFSET));
		tileHolder.setPrefRows(4);
		stream.forEach(i -> tileHolder.getChildren().add(getDisplayItem(i)));

		sp.setContent(tileHolder);

		root = sp;

	}

	private Node getDisplayItem(Resource resource) {
		//Out.out.logln("Loading: " + resource.getName() + "...");

		String css = "sidebar-button";

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(NODE_TOP_OFFSET, NODE_LEFT_OFFSET, NODE_BOTTOM_OFFSET, NODE_RIGHT_OFFSET));
		grid.setHgap(NODE_RIGHT_OFFSET);
		grid.setVgap(NODE_BOTTOM_OFFSET);
		grid.setAlignment(Pos.BASELINE_CENTER);

		Text text = new Text(resource.getName().length() > MAX_WIDTH_CHAR
				? resource.getName().substring(0, MAX_WIDTH_CHAR - 2) + "..." : resource.getName());
		text.setTextAlignment(TextAlignment.CENTER);
		ImageView iv = new ImageView(resource.getImage());
		//iv.setPreserveRatio(true);
		iv.setFitHeight(SIDEBAR_IMAGE_HEIGHT);
		iv.setFitWidth(SIDEBAR_IMAGE_WIDTH);

		grid.add(text, 0, 0);
		grid.add(iv, 0, 1);

		if (resource instanceof Item) {
			Item item = (Item) resource;
			text.setText(text.getText() + System.lineSeparator() + "Weight: " + item.getWeight());
			text.setFont(fm.getFont("SansSerif"));

			// Create drop button
			Button button = GraphicalInterface.newCommandButton("drop " + resource.getName(),
					game.getCommandManager().getCommand("Drop"), css);
			button.setPrefSize(50, 20);
			grid.add(button, 1, 0);

			// Create give button
			button = GraphicalInterface.newCommandButton("give " + resource.getName(),
					game.getCommandManager().getCommand("Give"), css);
			button.setPrefSize(50, 20);

			grid.add(button, 1, 1);
		}

		if (resource instanceof Room) {

			Room room = (Room) resource;

			Button button = GraphicalInterface.newCommandButton(
					"go " + game.getPlayer().getCurrentRoom().getExitFromRoomName(resource.getName()),
					game.getCommandManager().getCommand("Go"), css);
			//button.setPrefSize(50, 20);
			grid.add(button, 0, 2);

		}

		if (resource instanceof Character) {

			Character c = (Character) resource;
			
			grid.setOnMouseClicked(
					GraphicalInterface.getCommandEvent(
							
							" " + c.getName(),
							
							game.getCommandManager().getCommand("Give")));

		}

		if (resource.getDescription() != null) {
			Tooltip tp = new Tooltip(
					resource.getName() + System.lineSeparator() + System.lineSeparator() + resource.getDescription());
			tp.setContentDisplay(ContentDisplay.BOTTOM);
			tp.setFont(fm.getFont("Yu Gothic"));
			tp.setOpacity(.85);
			modifyTooltipTimer(tp, 25);
			Tooltip.install(grid, tp);
		}
		
		grids.add(grid);

		return grid;
	}

	public Node getNode() {
		return root;
	}

	private static void modifyTooltipTimer(Tooltip tooltip, int delay) {
		try {
			Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objBehavior = fieldBehavior.get(tooltip);

			Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
			fieldTimer.setAccessible(true);
			Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

			objTimer.getKeyFrames().clear();
			objTimer.getKeyFrames().add(new KeyFrame(new Duration(delay)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Collection<Resource> newContents) {
		
		// Doesn't work right
		tileHolder.getChildren().removeIf(item->{
			return !newContents.stream()
				.filter(i->i.equals(item))
				.findAny()
				.isPresent();
			});
		
		// Works fine?
		newContents.stream()
			.filter(item->{
				return !grids.stream()
				.filter(i->i.equals(item))
			.findFirst()
				.isPresent();
			})
			.forEach(i->tileHolder.getChildren().add(getDisplayItem(i)));
		
	}

}
