/*
 * Copyright (c) 2022, Gluon and/or its affiliates.
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
 *  - Neither the name of Oracle Corporation and Gluon nor the names of its
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
package com.dlsc.jfxcentral2.utilities.paintpicker.impl.colorpicker;

import com.dlsc.jfxcentral2.utilities.paintpicker.impl.DoubleField;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.FXColorInfoPane;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.PaintMode;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.PaintPickerController;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.gradientpicker.GradientPicker;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.gradientpicker.GradientPickerStop;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Controller class for the color part of the paint editor.
 */
public class ColorPicker extends VBox {

    protected final HBox hBox;
    protected final ScrollPane picker_scrollpane;
    protected final AnchorPane anchorPane;
    protected final Region picker_region;
    protected final Region region;
    protected final Region region0;
    protected final StackPane picker_handle_stackpane;
    protected final Circle circle;
    protected final Circle picker_handle_chip_circle;
    protected final VBox vBox;
    protected final StackPane stackPane;
    protected final Region region1;
    protected final Region chip_region;
    protected final TextField hexaTextfield;
    protected final Slider hue_slider;
    protected final GridPane gridPane;
    protected final StackPane stackPane0;
    protected final Region region2;
    protected final Region alpha_region;
    protected final Slider alpha_slider;
    protected final DoubleField alpha_textfield;
    protected final DoubleField hue_textfield;
    protected final DoubleField saturation_textfield;
    protected final DoubleField brightness_textfield;
    protected final DoubleField red_textfield;
    protected final DoubleField green_textfield;
    protected final DoubleField blue_textfield;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final Label label3;
    protected final Label label4;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final ColumnConstraints columnConstraints2;
    protected final ColumnConstraints columnConstraints3;
    protected final ColumnConstraints columnConstraints4;
    protected final ColumnConstraints columnConstraints5;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;

    private final PaintPickerController paintPickerController;
    private final FXColorInfoPane bottomPane = new FXColorInfoPane();
    private boolean updating = false;

    public FXColorInfoPane getBottomPane() {
        return bottomPane;
    }

