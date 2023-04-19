package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.components.MenuView;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class MenuViewSkin extends ControlBaseSkin<MenuView> {

    private Pane box;
    private final MenuView control;

    public MenuViewSkin(MenuView control) {
        super(control);
        this.control = control;
        layoutByOrientation();

        control.orientationProperty().addListener((ob, ov, nv) -> layoutByOrientation());
        control.itemsProperty().addListener((ob, ov, nv) -> layoutByOrientation());

    }

    private void layoutByOrientation() {
        ObservableList<MenuView.Item> items = control.getItems();
        if (box != null) {
            box.getChildren().clear();
        }
        box = (control.getOrientation() == Orientation.VERTICAL) ? new VBox() : new HBox();
        box.getStyleClass().add("content");
        if (items != null) {
            ToggleGroup group = new ToggleGroup();
            for (int i = 0; i < items.size(); i++) {
                MenuView.Item item = items.get(i);
                CustomToggleButton button = new CustomToggleButton(item.name());
                button.setToggleGroup(group);
                button.setFocusTraversable(false);
                button.getStyleClass().add("item");
                button.setUserData(item);
                if (item.ikon() != null) {
                    button.setGraphic(new FontIcon(item.ikon()));
                }
                box.getChildren().add(button);
                if (i == 0) {
                    button.setSelected(true);
                    System.out.println("item url = " + item.url());
                }
            }
        } else {
            Pane node = (Pane) getChildren().get(0);
            node.getChildren().clear();
        }
        for (int i = 0; i < box.getChildren().size(); i++) {
            int tempIndex = i;
            CustomToggleButton button = (CustomToggleButton) box.getChildren().get(i);
            button.setOnAction(event -> {
                MenuView.Item item = control.getItems().get(tempIndex);
                System.out.println("item url = " + item.url());
            });
        }
        getChildren().setAll(box);

    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return box.prefWidth(-1) + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return box.prefHeight(-1) + topInset + bottomInset;
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }
}
