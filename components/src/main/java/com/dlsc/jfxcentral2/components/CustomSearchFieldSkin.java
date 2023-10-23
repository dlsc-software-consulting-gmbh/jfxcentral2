package com.dlsc.jfxcentral2.components;

import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;

public class CustomSearchFieldSkin extends SkinBase<CustomSearchField> {
    private final HBox container;

    public CustomSearchFieldSkin(CustomSearchField textField) {
        super(textField);

        FontIcon leftFontIcon = new FontIcon();
        leftFontIcon.getStyleClass().add("left-font-icon");
        leftFontIcon.iconCodeProperty().bind(textField.leftIconProperty());

        StackPane leftPane = new StackPane(leftFontIcon);
        leftPane.getStyleClass().add("left-wrapper");
        leftPane.managedProperty().bind(leftPane.visibleProperty());
        leftPane.visibleProperty().bind(textField.leftIconProperty().isNotNull());
        leftPane.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (!e.isPrimaryButtonDown()) {
                return;
            }
            Runnable leftIconAction = textField.getLeftIconAction();
            if (leftIconAction != null) {
                leftIconAction.run();
            }
        });

        FontIcon rightFontIcon = new FontIcon();
        rightFontIcon.getStyleClass().add("right-font-icon");
        rightFontIcon.iconCodeProperty().bind(textField.rightIconProperty());

        StackPane rightPane = new StackPane(rightFontIcon);
        rightPane.getStyleClass().add("right-wrapper");
        rightPane.visibleProperty().bind(textField.textProperty().isNotEmpty());

        rightPane.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (!e.isPrimaryButtonDown()) {
                return;
            }
            Runnable rightIconAction = textField.getRightIconAction();
            if (rightIconAction != null) {
                rightIconAction.run();
            }
        });

        TextField innerTextField = new TextField();
        innerTextField.getStyleClass().add("inner-text-field");
        innerTextField.textProperty().bindBidirectional(textField.textProperty());
        innerTextField.promptTextProperty().bind(textField.promptTextProperty());
        HBox.setHgrow(innerTextField, Priority.ALWAYS);

        container = new HBox();
        container.getStyleClass().add("container");
        container.getChildren().addAll(leftPane, innerTextField, rightPane);

        getChildren().add(container);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double prefWidth = super.computePrefWidth(height - topInset - bottomInset, topInset, rightInset, bottomInset, leftInset);
        return prefWidth == -1 ? container.prefWidth(height - topInset - bottomInset) + leftInset + rightInset : prefWidth + leftInset + rightInset ;
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double prefHeight = super.computePrefHeight(width - leftInset - rightInset, topInset, rightInset, bottomInset, leftInset);
        return prefHeight == -1 ? container.prefHeight(width - leftInset - rightInset) + topInset + bottomInset : prefHeight + topInset + bottomInset;
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

}
