package advjava.assessment1.zuul.refactored.interfaces;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Main;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.graphical.SidePanel;
import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.Resource;
import advjava.assessment1.zuul.refactored.utils.ResourceManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GraphicalInterface extends Application implements UserInterface {
	
	private static Game game;
	private static FontManager fontManager;

	private static List<Button> commandButtons;
	private static Stage stage;
	private static Scene scene;
	private static BorderPane root;
	private static HBox commands;
	private static SidePanel inventory;
	private static SidePanel characters;
	private static SidePanel exits;
	private static String parameters = "hello darkness my old friend";

	/* Constants for spacing offsets between nodes in gridpanes */
//	private static final int NODE_LEFT_OFFSET = 10;
//	private static final int NODE_TOP_OFFSET = 10;
//	private static final int NODE_RIGHT_OFFSET = 10;
//	private static final int NODE_BOTTOM_OFFSET = 10;

//	private static final int SIDEBAR_IMAGE_WIDTH = 50;
//	private static final int SIDEBAR_IMAGE_HEIGHT = 50;
//	private static final int MAX_WIDTH_CHAR = 8;
	
	public static String getExternalCSS() {
		return new File(Main.XML_CONFIGURATION_FILES + File.separator + Main.game.getProperty("css")).toURI().toString();
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
		
		// Update all panels, change anything that may have been
		// used, dropped etc
		exits.update(game.getPlayer().getCurrentRoom().getExits());
		inventory.update(game.getPlayer().getInventory());
		characters.update(game.getPlayer().getCurrentRoom().getNonPlayerCharacters());
		
		// Let the characters when we move
		game.getCharacterManager().act(game);
		
		return true;
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

		/* Create root pane, a border pane */
		root = new BorderPane();
		// root.setStyle("");
		root.setStyle("root");
		root.setBottom(commands);
		
		inventory = new SidePanel("Inventory", game.getPlayer().getInventory(), fontManager, game);
		characters = new SidePanel("Characters in the room", game.getPlayer().getCurrentRoom().getNonPlayerCharacters(), fontManager, game);	
		exits = new SidePanel("Exits available", game.getPlayer().getCurrentRoom().getExits(), fontManager, game);

		scene = new Scene(root, 1280, 720);
		
		// Setup styling
		scene.getStylesheets().add(getExternalCSS());

		setBackgroundImage(game.getPlayer().getCurrentRoom());
		
		Out.out.log("Room: " + game.getPlayer().getCurrentRoom().getName());
		
		stage.setScene(scene);

		stage.show();

	}
	
	public static EventHandler<Event> getCommandEvent(String params, Command command){
		return new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
    			parameters += params;
    			executeCommand(command, e);
            }
        };
	}

	public static Button newCommandButton(String params, Command command, String css) {
		Button button = new Button(command.getName());
		button.getStyleClass().add(css);
		button.setOnAction(e -> {
			parameters = params;
			executeCommand(command, e);
		});
		return button;
	}

	private void setBackgroundImage(Resource image) {
		
		root.setStyle(
				"-fx-background-image: url(" + image.getImageFileURL() + ");"
				+ " -fx-background-size: cover;");
		
//		ImageView iv = new ImageView(ResourceManager.getResourceManager().getImage(resource));
//		iv.fitWidthProperty().bind(stage.widthProperty());
//		iv.fitHeightProperty().bind(stage.heightProperty());
		// ...
	}

	private HBox getCommandHBox() {
		HBox hbox = new HBox();
		hbox.setPrefHeight(50);
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		commandButtons = new ArrayList<>(game.getCommandManager().commands().size());

		Button buttonCurrent = null;

		for (Command command : game.getCommandManager().commands()) {

			if (!command.interfaceAcceptable(this)) {
				continue;
			}

			buttonCurrent = newCommandButton("", command, "command-button");
			buttonCurrent.setPrefSize(100, 20);
			hbox.getChildren().add(buttonCurrent);

		}

		return hbox;
	}

	private static void executeCommand(Command cmd, Event event) {

		Out.out.logln(event.getEventType().getName() + " > [" + cmd.getName() + "] >> " + parameters + " >> " + game.getInterface().getCurrentParameters());

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

		root.setLeft(inventory.getNode());
		root.setBottom(commands);

		// sliding transition... from left ...

	}

	@Override
	public void showCharacters() {
		if (root.getRight() != null) {
			root.setRight(null);
			return;
		}

		disableOtherWindows();

		root.setRight(characters.getNode());
		root.setBottom(commands);

		// sliding transition... from left ...
	}

	@Override
	public void showRoom() {

	}

	@Override
	public void showExits() {
		if (root.getTop() != null) {
			root.setTop(null);
			return;
		}

		disableOtherWindows();

		root.setTop(exits.getNode());
		root.setBottom(commands);
	}

	private void disableOtherWindows() {
		root.setLeft(null);
		root.setRight(null);
		root.setTop(null);
		root.setBottom(null);
	}

	public String getCurrentParameters() {
		return parameters;
	}
}
