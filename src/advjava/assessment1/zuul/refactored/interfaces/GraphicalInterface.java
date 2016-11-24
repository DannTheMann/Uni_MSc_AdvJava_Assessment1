package advjava.assessment1.zuul.refactored.interfaces;

import java.util.ArrayList;
import java.util.List;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Main;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GraphicalInterface extends Application implements UserInterface{

	private Game game;
	private FontManager fontManager;
	private List<Button> commandButtons;
	
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
	public CommandExecution getCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean update(Game game) {
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
        primaryStage.setTitle(game.getProperty("title"));
        
        BorderPane border = new BorderPane();
        
        border.setBottom(getCommandHBox());
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(border, 800, 600);
        primaryStage.setScene(scene);
        
//        Text scenetitle = new Text("Welcome");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);
//
//        Label userName = new Label("User Name:");
//        grid.add(userName, 0, 1);
//
//        TextField userTextField = new TextField();
//        grid.add(userTextField, 1, 1);
//
//        Label pw = new Label("Password:");
//        grid.add(pw, 0, 2);
//
//        PasswordField pwBox = new PasswordField();
//        grid.add(pwBox, 1, 2);
        
        primaryStage.show();
		
	}
	
	private Node getCommandHBox() {
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #336699;");

	    commandButtons = new ArrayList<Button>(game.getCommandManager().commands().size());
	    
	    Button buttonCurrent = null;
	    
	    for(Command command : game.getCommandManager().commands()){
	    	
                if(!command.interfaceAcceptable(this)){
                    continue;
                }
                
	    	buttonCurrent = new Button(command.getName());
		    buttonCurrent.setPrefSize(100, 20);
		    commandButtons.add(buttonCurrent);
		    buttonCurrent.setOnAction((event)->{
		    	executeCommand(command);
		    });
		    hbox.getChildren().add(buttonCurrent);
	    	
	    }


	    return hbox;
	}
	
	private String parameters = "hello darkness my old friend";
	
	private void executeCommand(Command cmd){
		
		CommandExecution ce = new CommandExecution(parameters);
		
		cmd.action(game, ce);
		
	}
}
