package com.dlsc.jfxcentral2.mobile.componenets;

import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.MobileRouter;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.scenicview.ScenicView;

public class MobileDevelopToolBar extends VBox {

    private final TextField pathField;

    private enum WidthType {
        LARGE(1440),
        MEDIUM(768),
        SMALL(375);

        private final int width;

        WidthType(int width) {
            this.width = width;
        }

        public int getWidth() {
            return width;
        }
    }

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

        HBox row1 = new HBox(8, pathField, loadTimeLabel, countLabel, visibleCountLabel);
        HBox.setHgrow(pathField, Priority.ALWAYS);

        Label widthLabel = new Label();
        Label heightLabel = new Label();
        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                widthLabel.textProperty().bind(newScene.widthProperty().asString("Width: %.0f"));
                heightLabel.textProperty().bind(newScene.heightProperty().asString("Height: %.0f"));
                newScene.focusOwnerProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        System.out.println("Focused component: " + newValue.getClass().getSimpleName());
                    }
                });
            } else {
                widthLabel.textProperty().unbind();
                heightLabel.textProperty().unbind();
                widthLabel.setText("");
                heightLabel.setText("");
            }
        });

        Label limitWidthLabel = new Label("Limit Width: ");
        ComboBox<WidthType> sizeComboBox = new ComboBox<>(FXCollections.observableArrayList(WidthType.values()));
        sizeComboBox.setValue(WidthType.SMALL);
        HBox sizeBox = new HBox(limitWidthLabel, sizeComboBox);
        sizeBox.setAlignment(Pos.CENTER_LEFT);

        sizeComboBox.valueProperty().addListener((obs, oldSize, newSize) -> {
            Scene scene = getScene();
            if (scene != null) {
                scene.getWindow().setWidth(newSize.getWidth());
                scene.getWindow().centerOnScreen();
            }
        });

        Button scenicViewButton = new Button("Scenic");
        scenicViewButton.setOnAction(evt -> {
            Scene scene = getScene();
            if (scene != null) {
                ScenicView.show(scene);
            }
        });

        HBox row2 = new HBox(5, widthLabel, heightLabel, sizeBox, scenicViewButton);
        row2.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(row1, row2);
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
