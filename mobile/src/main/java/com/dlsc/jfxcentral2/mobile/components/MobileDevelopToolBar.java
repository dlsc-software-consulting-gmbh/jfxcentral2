package com.dlsc.jfxcentral2.mobile.components;

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
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.scenicview.ScenicView;

public class MobileDevelopToolBar extends VBox {

    private final TextField pathField;

    public enum DeviceType {
        IPHONE_6_7_8("iPhone 6/7/8", 375, 667),
        IPHONE_6_7_8_PLUS("iPhone 6/7/8 Plus", 414, 736),
        IPHONE_XR("iPhone XR", 414, 896),
        IPHONE_X("iPhone X", 375, 812),
        IPHONE_11("iPhone 11", 414, 896),
        IPHONE_12_MINI("iPhone 12 Mini", 360, 780),
        IPHONE_12_12_PRO("iPhone 12/12 Pro", 390, 844),
        IPHONE_12_PRO_MAX("iPhone 12 Pro Max", 428, 926),
        IPHONE_13_MINI("iPhone 13 Mini", 360, 780),
        IPHONE_13_13_PRO("iPhone 13/13 Pro", 390, 844),
        IPHONE_13_PRO_MAX("iPhone 13 Pro Max", 428, 926),
        IPHONE_14("iPhone 14", 390, 844),
        IPHONE_14_PLUS("iPhone 14 Plus", 428, 926),
        IPHONE_14_PRO("iPhone 14 Pro", 393, 852),
        IPHONE_14_PRO_MAX("iPhone 14 Pro Max", 430, 932),
        IPHONE_15_15_PRO("iPhone 15/15 Pro", 393, 852),
        IPHONE_15_PLUS_15_PRO_MAX("iPhone 15 Plus/15 Pro Max", 430, 932);

        private final String desc;
        private final int width;
        private final int height;

        DeviceType(String desc, int width, int height) {
            this.desc = desc;
            this.width = width;
            this.height = height;
        }

        public String getDesc() {
            return desc;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public MobileDevelopToolBar(MobileRouter router) {
        getStyleClass().add("mobile-develop-tool-bar");
        setStyle("-fx-font-size: 12px");

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

        HBox row1 = new HBox(5, pathField, loadTimeLabel, countLabel, visibleCountLabel);
        HBox.setHgrow(pathField, Priority.ALWAYS);

        Label sizeLabel = new Label();
        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                sizeLabel.textProperty().bind(newScene.widthProperty().asString("%.0f")
                        .concat(" x ").concat(newScene.heightProperty().asString("%.0f")));
                newScene.focusOwnerProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        System.out.println("Focused component: " + newValue.getClass().getSimpleName());
                    }
                });
            } else {
                sizeLabel.textProperty().unbind();
                sizeLabel.setText("");
            }
        });

        Label limitWidthLabel = new Label("Device: ");
        ComboBox<DeviceType> sizeComboBox = new ComboBox<>(FXCollections.observableArrayList(DeviceType.values()));
        sizeComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(DeviceType object) {
                return object.getDesc();
            }

            @Override
            public DeviceType fromString(String string) {
                return null;
            }
        });
        sizeComboBox.setValue(DeviceType.IPHONE_12_12_PRO);
        HBox sizeBox = new HBox(limitWidthLabel, sizeComboBox);
        sizeBox.setAlignment(Pos.CENTER_LEFT);

        sizeComboBox.valueProperty().addListener((obs, oldSize, newSize) -> {
            Scene scene = getScene();
            if (scene != null) {
                scene.getWindow().setWidth(newSize.getWidth());
                scene.getWindow().setHeight(newSize.getHeight());
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

        HBox row2 = new HBox(5, sizeLabel, sizeBox, scenicViewButton);
        row2.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(row1, row2);
        setSpacing(5);
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
