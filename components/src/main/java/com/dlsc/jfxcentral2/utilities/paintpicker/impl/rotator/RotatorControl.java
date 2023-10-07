/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.dlsc.jfxcentral2.utilities.paintpicker.impl.rotator;

import com.dlsc.jfxcentral2.components.DoubleTextField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class RotatorControl extends GridPane {
    protected final Label rotator_label;
    protected final DoubleTextField rotator_textfield;
    protected final StackPane stackPane;
    protected final Button rotator_dial;
    protected final Button rotator_handle;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;


    private final int roundingFactor = 100; // 2 decimals rounding

    private final DoubleProperty rotation = new SimpleDoubleProperty();

    public RotatorControl(String text) {
        rotator_label = new Label();
        rotator_textfield = new DoubleTextField();
        stackPane = new StackPane();
        rotator_dial = new Button();
        rotator_handle = new Button();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();

        setId("GridPane");

        GridPane.setColumnIndex(rotator_label, 0);
        GridPane.setRowIndex(rotator_label, 0);
        rotator_label.setText("Label");

        GridPane.setColumnIndex(rotator_textfield, 1);
        GridPane.setRowIndex(rotator_textfield, 0);
        rotator_textfield.getStyleClass().add("LF3-textfield1");

        GridPane.setColumnIndex(stackPane, 2);
        GridPane.setRowIndex(stackPane, 0);
        stackPane.setId("StackPane");

        rotator_dial.setMnemonicParsing(false);
        rotator_dial.setOnMouseDragged(this::rotatorMouseDragged);
        rotator_dial.setOnMousePressed(this::rotatorMousePressed);
        rotator_dial.getStyleClass().add("rotation-dial");
        rotator_dial.setText("");

        rotator_handle.setDisable(true);
        rotator_handle.setMnemonicParsing(false);
        rotator_handle.setOpacity(1.0);
        rotator_handle.setRotate(0.0);
        rotator_handle.getStyleClass().add("rotation-handle");
        rotator_handle.setText("");
        GridPane.setMargin(stackPane, new Insets(0.0, 0.0, 0.0, 4.0));

        columnConstraints.setHalignment(javafx.geometry.HPos.RIGHT);
        columnConstraints.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPercentWidth(40.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints0.setMinWidth(10.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints1.setMinWidth(10.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        getChildren().add(rotator_label);
        getChildren().add(rotator_textfield);
        stackPane.getChildren().add(rotator_dial);
        stackPane.getChildren().add(rotator_handle);
        getChildren().add(stackPane);
        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getColumnConstraints().add(columnConstraints1);
        getRowConstraints().add(rowConstraints);

        rotator_textfield.setOnAction(this::rotatorAction);
        rotator_dial.setOnMouseDragged(this::rotatorMouseDragged);
        rotator_dial.setOnMousePressed(this::rotatorMousePressed);
        initialize(text);
    }

    public final DoubleProperty rotationProperty() {
        return rotation;
    }

    public final double getRotationProperty() {
        return rotation.get();
    }

    public final void setRotationProperty(double value) {
        rotation.set(value);
    }

    /**
     * Private
     */
    private void initialize(String text) {



        assert rotator_label != null;
        rotator_label.setText(text);
        
        rotator_dial.setOnAction((ActionEvent event) -> {
            event.consume();
        });
        rotator_handle.setOnAction((ActionEvent event) -> {
            event.consume();
        });
    }

    @FXML
    void rotatorAction(ActionEvent event) {
        double value = Double.parseDouble(rotator_textfield.getText());
        double rounded = round(value, roundingFactor);
        rotate(rounded);
        rotator_textfield.selectAll();
        event.consume();
    }

    @FXML
    void rotatorMousePressed(MouseEvent e) {
        rotatorMouseDragged(e);
    }

    @FXML
    void rotatorMouseDragged(MouseEvent e) {
        final Parent p = rotator_dial.getParent();
        final Bounds b = rotator_dial.getLayoutBounds();
        final Double centerX = b.getMinX() + (b.getWidth() / 2);
        final Double centerY = b.getMinY() + (b.getHeight() / 2);
        final Point2D center = p.localToParent(centerX, centerY);
        final Point2D mouse = p.localToParent(e.getX(), e.getY());
        final Double deltaX = mouse.getX() - center.getX();
        final Double deltaY = mouse.getY() - center.getY();
        final Double radians = Math.atan2(deltaY, deltaX);
        rotate(Math.toDegrees(radians));
    }

    private void rotate(Double value) {
        double rounded = round(value, roundingFactor);
        rotation.set(rounded);
        rotator_handle.setRotate(rounded);
        rotator_textfield.setText(Double.toString(rounded));
    }

    private double round(double value, int roundingFactor) {
        double doubleRounded = Math.round(value * roundingFactor);
        return doubleRounded / roundingFactor;
    }
}
