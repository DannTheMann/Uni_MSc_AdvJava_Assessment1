package advjava.assessment1.zuul.refactored.interfaces;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Main;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.Resource;
import advjava.assessment1.zuul.refactored.utils.ResourceManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GraphicalInterface extends Application implements UserInterface {

	private static Game game;
	private static FontManager fontManager;

	private static List<Button> commandButtons;
	private static Stage stage;
	private static Scene scene;
	private static BorderPane root;
	private static HBox commands;
	private static TilePane inventory;
	private static TilePane characters;
	private static TilePane exits;

	/* Constants for nodes used in gridpanes (indentations) */
	private static final int NODE_VERTICAL_INSET = 10;
	private static final int NODE_HORIZONTAL_INSET = 10;

	/* Constants for spacing offsets between nodes in gridpanes */
	private static final int NODE_LEFT_OFFSET = 25;
	private static final int NODE_TOP_OFFSET = 10;
	private static final int NODE_RIGHT_OFFSET = 25;
	private static final int NODE_BOTTOM_OFFSET = 10;

	public static String getExternalCSS() {
		return Main.XML_CONFIGURATION_FILES + File.separator + Main.game.getProperty("css");
	}

	@Override
	public void print(Object obj) {
		System.out.print(obj);
	}

	@Override
	public void println(Object obj) {
		System.out.println(obj);
	}

	@Override
	public void println() {
		System.out.println();
	}

	@Override
	public void printErr(Object obj) {
		System.err.print(obj);
	}

	@Override
	public void printlnErr(Object obj) {
		System.err.println(obj);
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayLocale(Object obj) {

	}

	@Override
	public void displaylnLocale(Object obj) {

	}

	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void play(Game zuulGame) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		game = Main.game;
		fontManager = new FontManager(game.getProperty("defaultFont"));
		stage = primaryStage;

		// Set title of the game from what is within our .properties file
		stage.setTitle(game.getProperty("title"));

		// Define the minimum resolution of the root container of the stage
		stage.setMinWidth(720);
		stage.setMinHeight(480);

		// Define the maximum resolution of the root container of the stage
		stage.setMaxWidth(1920);
		stage.setMaxHeight(1080);

		// Create resource manager, needed to handle resources such as images
		ResourceManager.newResourceManager();

		commands = getCommandHBox();

		setBackgroundImage("outside");

		/* Create root pane, a border pane */
		root = new BorderPane();
		// root.setStyle("");
		root.setStyle("" + "-fx-background-image: url(outside.jpg);" + "-fx-background-size: cover;");
		root.setBottom(commands);
		
		inventory = getSidePanel("-fx-background-color: rgba(59, 165, 100, 0.25); -fx-effect: dropshadow(gaussian, green, 50, 0, 0, 0);", game.getPlayer().getInventory());
		// 0.25); -fx-effect: dropshadow(gaussian, green, 50, 0, 0, 0);");

		characters = new TilePane();
		characters.setAlignment(Pos.BASELINE_CENTER);
		characters.setPrefWidth(200);
		characters.setHgap(NODE_HORIZONTAL_INSET);
		characters.setVgap(NODE_VERTICAL_INSET);

		// Insets, in order of
		// top, right, bottom, left
		characters.setPadding(new Insets(NODE_TOP_OFFSET, NODE_RIGHT_OFFSET, NODE_BOTTOM_OFFSET, NODE_LEFT_OFFSET));
		characters.setPrefRows(4);
		characters.setStyle(
				"-fx-background-color: rgba(255, 125, 0, 0.25); -fx-effect: dropshadow(gaussian, green, 50, 0, 0, 0);");

		// Load all items...
		// game.getPlayer().getInventory().stream().forEach(i->inventory.getChildren().add(getDisplayItem(i)));

		scene = new Scene(root, 1280, 720);
		scene.getStylesheets().add(getExternalCSS());

		stage.setScene(scene);

		stage.show();

	}

	private Node getDisplayItem(Resource resource) {

		Out.out.logln("Loading: " + resource.getName() + "...");
		
		  VBox vbox = new VBox();
		  vbox.setPadding(new Insets(NODE_TOP_OFFSET, NODE_RIGHT_OFFSET, NODE_BOTTOM_OFFSET, NODE_LEFT_OFFSET));
//		Rectangle r = new Rectangle();
//		r.setWidth(75);
//		r.setHeight(75);
//		r.setArcWidth(10);
//		r.setArcHeight(10);

		Text text = new Text(resource.getName() + (resource.getDescription() != null ? System.lineSeparator() + resource.getDescription() : ""));
		ImageView iv = new ImageView(resource.getImage());
		iv.setFitWidth(100);
		iv.setFitHeight(100);
		
		vbox.getChildren().add(iv);
		
		if (resource instanceof Item) {
			Item item = (Item) resource;
			text.setText(text.getText() + System.lineSeparator() + item.getWeight());
			Button button = new Button("Drop");
			button.setOnAction(e -> {
				parameters = "drop " + resource.getName().toLowerCase();
				executeCommand(game.getCommandManager().getCommand("Drop"), e);
				parameters = "";
			});
			vbox.getChildren().addAll(button);
		}

		vbox.getChildren().addAll(text);
		return vbox;
		// return new Button(i.getName());
	}

	private void setBackgroundImage(String resource) {
		ImageView iv = new ImageView(ResourceManager.getResourceManager().getImage(resource));
		iv.fitWidthProperty().bind(stage.widthProperty());
		iv.fitHeightProperty().bind(stage.heightProperty());
		// ...
	}

	private TilePane getSidePanel(String css, Collection<Resource> stream) {
		TilePane pane = new TilePane();
		pane.setAlignment(Pos.BASELINE_CENTER);
		pane.setPrefWidth(200);
		pane.setHgap(NODE_HORIZONTAL_INSET);
		pane.setVgap(NODE_VERTICAL_INSET);

		// Insets, in order of
		// top, right, bottom, left
		pane.setPadding(new Insets(NODE_TOP_OFFSET, NODE_RIGHT_OFFSET, NODE_BOTTOM_OFFSET, NODE_LEFT_OFFSET));
		pane.setPrefRows(4);
		pane.setStyle(css);
		Out.out.logln("Adding player inventory... [" + stream.size() + "]");
		stream.forEach(i -> pane.getChildren().add(getDisplayItem(i)));
		return pane;
	}

	private HBox getCommandHBox() {
		HBox hbox = new HBox();
		hbox.setPrefHeight(100);
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		commandButtons = new ArrayList<>(game.getCommandManager().commands().size());

		Button buttonCurrent = null;

		for (Command command : game.getCommandManager().commands()) {

			if (!command.interfaceAcceptable(this)) {
				continue;
			}

			buttonCurrent = new Button(command.getName());
			buttonCurrent.setPrefSize(100, 20);
			commandButtons.add(buttonCurrent);
			buttonCurrent.setOnAction((event) -> {
				executeCommand(command, event);
			});
			hbox.getChildren().add(buttonCurrent);

		}

		return hbox;
	}

	private String parameters = "hello darkness my old friend";

	private void executeCommand(Command cmd, ActionEvent event) {

		Out.out.logln(event.getEventType().getName() + " > [" + cmd.getName() + "] >> " + parameters);

		CommandExecution ce = new CommandExecution(parameters);

		cmd.action(game, ce);

	}

	@Override
	public void showInventory() {

		if (root.getLeft() != null) {
			root.setLeft(null);
			return;
		}

		disableOtherWindows();

		root.setLeft(inventory);

		// sliding transition... from left ...

	}

	@Override
	public void showCharacters() {
		if (root.getRight() != null) {
			root.setRight(null);
			return;
		}

		disableOtherWindows();

		root.setRight(characters);

		// sliding transition... from left ...
	}

	@Override
	public void showRoom() {

	}

	@Override
	public void showExits() {

	}

	private void disableOtherWindows() {
		root.setLeft(null);
		root.setRight(null);
		root.setTop(null);
	}

	public String getCurrentParameters() {
		return parameters;
	}

}
