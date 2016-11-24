package advjava.assessment1.zuul.refactored.interfaces;

import java.util.ArrayList;
import java.util.List;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.Main;
import advjava.assessment1.zuul.refactored.cmds.Command;
import advjava.assessment1.zuul.refactored.cmds.CommandExecution;
import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.ResourceManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GraphicalInterface extends Application implements UserInterface {

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
        ResourceManager.newResourceManager();

        primaryStage.setTitle(game.getProperty("title"));

        Node hbox = getCommandHBox();
        ImageView iv = new ImageView(ResourceManager.getResourceManager().getImage("outside"));
        iv.fitWidthProperty().bind(primaryStage.widthProperty());
        iv.fitHeightProperty().bind(primaryStage.heightProperty());

        BorderPane border = new BorderPane();
        border.setStyle(""
                + "-fx-background-image: url(outside.jpg);"
                + "-fx-background-size: cover;");
        border.setBottom(hbox);
        //sp.getChildren().add(hbox);

//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.BASELINE_LEFT);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
        Button btn = new Button();
        btn.setText("Open Dialog");
        btn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("This is a Dialog"));
                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
    
        border.setTop(btn);
    Scene scene = new Scene(border, 1280, 720);

    primaryStage.setScene (scene);
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

    primaryStage.show ();

}

private Node getCommandHBox() {
        HBox hbox = new HBox();
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

        Out.out.logln(event.getEventType().getName());

        CommandExecution ce = new CommandExecution(parameters);

        cmd.action(game, ce);

    }

    @Override
    public void showInventory() {

    }

    @Override
    public void showCharacters() {

    }

    @Override
    public void showRoom() {

    }
}
