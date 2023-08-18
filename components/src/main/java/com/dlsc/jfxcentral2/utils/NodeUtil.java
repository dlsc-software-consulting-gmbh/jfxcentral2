package com.dlsc.jfxcentral2.utils;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

    public static void scrollToNode(Node node) {
        ScrollPane pane = findScrollPane(node);
        if (pane != null) {
            Node content = pane.getContent();
            double width = content.getBoundsInLocal().getWidth();
            double height = content.getBoundsInLocal().getHeight();

            Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
            Bounds bounds = content.sceneToLocal(boundsInScene);

            double x = bounds.getMinX();
            double y = bounds.getMinY();

            // scrolling values range from 0 to 1
            pane.setVvalue(y/height);
            pane.setHvalue(x/width);

            // just for usability
            node.requestFocus();
        }
    }

    public static void scrollToNode(Node node, long delayMillis) {
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        Thread.sleep(delayMillis);
                        Platform.runLater(() -> scrollToNode(node));
                        return null;
                    }
                };
            }
        }.start();
    }

    public static ScrollPane findScrollPane(Node node) {
        Parent parent = node.getParent();
        do {
            if (parent instanceof ScrollPane) {
                return (ScrollPane) parent;
            }
            parent = parent.getParent();
        } while (parent != null);

        return null;
    }
}
