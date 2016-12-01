/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.interfaces.graphical;

import advjava.assessment1.zuul.refactored.Game;
import advjava.assessment1.zuul.refactored.interfaces.FontManager;
import advjava.assessment1.zuul.refactored.interfaces.GraphicalInterface;
import advjava.assessment1.zuul.refactored.item.Item;
import advjava.assessment1.zuul.refactored.room.Room;
import advjava.assessment1.zuul.refactored.utils.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author dja33
 */
public class CentralPanel {

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

    private HBox root;
    private FontManager fm;
    private Game game;
    private Map<String, Collection<GridPane>> panels;

    public CentralPanel(String title, FontManager fm, Game game, String cssStyling) {
        this.panels = new TreeMap<>();
        this.game = game;
        this.fm = fm;

        root = new HBox();
        SplitPane.setResizableWithParent(root, Boolean.FALSE);
//            
//
//		root = sp;

    }

    public boolean addPanel(String panelName, Stream<Resource> stream) {

        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        TilePane tileHolder = new TilePane();
        tileHolder.setAlignment(Pos.BASELINE_CENTER);
        tileHolder.setMinSize(GraphicalInterface.WINDOW_MIN_WIDTH+100, GraphicalInterface.WINDOW_MIN_HEIGHT);
        tileHolder.setMaxSize(GraphicalInterface.WINDOW_MAX_WIDTH+100, GraphicalInterface.WINDOW_MAX_HEIGHT);
        tileHolder.setHgap(NODE_HORIZONTAL_INSET);
        tileHolder.setVgap(NODE_VERTICAL_INSET);
        // Insets, in order of
        // top, right, bottom, left
        tileHolder.setPadding(new Insets(NODE_TOP_OFFSET, NODE_RIGHT_OFFSET, NODE_BOTTOM_OFFSET, NODE_LEFT_OFFSET));
        tileHolder.setPrefRows(8);
        //tileHolder.setPrefHeight(100);

        panels.put(panelName, new ArrayList<>());
        
        stream.forEach(i -> tileHolder.getChildren().add(getDisplayItem(i, panelName)));

        sp.setContent(tileHolder);

        root.getChildren().add(sp);

        return true;
    }

    private Node getDisplayItem(Resource resource, String panelName) {
        
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

        grid.add(text, 1, 0);
        grid.add(iv, 0, 0);

        if (resource instanceof Item) {
            Item item = (Item) resource;
            text.setText(text.getText() + System.lineSeparator() + "Weight: " + item.getWeight());
            text.setFont(fm.getFont("SansSerif"));

            // Create drop button
            Button button = GraphicalInterface.newCommandButton("take " + resource.getName(),
                    game.getCommandManager().getCommand("Take"), css);
            button.setPrefSize(50, 20);

            grid.add(button, 0, 1);
        }

        if (resource instanceof advjava.assessment1.zuul.refactored.character.Character) {

            advjava.assessment1.zuul.refactored.character.Character c = (advjava.assessment1.zuul.refactored.character.Character) resource;
            // ...
        }

        if (resource.getDescription() != null) {
            Tooltip tp = new Tooltip(
                    resource.getName() + System.lineSeparator() + System.lineSeparator() + resource.getDescription());
            tp.setContentDisplay(ContentDisplay.BOTTOM);
            tp.setFont(fm.getFont("Yu Gothic"));
            tp.setOpacity(.85);
            GraphicalInterface.modifyTooltipTimer(tp, 25);
            Tooltip.install(grid, tp);
        }

        panels.get(panelName).add(grid);

        return grid;
    }

    public Node getNode() {
        return root;
    }

    public void update(Collection<Resource> newContents) {

//        // Doesn't work right
//        panels.entrySet().stream()
//                .forEach(s -> s.getValue().stream()
//                        .forEach(gp
//                                -> gp.getChildren().removeIf(item -> {
//                                    return !newContents.stream()
//                                            .filter(i -> i.equals(item))
//                                            .findAny()
//                                            .isPresent();
//                                })));
//
//        // Works fine?
//        newContents.stream()
//                .filter(item -> {
//                    return !grids.stream()
//                            .filter(i -> i.equals(item))
//                            .findFirst()
//                            .isPresent();
//                })
//                .forEach(i -> tileHolder.getChildren().add(getDisplayItem(i)));

    }

}
