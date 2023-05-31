package com.dlsc.jfxcentral2.components;

import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class TopPane extends PaneBase {

    private TopMenuBar topMenuBar;
    private Region content;

    public TopPane(TopMenuBar topMenuBar, Region content) {
        this.topMenuBar = Objects.requireNonNull(topMenuBar);
        this.content = Objects.requireNonNull(content);

        getStyleClass().add("top-pane");
        setMinWidth(0);

        this.topMenuBar.setMaxHeight(Region.USE_PREF_SIZE);
        this.topMenuBar.modeProperty().addListener(it -> updateView());
        this.content.setMaxWidth(Double.MAX_VALUE);

        // constraints
        StackPane.setAlignment(topMenuBar, Pos.TOP_CENTER);
        VBox.setVgrow(topMenuBar, Priority.NEVER);
        VBox.setVgrow(content, Priority.ALWAYS);

        updateView();
    }

    private void updateView() {
        if (topMenuBar.getMode().equals(TopMenuBar.Mode.DARK)) {
            getChildren().setAll(content, topMenuBar);
        } else {
            VBox box = new VBox(topMenuBar, content);
            StackPane.setAlignment(box, Pos.TOP_CENTER);
            box.getStyleClass().add("vbox");
            box.setMaxWidth(Double.MAX_VALUE);
            box.setMinWidth(0);
            getChildren().setAll(box);
        }
    }
}
