package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.components.MenuView;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class MenuViewSkin extends ControlBaseSkin<MenuView> {

    private Pane box;
    private final MenuView control;
    private ToggleGroup group;

    public MenuViewSkin(MenuView control) {
        super(control);
        this.control = control;
        layoutByOrientation();

        control.orientationProperty().addListener((ob, ov, nv) -> layoutByOrientation());
        control.itemsProperty().addListener((ob, ov, nv) -> layoutByOrientation());
        control.selectedIndexProperty().addListener((ob, ov, nv) -> {
            if (nv.intValue() >= 0) {
                CustomToggleButton button = (CustomToggleButton) box.getChildren().get(nv.intValue());
                button.setSelected(true);
                button.fire();
            }else {
                group.selectToggle(null);
            }
        });
    }

    private void layoutByOrientation() {
        ObservableList<MenuView.Item> items = control.getItems();
        if (box != null) {
            box.getChildren().clear();
        }
        boolean isVertical = control.getOrientation() == Orientation.VERTICAL;
        box = isVertical ? new VBox() : new HBox();
        box.getStyleClass().add("content");
        if (items != null) {
            group = new ToggleGroup();
            for (int i = 0; i < items.size(); i++) {
                MenuView.Item item = items.get(i);
                CustomToggleButton button = new CustomToggleButton(item.name());
                button.setToggleGroup(group);
                button.setFocusTraversable(false);
                button.getStyleClass().add("item");
                button.setUserData(item);
                if (isVertical) {
                    button.setMaxWidth(Double.MAX_VALUE);
                }
                if (item.ikon() != null) {
                    button.setGraphic(new FontIcon(item.ikon()));
                }
                box.getChildren().add(button);
                if (i == control.getSelectedIndex()) {
                    button.setSelected(true);
                }
                if (item.url() != null) {
                    LinkUtil.setLink(button, item.url());
                }else if (item.action() != null){
                    button.setOnAction(event -> item.action().run());
                }
            }
        } else {
            Pane node = (Pane) getChildren().get(0);
            node.getChildren().clear();
        }
        getChildren().setAll(box);

    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
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
        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }
}
