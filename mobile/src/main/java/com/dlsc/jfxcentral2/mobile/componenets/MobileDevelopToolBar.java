package com.dlsc.jfxcentral2.mobile.componenets;

import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.MobileRouter;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class MobileDevelopToolBar extends HBox {

    private final TextField pathField;

    public MobileDevelopToolBar(MobileRouter router) {
        getStyleClass().add("mobile-develop-tool-bar");

        pathField = new TextField(PagePath.HOME);
        pathField.setOnAction(evt -> {
            String path = pathField.getText().trim().toLowerCase();
            MobileLinkUtil.getToPage(path);
        });

        router.currentPathProperty().addListener((obs, oldPath, newPath) -> {
            if (!pathField.getText().equals(newPath)) {
                pathField.setText(newPath);
            }
        });

        Label loadTimeLabel = new Label();
        loadTimeLabel.setGraphic(new FontIcon(MaterialDesign.MDI_TIMER));
        loadTimeLabel.textProperty().bind(router.loadTimeProperty().asString().concat(" ms"));

        Label countLabel = new Label();
        Label visibleCountLabel = new Label();

        HBox.setHgrow(pathField, Priority.ALWAYS);
        getChildren().addAll(pathField, loadTimeLabel, countLabel, visibleCountLabel);
        setSpacing(8);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(3));

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    if (getScene() != null) {
                        NodeCountResult nodeCountResult = countNodes(getScene().getRoot());
                        countLabel.setText("Nodes: " + nodeCountResult.totalNodes);
                        visibleCountLabel.setText("Visible: " + nodeCountResult.visibleNodes);
                    }
                });
            }
        }).start();
    }

    private NodeCountResult countNodes(Node node) {
        NodeCountResult result = new NodeCountResult();
        countNodesRecursive(node, result);
        return result;
    }

    private void countNodesRecursive(Node node, NodeCountResult result) {
        result.totalNodes++;
        if (node.isVisible()) {
            result.visibleNodes++;
        }

        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                countNodesRecursive(child, result);
            }
        }
    }

    private static class NodeCountResult {
        int totalNodes;
        int visibleNodes;
    }

}
