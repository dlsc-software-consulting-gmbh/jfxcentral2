package com.dlsc.jfxcentral2.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class TopPane extends Pane {

    private TopMenuBar topMenuBar;
    private Node content;

    public TopPane(TopMenuBar topMenuBar, Node content) {
        getStyleClass().add("top-pane");
        this.topMenuBar = Objects.requireNonNull(topMenuBar);
        this.topMenuBar.modeProperty().addListener(it -> requestLayout());
        this.content = Objects.requireNonNull(content);
        getChildren().setAll(content, topMenuBar);
    }

    @Override
    protected void layoutChildren() {
        Insets insets = getInsets();

        double toolbarHeight = topMenuBar.prefHeight(getWidth() - insets.getLeft() - insets.getRight());

        topMenuBar.resizeRelocate(insets.getLeft(), insets.getTop(), getWidth() - insets.getLeft() - insets.getRight(), toolbarHeight);

        if (topMenuBar.getMode().equals(TopMenuBar.Mode.DARK)) {
            content.resizeRelocate(insets.getLeft(), insets.getTop(), getWidth() - insets.getLeft() - insets.getRight(), getHeight() - insets.getTop() - insets.getBottom());
        } else {
            content.resizeRelocate(insets.getLeft(), insets.getTop() + toolbarHeight, getWidth() - insets.getLeft() - insets.getRight(), getHeight() - toolbarHeight - insets.getTop() - insets.getBottom());
        }
    }

    @Override
    protected double computePrefHeight(double width) {
        double ph = content.prefHeight(width - getInsets().getLeft() - getInsets().getRight());

        if (topMenuBar.getMode().equals(TopMenuBar.Mode.LIGHT)) {
            ph += topMenuBar.prefHeight(width - getInsets().getLeft() - getInsets().getRight());
        }

        return ph;
    }

    @Override
    protected double computePrefWidth(double height) {
        double availableHeight = height - getInsets().getTop() - getInsets().getBottom();
        return Math.max(topMenuBar.prefWidth(availableHeight), content.prefWidth(availableHeight));
    }
}
