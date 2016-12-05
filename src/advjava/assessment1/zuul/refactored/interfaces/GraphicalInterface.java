package advjava.assessment1.zuul.refactored.interfaces;

import java.io.File;
import java.util.stream.Stream;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Main;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.interfaces.graphical.CentralPanel;
import advjava.assessment1.zuul.refactored.interfaces.graphical.GraphicsUtil;
import advjava.assessment1.zuul.refactored.interfaces.graphical.SidePanel;
import advjava.assessment1.zuul.refactored.interfaces.graphical.SlideAnimation;
import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.Resource;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.AudioManager;
import advjava.assessment1.zuul.refactored.utils.resourcemanagers.ResourceManager;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GraphicalInterface extends Application implements UserInterface {

	// Game reference to access logic in game
	private static Game game;
	private static FontManager fontManager;

	// All Pane and Node components for the GUI
	private static Stage stage;
	private static Scene scene;
	private static BorderPane root;
	private static StackPane playerToolbar;
	private static Text playerWeight;
	private static Text commandTranslation;
	private static SidePanel inventory;
	private static SidePanel characters;
	private static SidePanel exits;
	private static CentralPanel room;
	private static String parameters = ""; // hello darkness my old friend

	/* Constants for spacing offsets between nodes in gridpanes */
	public static final int WINDOW_MAX_WIDTH = 1920;
	public static final int WINDOW_MAX_HEIGHT = 1080;
	public static final int WINDOW_MIN_WIDTH = 720;
	public static final int WINDOW_MIN_HEIGHT = 480;

	/* Series of constants to define panel constraints */
	private static final int PLAYER_INFO_MAX_WIDTH = 650;
	private static final int ITEMS_MAX_IMAGE_SIZE = 55;
	private static final int CHARACTERS_MAX_IMAGE_SIZE = 70;
	private static final int SIDE_PANEL_NODE_HEIGHT = 110;
	private static final int SIDE_PANEL_NODE_WIDTH = 80;
	private static final int CENTRAL_PANEL_NODE_HEIGHT = 130;
	private static final int CENTRAL_PANEL_NODE_WIDTH = 90;

	/* Panel Identifiers for Central Panel */
	private static final String CENTRAL_PANEL_CHARACTERS = "Characters";
	private static final String CENTRAL_PANEL_ITEMS = "Items";

	/* Panel empty messages, to be used if the panel has no items */
	private static final String NO_CHARACTERS_IN_ROOM = "There is no one in the room.";
	private static final String NO_ITEMS_IN_ROOM = "There are no items.";
	private static final String NO_EXITS_IN_ROOM = "There are no exits. We're trapped Bob.";
	private static final String NO_ITEMS_IN_INVENTORY = "You have no items.";
	/* Panel identifiers for other panels */
	private static final String SIDE_PANEL_INVENTORY = "Inventory";
	private static final String SIDE_PANEL_CHARACTERS = "Characters";
	private static final String SIDE_PANEL_EXITS = "Exits";
	private static final String CENTRAL_PANEL_TITLE = "Room Information";

	/**
	 * Get the external CSS to use
	 * @return CSS file to load
	 */
	public static String getExternalCSS() {
		return new File(Main.XML_CONFIGURATION_FILES + File.separator + Main.game.getProperty("css")).toURI()
				.toString();
	}

	@Override
	public void print(Object obj) {
		//System.out.print(obj);
	}

	@Override
	public void println(Object obj) {
		//System.out.println(obj);
	}

	@Override
	public void println() {
		//System.out.println();
	}

	@Override
	public void printErr(Object obj) {
		//System.err.print(obj);
		if(stage != null) // If the stage is ready, show error
			GraphicsUtil.showAlert("Error", "An error occurred somewhere.", obj.toString(), AlertType.ERROR);
	}

	@Override
	public void printlnErr(Object obj) {
		//System.err.println(obj);
		if(stage != null) // If the stage is ready, show error
			GraphicsUtil.showAlert("Error", "An error occurred somewhere.", obj.toString(), AlertType.ERROR);
	}

	// Close stage and logger
	@Override
	public void exit() {
		stage.close();
		Out.close();
	}

	/**
	 * Display as a dialog some information
	 */
	@Override
	public void displayLocale(Object obj) {
		GraphicsUtil.showAlert("Zuul", null, obj.toString(), AlertType.INFORMATION);
	}

	@Override
	public void displaylnLocale(Object obj) {
		//...
	}

	/**
	 * Update the GUI, pass all current resources to
	 * respective components and in turn call their
	 * update methods.
	 */
	@Override
	public boolean update(boolean act) {

		// Let the characters when we move
		if (act)
			game.getCharacterManager().act(game);

		// Update all panels, change anything that may have been
		// used, dropped etc
		exits.update(game.getPlayer().getCurrentRoom().getExits());
		inventory.update(game.getPlayer().getInventory());
		characters.update(game.getPlayer().getCurrentRoom().getNonPlayerCharacters());
		room.update(CENTRAL_PANEL_CHARACTERS, game.getPlayer().getCurrentRoom().getNonPlayerCharacters());
		room.update(CENTRAL_PANEL_ITEMS, game.getPlayer().getCurrentRoom().getItems());

		// Update background, weight and current command details
		setBackgroundImage(game.getPlayer().getCurrentRoom());
		updateWeight();
		updateCommandTranslation();
		return true;
	}

	/**
	 * Set the current command text box, if the length is greater than 15 slice of the end and add "..."
	 */
	private static void updateCommandTranslation() {
		commandTranslation.setText(parameters.length() > 15 ? parameters.substring(0, 15-2) + "..." : parameters);
	}

	/**
	 * Set the current weight for the player to their weight
	 */
	private void updateWeight() {
		playerWeight.setText(getWeightTranslation());
	}

	/**
	 * Launch the interface application
	 */
	@Override
	public void play(Game zuulGame) {
		launch();
	}

	/**
	 * Create the stage and scene, including all respective components
	 * of the scene.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		Out.out.logln(" - - - - - - - - - - - - - - - - - - - - ");
		Out.out.logln("             Creating GUI...             ");
		Out.out.logln(" - - - - - - - - - - - - - - - - - - - - ");

		game = Main.game;
		fontManager = new FontManager(game.getProperty("defaultFont"));
		stage = primaryStage;

		// Set title of the game from what is within our .properties file
		stage.setTitle(game.getProperty("title"));

		// Define the minimum resolution of the root container of the stage
		stage.setMinWidth(WINDOW_MIN_WIDTH);
		stage.setMinHeight(WINDOW_MIN_HEIGHT);

		// Define the maximum resolution of the root container of the stage
		stage.setMaxWidth(WINDOW_MAX_WIDTH);
		stage.setMaxHeight(WINDOW_MAX_HEIGHT);
		
		// Save log file when abruptly closed
		stage.setOnCloseRequest(e->{
			Out.close();
		});

		// Create resource manager, needed to handle resources such as images
		ResourceManager.newResourceManager();

		playerToolbar = getPlayerInformationNode();

		/* Create root pane, a border pane */
		root = new BorderPane();

		Out.out.logln();
		Out.out.logln("	Creating panels...");

		// Current room items and current room npcs
		Stream<Resource> centralResources = Stream.concat(game.getPlayer().getCurrentRoom().getItems().stream(),
				game.getPlayer().getCurrentRoom().getNonPlayerCharacters().stream());

		inventory = new SidePanel(
				SIDE_PANEL_INVENTORY, NO_ITEMS_IN_INVENTORY,
				game.getPlayer().getInventory().stream(),
				fontManager, game, "sidepanel-inventory", 
				ITEMS_MAX_IMAGE_SIZE, ITEMS_MAX_IMAGE_SIZE,
				SIDE_PANEL_NODE_WIDTH, SIDE_PANEL_NODE_HEIGHT);
		
		characters = new SidePanel(SIDE_PANEL_CHARACTERS, NO_CHARACTERS_IN_ROOM,
				game.getPlayer().getCurrentRoom().getNonPlayerCharacters().stream(),
				fontManager, game,
				"sidepanel-characters",
				CHARACTERS_MAX_IMAGE_SIZE, CHARACTERS_MAX_IMAGE_SIZE, 
				CENTRAL_PANEL_NODE_WIDTH,CENTRAL_PANEL_NODE_HEIGHT);
		
		
		exits = new SidePanel(SIDE_PANEL_EXITS, NO_EXITS_IN_ROOM, 
				game.getPlayer().getCurrentRoom().getExits().stream(),
				fontManager, game, "sidepanel-exits", 
				CHARACTERS_MAX_IMAGE_SIZE, CHARACTERS_MAX_IMAGE_SIZE,
				SIDE_PANEL_NODE_WIDTH+35, SIDE_PANEL_NODE_HEIGHT+20, 500, 500);
		
		((ScrollPane) exits.getNode()).setFitToWidth(true);	

		room = new CentralPanel(CENTRAL_PANEL_TITLE, centralResources, fontManager, game, "sidepanel-room");
		
		room.setVisible(false);
		
		room.addPanel(CENTRAL_PANEL_CHARACTERS, NO_CHARACTERS_IN_ROOM,
				game.getPlayer().getCurrentRoom().getNonPlayerCharacters().stream(), 
				CHARACTERS_MAX_IMAGE_SIZE,CHARACTERS_MAX_IMAGE_SIZE, 
				CENTRAL_PANEL_NODE_WIDTH, CENTRAL_PANEL_NODE_HEIGHT);
		
		room.addPanel(CENTRAL_PANEL_ITEMS, NO_ITEMS_IN_ROOM, 
				game.getPlayer().getCurrentRoom().getItems().stream(),
				ITEMS_MAX_IMAGE_SIZE, ITEMS_MAX_IMAGE_SIZE, 
				CENTRAL_PANEL_NODE_WIDTH, CENTRAL_PANEL_NODE_HEIGHT);
		
		scene = new Scene(root, 1280, 720);

		// Setup styling
		scene.getStylesheets().add(getExternalCSS());

		// Set current room background
		setBackgroundImage(game.getPlayer().getCurrentRoom());

		stage.setScene(scene);

		stage.show();
		
		// Set all components to the root pane
		root.setCenter(playerToolbar);
		playerToolbar.toFront();
		root.setTop(exits.getNode());
		root.setRight(room.getNode());
		root.setLeft(inventory.getNode());
		
		updateAnimation();
		
		// Add listeners to update the animation points based on
		// current window size
		scene.heightProperty().addListener(s->updateAnimation());
		scene.widthProperty().addListener(s->updateAnimation());
		
		// Play default song
		AudioManager.am.playSong("main.mp3");
		
		Out.out.logln(" - - - - - - - - - - - - - - - - - - - - ");
		Out.out.logln("              Finished GUI.              ");
		Out.out.logln(" BorderPane w: " + root.getWidth() + ", h: " +root.getHeight());
		Out.out.logln(" - - - - - - - - - - - - - - - - - - - - ");
		
	}
	
	/**
	 * Update the animation positions for all panes
	 */
	private void updateAnimation(){
		Out.out.logln("Updating...");
		inventory.setSlideAnimation(root, SlideAnimation.LEFT, 200);
		characters.setSlideAnimation(root, SlideAnimation.RIGHT, 500);
		exits.setSlideAnimation(root, SlideAnimation.TOP, 500);
		room.setSlideAnimation(room.getNode(), root, SlideAnimation.RIGHT, 500);
	}

	/**
	 * Create and return the Player Toolbar, this toolbar contains
	 * the players weight, audio levels, description, name and commands
	 * @return
	 */
	private StackPane getPlayerInformationNode() {

		// Wrapper for entire toolbar
		StackPane root = new StackPane();

		// Add a blank rectangle to the backdrop
		root.getChildren().add(GraphicsUtil.createNewRectangle("player-information-root", PLAYER_INFO_MAX_WIDTH, 150));
		root.setAlignment(Pos.BOTTOM_CENTER);

		{

			// Vertical box to split commands and player information
			VBox vbox = new VBox();

			vbox.setMaxWidth(PLAYER_INFO_MAX_WIDTH);
			vbox.setAlignment(Pos.BOTTOM_CENTER);
			
			// Backdrop for player information
			StackPane stack = new StackPane();

			stack.getChildren()
					.add(GraphicsUtil.createNewRectangle("player-information-detail-root", PLAYER_INFO_MAX_WIDTH, 120));

			{
				// Gridpane for player information
				GridPane gp = new GridPane();

				gp.setHgap(10);
				gp.setVgap(10);
				gp.setPadding(new Insets(0, 5, 0, 5));

				gp.setAlignment(Pos.CENTER);

				{
					// set the player image, overlay ontop of a rectangle
					StackPane sp = new StackPane();
					sp.getChildren().add(GraphicsUtil.createNewRectangle("player-information-image", 110, 110));
					sp.getChildren().add(GraphicsUtil.createIconImage(game.getPlayer().getImage(), 100, 100));

					gp.add(sp, 0, 0);
				}

				// New gridpane to handle player name and information
				GridPane innergp = new GridPane();

				innergp.add(GraphicsUtil.createNewText(game.getPlayer().getName(), "player-information-name"), 1, 0);
				if (game.getPlayer().getDescription() != null) {
					innergp.add(
							GraphicsUtil.createNewText(game.getPlayer().getDescription(), "player-information-filler"),
							1, 1);
				}

				{
					
					// HBox to split current command, weight and audio levels
					HBox hbox = new HBox();
					
					StackPane weightPane = new StackPane();

					weightPane.getChildren().add(GraphicsUtil.createNewRectangle("player-information-weight", 200, 25));

					playerWeight = GraphicsUtil.createNewText(getWeightTranslation(), "player-information-weight-text");

					// add the player weight
					weightPane.getChildren().add(playerWeight);
					weightPane.setPadding(new Insets(5, 5, 5, 5));
					weightPane.setAlignment(Pos.CENTER);
					innergp.setAlignment(Pos.CENTER);

					hbox.getChildren().add(weightPane);
					
					weightPane = new StackPane();

					weightPane.getChildren().add(GraphicsUtil.createNewRectangle("player-information-current-command", 200, 25));

					commandTranslation = GraphicsUtil.createNewText(parameters, "player-information-weight-text");

					// add the command translation
					weightPane.getChildren().add(commandTranslation);
					weightPane.setPadding(new Insets(5, 5, 5, 5));
					weightPane.setAlignment(Pos.CENTER);
					
					hbox.getChildren().add(weightPane);
					
					weightPane = new StackPane();
					weightPane.setAlignment(Pos.CENTER);
					
					VBox sliderGP = new VBox();
					
					// create slider for audio levels
					sliderGP.getChildren().add(GraphicsUtil.createNewText("Audio", "player-information-weight-text"));
					sliderGP.setPadding(new Insets(5, 5, 5, 5));
					sliderGP.setAlignment(Pos.CENTER);
					
					Slider slider = new Slider(0,0.5,0.3);
			        slider.valueProperty().addListener(new ChangeListener<Number>() {
			        	// onchange event for slider, update AudioManager
			            public void changed(ObservableValue<? extends Number> ov,
			                Number old_val, Number new_val) {
			                    AudioManager.am.setVolume(new_val.doubleValue());
			            }
			        });
			        
			        /* Add everything to their respective wrappers*/
			        sliderGP.getChildren().add(slider);
			        
			        weightPane.getChildren().add(sliderGP);
					
			        hbox.getChildren().add(weightPane);
			        
					innergp.add(hbox, 1, 3);

					gp.add(innergp, 1, 0);

				}
		        /* Add everything to their respective wrappers*/
				stack.getChildren().add(gp);

				vbox.getChildren().add(stack);
				vbox.getChildren().add(getCommandHBox());

				root.getChildren().add(vbox);

			}

		}

		return root;
	}

	/**
	 * Get the command box, splits all commands along a HBox
	 * @return
	 */
	private HBox getCommandHBox() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(5, 5, 7, 5));
		hbox.setSpacing(10);

		Button buttonCurrent = null;

		for (Command command : game.getCommandManager().commands()) {

			if (!command.interfaceAcceptable(this)) {
				continue;
			}

			// Create commands and set their events
			buttonCurrent = newCommandButton(command.getRawName(), command, "command-button");
			buttonCurrent.setPrefSize(100, 20);
			// Add tooltip to help explain command
			Tooltip.install(buttonCurrent, GraphicsUtil.createNewToolTip(command.getDescription(), FontManager.getFontManager().getFont("DEFAULT_FONT"), 1000));
			hbox.getChildren().add(buttonCurrent);

		}

		return hbox;
	}

	/**
	 * Return the weight translation
	 * @return weight translation
	 */
	private String getWeightTranslation() {
		return "Weight > " + game.getPlayer().getWeight() + " / " + game.getPlayer().getMaxWeight();
	}

	/**
	 * Create a command event, such as MouseClick, which when triggered
	 * will execute the command.
	 * @param params Parameters for this command
	 * @param command Command to execute
	 * @return The eventhandler
	 */
	public static EventHandler<Event> getCommandEvent(String params, Command command) {
		return (Event e) -> {
			
			parameters += params;
			if (executeCommand(command, e)) {
				parameters = "";
			}
			updateCommandTranslation();
		};
	}

	/**
	 * Specific MouseEvent handler for variable parameters of the current command
	 * @param params The current command, and or more parameters
	 * @return Eventhandler
	 */
	public static EventHandler<? super MouseEvent> getVariableCommandEvent(String params) {
		
		return (Event e) -> {
			Command command = Main.game.getCommandManager().getCommand(parameters);			
			
			if(command == null){
				if(parameters.split(" ").length > 0){
					command = Main.game.getCommandManager().getCommand(parameters.split(" ")[0]);
					if(command == null)
						return;
				}else{
					return;
				}
			}
			
			parameters += " " + params;		

			if (executeCommand(command, e)) {
				parameters = command.getName();
			}
			
			updateCommandTranslation();

		};
	}

	/**
	 * Create new command button, that will execute a command when pressed with
	 * the given parameters and apply the css to it
	 * @param params Params
	 * @param command Command
	 * @param css StyleSheet
	 * @return CommandButton
	 */
	public static Button newCommandButton(String params, Command command, String css) {
		Button button = new Button(command.getName());
		button.getStyleClass().add(css); // node
		button.setPrefSize(50, 20);
		// button.setAlignment(Pos.CENTER);
		button.setOnAction(e -> {
			parameters = params;
			if (executeCommand(command, e)) {
				parameters = "";
			}
		});
		return button;
	}

	/**
	 * Set the CSS background-image for the root pane based on the current room
	 * @param image
	 */
	private void setBackgroundImage(Resource image) {

		root.setStyle(
				"   -fx-background-image: url(" + image.getImageFileURL() + ");" + " -fx-background-size: cover;");

	}

	/**
	 * Execute a command, update command translation and return whether it was
	 * successfull
	 * @param cmd The command to execute
	 * @param event The event that called this
	 * @return true if successfull
	 */
	public static boolean executeCommand(Command cmd, Event event) {

		Out.out.logln(event.getEventType().getName() + " > [" + cmd.getName() + "] >> " + parameters);

		CommandExecution ce = new CommandExecution(parameters);

		updateCommandTranslation();
		
		return cmd.action(game, ce);

	}

	/**
	 * Show the players inventory
	 */
	@Override
	public void showInventory(boolean override) {

		if(inventory.isHidden()){
			hideOtherPanes();
			inventory.show();
		}else if(override){
			hideOtherPanes();
			inventory.hide();
		}
	}

	/**
	 * Show the characters in the room
	 */
	@Override
	public void showCharacters(boolean override) {

		if(characters.isHidden()){
			
			root.setRight(characters.getNode());
			
			characters.update(game.getPlayer().getCurrentRoom().getNonPlayerCharacters());
			
			characters.show();
		}else if(override){
			//hideOtherPanes();
			characters.hide();
		}
	}

	/**
	 * Show the details of the room, such as characters or items
	 */
	@Override
	public void showRoom(boolean override) {

		if(room.isHidden()){
			hideOtherPanes();
			
			root.setRight(room.getNode());
			
			room.show();
		}else if(override){
			room.hide();
		}

	}

	/**
	 * Show the exits in the room
	 */
	@Override
	public void showExits(boolean override) {

		if(exits.isHidden()){
			hideOtherPanes();
			exits.show();
		}else{
			exits.hide();
		}
	}

	/**
	 * Hide all other visible panes
	 */
	private void hideOtherPanes(){
		inventory.hide();
		characters.hide();
		exits.hide();
		room.hide();
		//
	}

	/**
	 * Get the current parameters
	 */
	@Override
	public String getCurrentParameters() {
		return parameters;
	}
	
	/**
	 * Reset the parameters to either empty or the command
	 * they are building off of
	 */
	public void resetParameters(){		
		parameters = parameters.split(" ")[0];
	}

	/**
	 * Get the help description
	 */
	@Override
	public String getHelpDescription() {
		return game.getProperty("guiHelpDescription");
	}
}
