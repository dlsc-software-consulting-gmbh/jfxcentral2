package com.dlsc.jfxcentral2.utils;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class NodeUtil {
    private NodeUtil() {
    }

    public static VBox createVBox(Node... nodes) {
        VBox box = new VBox();
        setAll(box, nodes);
        return box;
    }

    public static HBox createHBox(Node... nodes) {
        HBox box = new HBox();
        setAll(box, nodes);
        return box;
    }

    /**
     * If the node is not empty, add it to the pane
     */
    public static void setAll(Pane pane, Node... nodes) {
        pane.getChildren().clear();
        for (Node node : nodes) {
            if (node != null) {
                pane.getChildren().add(node);
            }
        }
    }

    public static void setHBoxMargin(Node node, Insets insets) {
        if (node != null) {
            HBox.setMargin(node, insets);
        }
    }

    public static void setVBoxMargin(Node node, Insets insets) {
        if (node != null) {
            VBox.setMargin(node, insets);
        }
    }


        public static Label createLabel(String... styleClass) {
        Label label = new Label();
        label.getStyleClass().addAll(styleClass);
        return label;
    }

    public static Label createLabel(String text, Node graphic, String... styleClass) {
        Label label = createLabel(styleClass);
        label.setText(text);
        label.setGraphic(graphic);
        return label;
    }

    public static Label createLabel(String text, Ikon ikon, String... styleClass) {
        Label label = createLabel(styleClass);
        label.setText(text);
        FontIcon fontIcon = new FontIcon();
        fontIcon.setIconCode(ikon);
        label.setGraphic(fontIcon);
        return label;
    }

}