    public ColorPicker(PaintPickerController pe) {
        paintPickerController = pe;
        //initialize();
        setMaxHeight(-1.0);
        setPrefHeight(-1.0);
        setPrefWidth(-1.0);
        setSpacing(2.0);

        hBox = new HBox();
        picker_scrollpane = new ScrollPane();
        anchorPane = new AnchorPane();
        picker_region = new Region();
        region = new Region();
        region0 = new Region();
        picker_handle_stackpane = new StackPane();
        circle = new Circle();
        picker_handle_chip_circle = new Circle();
        vBox = new VBox();
        stackPane = new StackPane();
        region1 = new Region();
        chip_region = new Region();
        hexaTextfield = new TextField();
        hue_slider = new Slider();
        hue_slider.setFocusTraversable(false);
        gridPane = new GridPane();
        stackPane0 = new StackPane();
        region2 = new Region();
        alpha_region = new Region();
        alpha_slider = new Slider();
        alpha_textfield = new DoubleField();
        hue_textfield = new DoubleField();
        saturation_textfield = new DoubleField();
        brightness_textfield = new DoubleField();
        red_textfield = new DoubleField();
        green_textfield = new DoubleField();
        blue_textfield = new DoubleField();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        columnConstraints2 = new ColumnConstraints();
        columnConstraints3 = new ColumnConstraints();
        columnConstraints4 = new ColumnConstraints();
        columnConstraints5 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        picker_scrollpane.addEventFilter(KeyEvent.ANY, Event::consume);
        picker_scrollpane.addEventFilter(ScrollEvent.SCROLL, Event::consume);

        setId("CONTENT");
        setVisible(true);

        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setMaxWidth(Double.MAX_VALUE);
        hBox.setPrefHeight(-1.0);
        hBox.setPrefWidth(-1.0);
        hBox.setSpacing(2.0);

        HBox.setHgrow(picker_scrollpane, Priority.NEVER);
        picker_scrollpane.setFitToHeight(false);
        picker_scrollpane.setFitToWidth(false);
        picker_scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        picker_scrollpane.setHmax(1.0);
        picker_scrollpane.setHmin(0.0);
        picker_scrollpane.setHvalue(0.5);
        picker_scrollpane.setMaxHeight(80.0);
        picker_scrollpane.setMaxWidth(220.0);
        picker_scrollpane.setMinHeight(80.0);
        picker_scrollpane.setMinWidth(220.0);
        picker_scrollpane.setPrefViewportHeight(80.0);
        picker_scrollpane.setPrefViewportWidth(200.0);
        picker_scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        picker_scrollpane.setVmax(1.0);
        picker_scrollpane.setVvalue(0.5);

        anchorPane.setId("AnchorPane");
        anchorPane.getStyleClass().add("picker-content");
        anchorPane.setPrefHeight(100.0);
        anchorPane.setPrefWidth(240.0);

        AnchorPane.setBottomAnchor(picker_region, 12.0);
        AnchorPane.setLeftAnchor(picker_region, 12.0);
        AnchorPane.setRightAnchor(picker_region, 12.0);
        AnchorPane.setTopAnchor(picker_region, 12.0);
        picker_region.setOnMouseDragged(this::onPickerRegionDragged);
        picker_region.setOnMousePressed(this::onPickerRegionPressed);
        picker_region.setPrefHeight(80.0);
        picker_region.setPrefWidth(200.0);
        picker_region.setMinHeight(80.0);
        picker_region.setMinWidth(200.0);
        picker_region.setMouseTransparent(false);
        picker_region.setStyle("-fx-background-color: red;");

        AnchorPane.setBottomAnchor(region, 12.0);
        AnchorPane.setLeftAnchor(region, 12.0);
        AnchorPane.setRightAnchor(region, 12.0);
        AnchorPane.setTopAnchor(region, 12.0);
        region.setDisable(true);
        region.setPrefHeight(80.0);
        region.setPrefWidth(200.0);
        region.setStyle("");
        region.getStyleClass().add("saturationRect");

        AnchorPane.setBottomAnchor(region0, 12.0);
        AnchorPane.setLeftAnchor(region0, 12.0);
        AnchorPane.setRightAnchor(region0, 12.0);
        AnchorPane.setTopAnchor(region0, 12.0);
        region0.setDisable(true);
        region0.setPrefHeight(80.0);
        region0.setPrefWidth(200.0);
        region0.setStyle("");
        region0.getStyleClass().add("brightnessRect");

        picker_handle_stackpane.setDisable(true);
        picker_handle_stackpane.setLayoutX(13.0);
        picker_handle_stackpane.setLayoutY(13.0);

        circle.setFill(Color.TRANSPARENT);
        circle.setRadius(7.0);
        circle.setStroke(Color.BLACK);
        circle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        picker_handle_chip_circle.setFill(Color.TRANSPARENT);
        picker_handle_chip_circle.setRadius(6.0);
        picker_handle_chip_circle.setStroke(Color.WHITE);
        picker_handle_chip_circle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        picker_scrollpane.setContent(anchorPane);

        HBox.setHgrow(vBox, Priority.ALWAYS);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setId("VBox");
        vBox.setSpacing(2.0);

        VBox.setVgrow(stackPane, Priority.ALWAYS);

        region1.setStyle("");
        region1.getStyleClass().add("chip-background");

        chip_region.setStyle("");
        chip_region.setPadding(new Insets(0.0));
        StackPane.setMargin(chip_region, new Insets(2.0));

        hexaTextfield.setOnAction(this::onActionHexa);
        hexaTextfield.setPrefColumnCount(6);
        hexaTextfield.setPrefHeight(-1.0);
        hexaTextfield.setPrefWidth(-1.0);
        hexaTextfield.setText("#000000");

        hue_slider.setBlockIncrement(1.0);
        hue_slider.setMax(360.0);
        hue_slider.getStyleClass().add("hue-slider");

        gridPane.setGridLinesVisible(false);
        gridPane.setHgap(2.0);

        GridPane.setColumnIndex(stackPane0, 0);
        GridPane.setColumnSpan(stackPane0, 6);
        GridPane.setRowIndex(stackPane0, 0);
        stackPane0.setId("StackPane");

        region2.setMaxWidth(1000.0);
        region2.setPrefHeight(-1.0);
        region2.setPrefWidth(-1.0);
        region2.getStyleClass().add("opacity-slider-background");
        StackPane.setMargin(region2, new Insets(0.0, 4.0, 0.0, 4.0));

        alpha_region.setMaxWidth(1000.0);
        alpha_region.setPrefHeight(-1.0);
        alpha_region.setPrefWidth(-1.0);
        alpha_region.setStyle("");

        alpha_slider.setBlockIncrement(0.01);
        alpha_slider.setFocusTraversable(false);
        alpha_slider.setMax(1.0);
        alpha_slider.getStyleClass().add("opacity-slider");
        GridPane.setMargin(stackPane0, new Insets(0.0, 0.0, 2.0, 0.0));

        GridPane.setColumnIndex(alpha_textfield, 6);
        GridPane.setMargin(alpha_textfield, new Insets(0, 0, 2, 0));
        GridPane.setRowIndex(alpha_textfield, 0);

        GridPane.setColumnIndex(hue_textfield, 0);
        GridPane.setRowIndex(hue_textfield, 1);

        GridPane.setColumnIndex(saturation_textfield, 1);
        GridPane.setRowIndex(saturation_textfield, 1);

        GridPane.setColumnIndex(brightness_textfield, 2);
        GridPane.setRowIndex(brightness_textfield, 1);

        GridPane.setColumnIndex(red_textfield, 4);
        GridPane.setRowIndex(red_textfield, 1);

        GridPane.setColumnIndex(green_textfield, 5);
        GridPane.setRowIndex(green_textfield, 1);

        GridPane.setColumnIndex(blue_textfield, 6);
        GridPane.setRowIndex(blue_textfield, 1);

        GridPane.setColumnIndex(label, 0);
        GridPane.setRowIndex(label, 2);
        label.getStyleClass().add("small-label");
        label.setText("H °");

        GridPane.setColumnIndex(label0, 1);
        GridPane.setRowIndex(label0, 2);
        label0.getStyleClass().add("small-label");
        label0.setText("S %");

        GridPane.setColumnIndex(label1, 2);
        GridPane.setRowIndex(label1, 2);
        label1.getStyleClass().add("small-label");
        label1.setText("B %");

        GridPane.setColumnIndex(label2, 4);
        GridPane.setRowIndex(label2, 2);
        label2.getStyleClass().add("small-label");
        label2.setText("R");

        GridPane.setColumnIndex(label3, 5);
        GridPane.setRowIndex(label3, 2);
        label3.getStyleClass().add("small-label");
        label3.setText("G");

        GridPane.setColumnIndex(label4, 6);
        GridPane.setRowIndex(label4, 2);
        label4.getStyleClass().add("small-label");
        label4.setText("B");

        columnConstraints.setHalignment(javafx.geometry.HPos.CENTER);
        columnConstraints.setHgrow(Priority.SOMETIMES);
        columnConstraints.setMaxWidth(-1.0);
        columnConstraints.setPrefWidth(-1.0);

        columnConstraints0.setHalignment(javafx.geometry.HPos.CENTER);
        columnConstraints0.setHgrow(Priority.SOMETIMES);
        columnConstraints0.setMaxWidth(-1.0);
        columnConstraints0.setPrefWidth(-1.0);

        columnConstraints1.setHalignment(javafx.geometry.HPos.CENTER);
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        columnConstraints1.setMaxWidth(-1.0);
        columnConstraints1.setPrefWidth(-1.0);

        columnConstraints2.setHgrow(Priority.SOMETIMES);
        columnConstraints2.setMaxWidth(8.0);
        columnConstraints2.setMinWidth(8.0);
        columnConstraints2.setPrefWidth(8.0);

        columnConstraints3.setHalignment(javafx.geometry.HPos.CENTER);
        columnConstraints3.setHgrow(Priority.SOMETIMES);
        columnConstraints3.setMaxWidth(-1.0);
        columnConstraints3.setPrefWidth(-1.0);

        columnConstraints4.setHalignment(javafx.geometry.HPos.CENTER);
        columnConstraints4.setHgrow(Priority.SOMETIMES);
        columnConstraints4.setMaxWidth(-1.0);
        columnConstraints4.setPrefWidth(-1.0);

        columnConstraints5.setHalignment(javafx.geometry.HPos.CENTER);
        columnConstraints5.setHgrow(Priority.SOMETIMES);
        columnConstraints5.setMaxWidth(-1.0);
        columnConstraints5.setPrefWidth(-1.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setVgrow(Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(-1.0);
        rowConstraints0.setVgrow(Priority.SOMETIMES);

        rowConstraints1.setMinHeight(5.0);
        rowConstraints1.setPrefHeight(-1.0);
        rowConstraints1.setVgrow(Priority.SOMETIMES);

        anchorPane.getChildren().add(picker_region);
        anchorPane.getChildren().add(region);
        anchorPane.getChildren().add(region0);
        picker_handle_stackpane.getChildren().add(circle);
        picker_handle_stackpane.getChildren().add(picker_handle_chip_circle);
        anchorPane.getChildren().add(picker_handle_stackpane);
        hBox.getChildren().add(picker_scrollpane);
        stackPane.getChildren().add(region1);
        stackPane.getChildren().add(chip_region);
        vBox.getChildren().add(stackPane);
        vBox.getChildren().add(hexaTextfield);
        hBox.getChildren().add(vBox);
        getChildren().add(hBox);
        getChildren().add(hue_slider);
        stackPane0.getChildren().add(region2);
        stackPane0.getChildren().add(alpha_region);
        stackPane0.getChildren().add(alpha_slider);
        gridPane.getChildren().add(stackPane0);
        gridPane.getChildren().add(alpha_textfield);
        gridPane.getChildren().add(hue_textfield);
        gridPane.getChildren().add(saturation_textfield);
        gridPane.getChildren().add(brightness_textfield);
        gridPane.getChildren().add(red_textfield);
        gridPane.getChildren().add(green_textfield);
        gridPane.getChildren().add(blue_textfield);
        gridPane.getChildren().add(label);
        gridPane.getChildren().add(label0);
        gridPane.getChildren().add(label1);
        gridPane.getChildren().add(label2);
        gridPane.getChildren().add(label3);
        gridPane.getChildren().add(label4);
        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getColumnConstraints().add(columnConstraints2);
        gridPane.getColumnConstraints().add(columnConstraints3);
        gridPane.getColumnConstraints().add(columnConstraints4);
        gridPane.getColumnConstraints().add(columnConstraints5);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        getChildren().add(gridPane);
        getChildren().add(bottomPane);
        //setMargin(bottomPane,new Insets(3,3,2,2));
        initialize();
    }

    private int getRGBFieldValue(DoubleField textField) {
        String text = textField.getText().trim();
        if (text.equals("")) {
            textField.setText("0");
            return 0;
        }
        int value = Double.valueOf(textField.getText()).intValue();
        if (value > 255) {
            textField.setText("" + 255);
            return 255;
        }
        if (value < 0) {
            textField.setText("0");
            return 0;
        }
        return value;
    }

    private double getHSBFieldValue(DoubleField textField, double max) {
        String text = textField.getText().trim();
        if (text.equals("")) {
            textField.setText("0");
            return 0;
        }
        int value = Double.valueOf(textField.getText()).intValue();
        if (value > max) {
            textField.setText("" + max);
            return max;
        }
        if (value < 0) {
            textField.setText("0");
            return 0;
        }
        return value;
    }

    private double getAlphaFieldValue() {
        String text = alpha_textfield.getText().trim();
        if (text.equals("")) {
            alpha_textfield.setText("0");
            return 0;
        }
        double value = Double.parseDouble(alpha_textfield.getText());
        if (value > 1.0) {
            alpha_textfield.setText("1.0");
            return 1.0;
        }
        if (value < 0) {
            alpha_textfield.setText("0");
            return 0;
        }
        return value;
    }

    public Color getValue() {
        double hue = getHSBFieldValue(hue_textfield, 360);
        double saturation = getHSBFieldValue(saturation_textfield, 100) / 100.0;
        double brightness = getHSBFieldValue(brightness_textfield, 100) / 100.0;
        double alpha = getAlphaFieldValue();
        return Color.hsb(hue, saturation, brightness, alpha);
    }

    public String getRGBValue(boolean withAlpha) {
        int r = getRGBFieldValue(red_textfield);
        int g = getRGBFieldValue(green_textfield);
        int b = getRGBFieldValue(blue_textfield);
        String a = "";
        if (withAlpha) {
            a = "," + alpha_textfield.getText();
        }
        return r + "," + g + "," + b + a;
    }

    public String getHexValue(boolean withAlpha, boolean withSharp) {
        String text = hexaTextfield.getText().toUpperCase();
        if (!withSharp) {
            text = text.replace("#", "");
        }
        if (!withAlpha) {
            return text;
        }

        int alphaValue = (int) Math.round(Double.parseDouble(alpha_textfield.getText().isEmpty()?"1":alpha_textfield.getText()) * 255);
        //不足两位补零
        String s = Integer.toHexString(alphaValue).toUpperCase();
        return text + (s.length() == 1 ? "0" + s : s);
    }

    public void updateUI(final Color color) {
        double hue = color.getHue();
        double saturation = color.getSaturation();
        double brightness = color.getBrightness();
        double alpha = color.getOpacity();
        updateUI(hue, saturation, brightness, alpha);
    }

    /**
     * Private
     */
    private void initialize() {
        hue_slider.setStyle(makeHueSliderCSS()); // Make the grad for hue slider
        // Investigate why height + width listeners do not work
        // Indeed, the picker_handle_stackpane bounds may still be null at this point
        // UPDATE BELOW TO BE CALLED ONCE ONLY AT DISPLAY TIME
        picker_region.boundsInParentProperty().addListener((ChangeListener<Bounds>) (ov, oldb, newb) -> {
            picker_scrollpane.setHvalue(0.5);
            picker_scrollpane.setVvalue(0.5);
            // Init time only
            final Paint paint = paintPickerController.getPaintProperty();
            if (paint instanceof Color) {
                updateUI((Color) paint);
            } else if (paint instanceof LinearGradient
                    || paint instanceof RadialGradient) {
                final GradientPicker gradientPicker = paintPickerController.getGradientPicker();
                final GradientPickerStop gradientPickerStop = gradientPicker.getSelectedStop();
                // Update the color preview with the color of the selected stop
                if (gradientPickerStop != null) {
                    updateUI(gradientPickerStop.getColor());
                }
            }
        });

        final ChangeListener<Boolean> onHSBFocusedChange = (ov, oldValue, newValue) -> {
            if (newValue == false) {
                // Update UI
                final Color color = updateUI_OnHSBChange();
                // Update model
                setPaintProperty(color);
            }
        };
        final ChangeListener<Boolean> onRGBFocusedChange = (ov, oldValue, newValue) -> {
            if (newValue == false) {
                // Update UI
                final Color color = updateUI_OnRGBChange();
                // Update model
                setPaintProperty(color);
            }
        };
        final ChangeListener<Boolean> onHexaFocusedChange = (ov, oldValue, newValue) -> {
            if (newValue == false) {
                try {
                    // Update UI
                    final Color color = updateUI_OnHexaChange();
                    // Update model
                    setPaintProperty(color);
                } catch (IllegalArgumentException iae) {
                    handleHexaException();
                }
            }
        };
        EventHandler<ActionEvent> onHSBAction = actionEvent -> {
            final Color color = updateUI_OnHSBChange();
            setPaintProperty(color);
        };
        EventHandler<ActionEvent> onRGBAction = actionEvent -> {
            final Color color = updateUI_OnRGBChange();
            // Update model
            setPaintProperty(color);
        };
        EventHandler<ActionEvent> onHexaAction = actionEvent -> {
            try {
                // Update UI
                final Color color = updateUI_OnHexaChange();
                // Update model
                setPaintProperty(color);
            } catch (IllegalArgumentException iae) {
                handleHexaException();
            }
        };

        // TextField ON FOCUS LOST event handler
        hue_textfield.focusedProperty().addListener(onHSBFocusedChange);
        saturation_textfield.focusedProperty().addListener(onHSBFocusedChange);
        brightness_textfield.focusedProperty().addListener(onHSBFocusedChange);
        alpha_textfield.focusedProperty().addListener(onHSBFocusedChange);
        red_textfield.focusedProperty().addListener(onRGBFocusedChange);
        green_textfield.focusedProperty().addListener(onRGBFocusedChange);
        blue_textfield.focusedProperty().addListener(onRGBFocusedChange);
        hexaTextfield.focusedProperty().addListener(onHexaFocusedChange);
        //按回车键
        hue_textfield.setOnAction(onHSBAction);
        saturation_textfield.setOnAction(onHSBAction);
        brightness_textfield.setOnAction(onHSBAction);
        alpha_textfield.setOnAction(onHSBAction);
        red_textfield.setOnAction(onRGBAction);
        green_textfield.setOnAction(onRGBAction);
        blue_textfield.setOnAction(onRGBAction);
        hexaTextfield.setOnAction(onHexaAction);

        // Slider ON VALUE CHANGE event handler
        hue_slider.valueProperty().addListener((ChangeListener<Number>) (ov, oldValue, newValue) -> {
            if (updating == true) {
                return;
            }
            double hue = newValue.doubleValue();
            // retrieve HSB TextFields values
            double saturation = Double.valueOf(saturation_textfield.getText()) / 100.0;
            double brightness = Double.valueOf(brightness_textfield.getText()) / 100.0;
            double alpha = getAlphaFieldValue();
            // Update UI
            final Color color = updateUI(hue, saturation, brightness, alpha);
            // Update model
            setPaintProperty(color);
        });
        alpha_slider.valueProperty().addListener((ChangeListener<Number>) (ov, oldValue, newValue) -> {
            if (updating == true) {
                return;
            }
            double alpha = newValue.doubleValue();
            // retrieve HSB TextFields values
            double hue = Double.valueOf(hue_textfield.getText());
            double saturation = Double.valueOf(saturation_textfield.getText()) / 100.0;
            double brightness = Double.valueOf(brightness_textfield.getText()) / 100.0;

            // Update UI
            final Color color = updateUI(hue, saturation, brightness, alpha);
            // Update model
            setPaintProperty(color);
        });

        final ChangeListener<Boolean> liveUpdateListener = (ov, oldValue, newValue) -> paintPickerController.setLiveUpdate(newValue);
        picker_region.pressedProperty().addListener(liveUpdateListener);
        hue_slider.pressedProperty().addListener(liveUpdateListener);
        alpha_slider.pressedProperty().addListener(liveUpdateListener);
    }

    /**
     * When updating the color picker, we may update :
     * - either the color of the paint picker itself (Color mode)
     * - or the color of the selected stop (LinearGradient or RadialGradient mode)
     *
     * @param color
     */
    private void setPaintProperty(Color color) {
        final PaintMode paintMode = paintPickerController.getMode();
        final Paint paint;
        switch (paintMode) {
            case COLOR:
                paint = color;
                break;
            case LINEAR:
            case RADIAL:
                final GradientPicker gradientPicker = paintPickerController.getGradientPicker();
                final GradientPickerStop gradientPickerStop = gradientPicker.getSelectedStop();
                // Set the color of the selected stop
                if (gradientPickerStop != null) {
                    gradientPickerStop.setColor(color);
                }
                // Update gradient preview
                paint = gradientPicker.getValue(paintMode);
                gradientPicker.updatePreview(paint);
                break;
            default:
                paint = null;
                break;
        }
        paintPickerController.setPaintProperty(paint);
    }

    void onActionHue(ActionEvent event) {
        onHSBChange(event);
        event.consume();
    }

    void onActionSaturation(ActionEvent event) {
        onHSBChange(event);
        event.consume();
    }

    void onActionBrightness(ActionEvent event) {
        onHSBChange(event);
        event.consume();
    }

    void onActionAlpha(ActionEvent event) {
        onHSBChange(event);
        event.consume();
    }

    void onActionRed(ActionEvent event) {
        onRGBChange(event);
        event.consume();
    }

    void onActionGreen(ActionEvent event) {
        onRGBChange(event);
        event.consume();
    }

    void onActionBlue(ActionEvent event) {
        onRGBChange(event);
        event.consume();
    }

    void onActionHexa(ActionEvent event) {
        onHexaChange(event);
        event.consume();
    }

    private void onHSBChange(ActionEvent event) {
        // Update UI
        final Color color = updateUI_OnHSBChange();
        final Object source = event.getSource();
        assert source instanceof TextField;
        ((TextField) source).selectAll();
        // Update model
        setPaintProperty(color);
    }

    private void onRGBChange(ActionEvent event) {
        // Update UI
        final Color color = updateUI_OnRGBChange();
        final Object source = event.getSource();
        assert source instanceof TextField;
        ((TextField) source).selectAll();
        // Update model
        setPaintProperty(color);
    }

    private void onHexaChange(ActionEvent event) {
        try {
            // Update UI
            final Color color = updateUI_OnHexaChange();
            final Object source = event.getSource();
            assert source instanceof TextField;
            ((TextField) source).selectAll();
            // Update model
            setPaintProperty(color);
        } catch (IllegalArgumentException iae) {
            handleHexaException();
        }
    }

    void onPickerRegionPressed(MouseEvent e) {
        double mx = e.getX();
        double my = e.getY();
        // Update UI
        final Color color = updateUI_OnPickerChange(mx, my);
        // Update model
        setPaintProperty(color);
    }

    void onPickerRegionDragged(MouseEvent e) {
        double mx = e.getX();
        double my = e.getY();
        // Update UI
        final Color color = updateUI_OnPickerChange(mx, my);
        // Update model
        setPaintProperty(color);
    }

    private Color updateUI_OnPickerChange(double x, double y) {
        double w = picker_region.getWidth();
        double h = picker_region.getHeight();
        double hue = Double.valueOf(hue_textfield.getText());
        double saturation = x / w;
        double brightness = 1.0 - (y / h);
        double alpha = getAlphaFieldValue();
        return updateUI(hue, saturation, brightness, alpha);
    }

    private Color updateUI_OnHSBChange() {
        // retrieve HSB TextFields values
        double hue = Double.parseDouble(hue_textfield.getText());
        double saturation = Double.parseDouble(saturation_textfield.getText()) / 100.0;
        double brightness = Double.parseDouble(brightness_textfield.getText()) / 100.0;
        double alpha = Double.parseDouble(alpha_textfield.getText());
        return updateUI(hue, saturation, brightness, alpha);
    }

    //private int getHSBFieldValue(DoubleTextField textField) {
    //    String text = textField.getText().trim();
    //    if (text.equals("")) {
    //        textField.setText("0");
    //        return 0;
    //    }
    //    int value = Double.valueOf(textField.getText()).intValue();
    //    if (value > 100) {
    //        textField.setText("100");
    //        return 100;
    //    }
    //    return value;
    //}

    private Color updateUI_OnRGBChange() {
        // retrieve RGB TextFields values
        int red = getRGBFieldValue(red_textfield);
        int green = getRGBFieldValue(green_textfield);
        int blue = getRGBFieldValue(blue_textfield);
        // retrieve HSB values from RGB values
        final Color color = Color.rgb(red, green, blue);
        double hue = color.getHue();
        double saturation = color.getSaturation();
        double brightness = color.getBrightness();
        double alpha = getAlphaFieldValue();
        return updateUI(hue, saturation, brightness, alpha);
    }

    private Color updateUI_OnHexaChange() {
        // retrieve Hexa TextField value
        final String hexa = hexaTextfield.getText().trim();
        final Color color = Color.web(hexa);
        double hue = color.getHue();
        double saturation = color.getSaturation();
        double brightness = color.getBrightness();
        double alpha = getAlphaFieldValue();
        return updateUI(hue, saturation, brightness, alpha);
    }

    public TextField getHexaTextfield() {
        return hexaTextfield;
    }

    private Color updateUI(double hue, double saturation, double brightness, double alpha) {
        updating = true;
        // update the HSB values so they are within range
        hue = PaintPickerController.clamp(0, hue, 360);
        saturation = PaintPickerController.clamp(0, saturation, 1);
        brightness = PaintPickerController.clamp(0, brightness, 1);
        alpha = BigDecimal.valueOf(PaintPickerController.clamp(0, alpha, 1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
        // make an rgb color from the hsb
        final Color color = Color.hsb(hue, saturation, brightness, alpha);
        int red = (int) Math.round(color.getRed() * 255);
        int green = (int) Math.round(color.getGreen() * 255);
        int blue = (int) Math.round(color.getBlue() * 255);
        final String hexa = String.format("#%02x%02x%02x", red, green, blue); //NOI18N

        // Set TextFields value
        hue_textfield.setText(String.valueOf((int) hue));
        DecimalFormat format = new DecimalFormat("0.##");
        saturation_textfield.setText(format.format(saturation * 100));
        brightness_textfield.setText(format.format(brightness * 100));

        double alpha_rounded = round(alpha, 100); // 2 decimals rounding
        alpha_textfield.setText(Double.toString(alpha_rounded));
        red_textfield.setText(Integer.toString(red));
        green_textfield.setText(Integer.toString(green));
        blue_textfield.setText(Integer.toString(blue));
        hexaTextfield.setText(hexa);

        // Set the background color of the chips
        final StringBuilder sb = new StringBuilder();
        sb.append("hsb("); //NOI18N
        sb.append(hue);
        sb.append(", "); //NOI18N
        sb.append(saturation * 100);
        sb.append("%, "); //NOI18N
        sb.append(brightness * 100);
        sb.append("%, "); //NOI18N
        sb.append(alpha);
        sb.append(")"); //NOI18N
        final String hsbCssValue = sb.toString();
        final String chipStyle = "-fx-background-color: " + hsbCssValue; //NOI18N

        chip_region.setStyle(chipStyle);

        picker_handle_chip_circle.setFill(Color.rgb(red, green, blue));
        final String alphaChipStyle = "-fx-background-color: " //NOI18N
                + "linear-gradient(to right, transparent, " + hsbCssValue + ")"; //NOI18N

        alpha_region.setStyle(alphaChipStyle);


        // Set the background color of the picker region
        // (force saturation and brightness to 100% - don't add opacity)
        final String pickerRegionStyle = "-fx-background-color: hsb(" //NOI18N
                + hue + ", 100%, 100%, 1.0);"; //NOI18N
        picker_region.setStyle(pickerRegionStyle);

        // Position the picker dot
        double xSat = picker_region.getWidth() * saturation; // Saturation is on x axis
        double yBri = picker_region.getHeight() * (1.0 - brightness); // Brightness is on y axis (reversed as white is top)
        double xPos = (picker_region.getBoundsInParent().getMinX() + xSat) - picker_handle_stackpane.getWidth() / 2;
        double yPos = (picker_region.getBoundsInParent().getMinY() + yBri) - picker_handle_stackpane.getHeight() / 2;
        picker_handle_stackpane.setLayoutX(xPos);
        picker_handle_stackpane.setLayoutY(yPos);

        // Set the Sliders value
        hue_slider.adjustValue(hue);
        alpha_slider.adjustValue(alpha);

        updating = false;
        return color;
    }
    private String makeHueSliderCSS() {
        final StringBuilder sb = new StringBuilder();
        sb.append("-fx-background-color: linear-gradient(to right "); //NOI18N
        for (int i = 0; i < 12; i++) { // max 12 gradient stops
            sb.append(", hsb("); //NOI18N
            sb.append(i * (360 / 11));
            sb.append(", 100%, 100%)"); //NOI18N
        }
        sb.append(");"); //NOI18N
        return sb.toString();
    }

    private double round(double value, int roundingFactor) {
        double doubleRounded = Math.round(value * roundingFactor);
        return doubleRounded / roundingFactor;
    }

    private void handleHexaException() {
        paintPickerController.getDelegate().handleError(
                "log.warning.color.creation.error.hexadecimal",
                hexaTextfield.getText().trim());
        // Update UI to previous value
        final Color color;
        switch (paintPickerController.getMode()) {
            case COLOR:
                assert paintPickerController.getPaintProperty() instanceof Color;
                color = (Color) paintPickerController.getPaintProperty();
                break;
            case LINEAR:
            case RADIAL:
                final GradientPicker gradientPicker = paintPickerController.getGradientPicker();
                if (gradientPicker.getGradientStops().isEmpty() == false) {
                    final GradientPickerStop stop = paintPickerController.getGradientPicker().getSelectedStop();
                    assert stop != null;
                    color = stop.getColor();
                } else {
                    color = Color.BLACK;
                }
                break;
            default:
                color = null;
                assert false;
        }
        assert color != null;
        updateUI(color);
        hexaTextfield.selectAll();
    }
}
