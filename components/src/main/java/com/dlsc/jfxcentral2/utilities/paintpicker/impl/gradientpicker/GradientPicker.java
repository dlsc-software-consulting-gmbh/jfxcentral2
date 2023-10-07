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
package com.dlsc.jfxcentral2.utilities.paintpicker.impl.gradientpicker;

import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.PaintMode;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.PaintPickerController;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.rotator.RotatorControl;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.slider.SliderControl;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the gradient part of the paint editor.
 */
public class GradientPicker extends VBox {
    protected final StackPane slider_container;
    protected final Label stop_label;
    protected final Pane track_pane;
    protected final GridPane gridPane;
    protected final StackPane stackPane;
    protected final Rectangle preview_rect;
    protected final StackPane stackPane0;
    protected final Slider centerX_slider;
    protected final Slider startX_slider;
    protected final ContextMenu contextMenu;
    protected final CustomMenuItem customMenuItem;
    protected final HBox hBox;
    protected final Label label;
    protected final TextField textField;
    protected final StackPane stackPane1;
    protected final Slider centerY_slider;
    protected final Slider startY_slider;
    protected final Slider endY_slider;
    protected final Slider endX_slider;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final VBox shared_container;
    protected final GridPane gridPane0;
    protected final Label label0;
    protected final CheckBox proportional_checkbox;
    protected final Label label1;
    protected final ComboBox<CycleMethod> cycleMethod_choicebox;
    protected final ColumnConstraints columnConstraints2;
    protected final ColumnConstraints columnConstraints3;
    protected final RowConstraints rowConstraints2;
    protected final RowConstraints rowConstraints3;
    protected final VBox radial_container;
    private final PaintPickerController paintPicker;
    private final RotatorControl focusAngleRotator
            = new RotatorControl("focusAngle"); //NOI18N
    private final SliderControl focusDistanceSlider
            = new SliderControl("focusDistance", -1.0, 1.0, 0.0); //NOI18N
    private final SliderControl radiusSlider
            = new SliderControl("radius", 0.0, 1.0, 0.5); //NOI18N
    private final List<GradientPickerStop> gradientPickerStops = new ArrayList<>();
    private final int maxStops = 12; // the numbers of stops supported in platform

    public GradientPicker(PaintPickerController pe) {
        getStyleClass().add("gradient-picker");
        paintPicker = pe;
        slider_container = new StackPane();
        stop_label = new Label();
        track_pane = new Pane();
        track_pane.getStyleClass().add("track-pane");
        gridPane = new GridPane();
        gridPane.getStyleClass().add("gradient-gridpane");
        stackPane = new StackPane();
        preview_rect = new Rectangle();
        preview_rect.getStyleClass().add("preview-rect");
        stackPane0 = new StackPane();
        centerX_slider = new Slider();
        startX_slider = new Slider();
        contextMenu = new ContextMenu();
        customMenuItem = new CustomMenuItem();
        hBox = new HBox();
        label = new Label();
        textField = new TextField();
        stackPane1 = new StackPane();
        centerY_slider = new Slider();
        centerY_slider.getStyleClass().add("center-y-slider");
        startY_slider = new Slider();
        startY_slider.getStyleClass().add("start-y-slider");
        endY_slider = new Slider();
        endY_slider.getStyleClass().add("end-y-slider");
        endX_slider = new Slider();
        endX_slider.getStyleClass().add("end-x-slider");
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        shared_container = new VBox();
        gridPane0 = new GridPane();
        label0 = new Label();
        proportional_checkbox = new CheckBox();
        label1 = new Label();
        cycleMethod_choicebox = new ComboBox<>();
        columnConstraints2 = new ColumnConstraints();
        columnConstraints3 = new ColumnConstraints();
        rowConstraints2 = new RowConstraints();
        rowConstraints3 = new RowConstraints();
        radial_container = new VBox();

        slider_container.setMinHeight(10.0);
        slider_container.setStyle("");
        slider_container.getStyleClass().add("track-background");

        StackPane.setAlignment(stop_label, javafx.geometry.Pos.TOP_CENTER);
        stop_label.getStyleClass().addAll("small-label", "stop-label");
        stop_label.setText("Click to add stop");
        StackPane.setMargin(stop_label, new Insets(2.0, 0.0, 0.0, 0.0));

        track_pane.setOnMouseDragged(this::sliderDragged);
        track_pane.setOnMousePressed(this::sliderPressed);
        VBox.setMargin(slider_container, new Insets(0.0, 0.0, 8.0, 0.0));

        gridPane.setHgap(4.0);
        gridPane.setMaxHeight(USE_PREF_SIZE);
        gridPane.setMaxWidth(USE_PREF_SIZE);
        gridPane.setVgap(4.0);

        GridPane.setColumnIndex(stackPane, 1);
        GridPane.setRowIndex(stackPane, 1);
        stackPane.setStyle("");
        stackPane.getStyleClass().add("gradient-background");

        preview_rect.setArcHeight(5.0);
        preview_rect.setArcWidth(5.0);
        preview_rect.setFill(Color.DODGERBLUE);
        preview_rect.setHeight(220.0);
        preview_rect.setStroke(Color.BLACK);
        preview_rect.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        preview_rect.setVisible(true);
        preview_rect.setWidth(220.0);

        GridPane.setColumnIndex(stackPane0, 1);
        GridPane.setRowIndex(stackPane0, 0);

        centerX_slider.setMax(1.0);
        centerX_slider.setShowTickLabels(false);
        centerX_slider.setValue(0.5);
        centerX_slider.setVisible(false);
        StackPane.setMargin(centerX_slider, new Insets(0.0, 15.0, 0.0, 15.0));

        startX_slider.setMax(1.0);
        startX_slider.setShowTickLabels(false);
        startX_slider.setVisible(true);

        customMenuItem.setHideOnClick(false);
        customMenuItem.setMnemonicParsing(false);
        customMenuItem.setText("");

        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setSpacing(0.0);

        label.setText("startX");

        textField.setPrefColumnCount(3);
        customMenuItem.setContent(hBox);
        startX_slider.setContextMenu(contextMenu);

        GridPane.setColumnIndex(stackPane1, 0);
        GridPane.setRowIndex(stackPane1, 1);

        centerY_slider.setMax(1.0);
        centerY_slider.setOrientation(javafx.geometry.Orientation.VERTICAL);
        centerY_slider.setRotate(180.0);
        centerY_slider.setValue(0.5);
        centerY_slider.setVisible(false);

        startY_slider.setMax(1.0);
        startY_slider.setOrientation(javafx.geometry.Orientation.VERTICAL);
        startY_slider.setRotate(180.0);

        GridPane.setColumnIndex(endY_slider, 2);
        GridPane.setRowIndex(endY_slider, 1);
        endY_slider.setMax(1.0);
        endY_slider.setOrientation(javafx.geometry.Orientation.VERTICAL);
        endY_slider.setRotate(180.0);
        endY_slider.setValue(1.0);

        GridPane.setColumnIndex(endX_slider, 1);
        GridPane.setRowIndex(endX_slider, 2);
        endX_slider.setMax(1.0);
        endX_slider.setValue(1.0);

        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setMinWidth(-1.0);
        columnConstraints.setPrefWidth(-1.0);

        columnConstraints0.setHgrow(Priority.NEVER);
        columnConstraints0.setMinWidth(-1.0);
        columnConstraints0.setPrefWidth(-1.0);

        columnConstraints1.setHgrow(Priority.ALWAYS);
        columnConstraints1.setMinWidth(-1.0);
        columnConstraints1.setPrefWidth(-1.0);

        rowConstraints.setMinHeight(-1.0);
        rowConstraints.setPrefHeight(-1.0);
        rowConstraints.setVgrow(Priority.SOMETIMES);

        rowConstraints0.setMinHeight(-1.0);
        rowConstraints0.setPrefHeight(-1.0);
        rowConstraints0.setVgrow(Priority.SOMETIMES);

        rowConstraints1.setMinHeight(-1.0);
        rowConstraints1.setPrefHeight(-1.0);
        rowConstraints1.setVgrow(Priority.SOMETIMES);

        VBox.setMargin(shared_container, new Insets(0, 0, 8, 0));
        shared_container.setMaxHeight(-1.0);
        shared_container.setMinHeight(10.0);
        shared_container.setSpacing(5.0);

        gridPane0.setHgap(4.0);
        gridPane0.setId("GridPane");
        gridPane0.setVgap(5.0);

        GridPane.setColumnIndex(label0, 0);
        GridPane.setRowIndex(label0, 0);
        label0.setText("proportional");

        GridPane.setColumnIndex(proportional_checkbox, 1);
        GridPane.setRowIndex(proportional_checkbox, 0);
        proportional_checkbox.setMnemonicParsing(false);
        proportional_checkbox.setText("");

        GridPane.setColumnIndex(label1, 0);
        GridPane.setRowIndex(label1, 1);
        label1.setText("cycleMethod");

        GridPane.setColumnIndex(cycleMethod_choicebox, 1);
        GridPane.setRowIndex(cycleMethod_choicebox, 1);

        columnConstraints2.setHalignment(javafx.geometry.HPos.RIGHT);
        columnConstraints2.setHgrow(Priority.ALWAYS);
        columnConstraints2.setMinWidth(10.0);
        columnConstraints2.setPercentWidth(40.0);

        columnConstraints3.setHgrow(Priority.SOMETIMES);
        columnConstraints3.setMinWidth(-1.0);

        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setVgrow(Priority.SOMETIMES);

        rowConstraints3.setMinHeight(10.0);
        rowConstraints3.setVgrow(Priority.SOMETIMES);

        radial_container.setMaxHeight(-1.0);
        radial_container.setMinHeight(10.0);
        radial_container.setSpacing(5.0);

        slider_container.getChildren().addAll(stop_label, track_pane);
        getChildren().add(slider_container);
        stackPane.getChildren().add(preview_rect);
        gridPane.getChildren().add(stackPane);
        stackPane0.getChildren().add(centerX_slider);
        hBox.getChildren().add(label);
        hBox.getChildren().add(textField);
        contextMenu.getItems().add(customMenuItem);
        stackPane0.getChildren().add(startX_slider);
        gridPane.getChildren().add(stackPane0);
        stackPane1.getChildren().add(centerY_slider);
        stackPane1.getChildren().add(startY_slider);
        gridPane.getChildren().add(stackPane1);
        gridPane.getChildren().add(endY_slider);
        gridPane.getChildren().add(endX_slider);
        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        getChildren().add(new StackPane(gridPane));
        gridPane0.getChildren().add(label0);
        gridPane0.getChildren().add(proportional_checkbox);
        gridPane0.getChildren().add(label1);
        cycleMethod_choicebox.getItems().addAll(CycleMethod.NO_CYCLE, CycleMethod.REFLECT, CycleMethod.REPEAT);
        gridPane0.getChildren().add(cycleMethod_choicebox);
        gridPane0.getColumnConstraints().add(columnConstraints2);
        gridPane0.getColumnConstraints().add(columnConstraints3);
        gridPane0.getRowConstraints().add(rowConstraints2);
        gridPane0.getRowConstraints().add(rowConstraints3);
        shared_container.getChildren().add(gridPane0);
        getChildren().add(shared_container);
        getChildren().add(radial_container);

        initialize();
    }

    public final PaintPickerController getPaintPickerController() {
        return paintPicker;
    }

    public Paint getValue(PaintMode paintMode) {
        final Paint paint;
        switch (paintMode) {
            case LINEAR:
                double startX = startX_slider.getValue();
                double startY = startY_slider.getValue();
                double endX = endX_slider.getValue();
                double endY = endY_slider.getValue();
                boolean linear_proportional = proportional_checkbox.isSelected();
                final CycleMethod linear_cycleMethod = cycleMethod_choicebox.getValue();
                paint = new LinearGradient(startX, startY, endX, endY,
                        linear_proportional, linear_cycleMethod, getStops());
                break;
            case RADIAL:
                double focusAngle = focusAngleRotator.getRotationProperty();
                double focusDistance = focusDistanceSlider.getSlider().getValue();
                double centerX = centerX_slider.getValue();
                double centerY = centerY_slider.getValue();
                double radius = radiusSlider.getSlider().getValue();
                boolean radial_proportional = proportional_checkbox.isSelected();
                final CycleMethod radial_cycleMethod = cycleMethod_choicebox.getValue();
                paint = new RadialGradient(focusAngle, focusDistance, centerX, centerY, radius,
                        radial_proportional, radial_cycleMethod, getStops());
                break;
            default:
                assert false;
                paint = null;
                break;
        }
        return paint;
    }

    public boolean isGradientStopsEmpty() {
        return gradientPickerStops.isEmpty();
    }

    public List<GradientPickerStop> getGradientStops() {
        return gradientPickerStops;
    }

    public GradientPickerStop getSelectedStop() {
        GradientPickerStop selectedThumb = null;
        for (GradientPickerStop gradientStopThumb : gradientPickerStops) {
            if (gradientStopThumb.isSelected()) {
                selectedThumb = gradientStopThumb;
            }
        }
        return selectedThumb;
    }

    public void updateUI(Paint value) {
        assert value instanceof LinearGradient || value instanceof RadialGradient;
        if (value instanceof LinearGradient) {
            final LinearGradient linear = (LinearGradient) value;
            startX_slider.setValue(linear.getStartX());
            startY_slider.setValue(linear.getStartY());
            endX_slider.setValue(linear.getEndX());
            endY_slider.setValue(linear.getEndY());
            proportional_checkbox.setSelected(linear.isProportional());
            cycleMethod_choicebox.setValue(linear.getCycleMethod());
            // clear first
            removeAllStops();
            for (Stop stop : linear.getStops()) {
                // Update stops
                addStop(0.0, 1.0, stop.getOffset(), stop.getColor());
            }

        } else {
            assert value instanceof RadialGradient;
            final RadialGradient radial = (RadialGradient) value;
            centerX_slider.setValue(radial.getCenterX());
            centerY_slider.setValue(radial.getCenterY());
            focusAngleRotator.setRotationProperty(radial.getFocusAngle());
            focusDistanceSlider.getSlider().setValue(radial.getFocusDistance());
            radiusSlider.getSlider().setValue(radial.getRadius());
            proportional_checkbox.setSelected(radial.isProportional());
            cycleMethod_choicebox.setValue(radial.getCycleMethod());
            // clear first
            removeAllStops();
            for (Stop stop : radial.getStops()) {
                // Update stops
                addStop(0.0, 1.0, stop.getOffset(), stop.getColor());
            }
        }
        setMode(value);
        updatePreview(value);
    }

    public void updatePreview(Paint value) {
        preview_rect.setFill(value);
    }

    public void setMode(Paint value) {
        final PaintMode paintMode;
        if (value instanceof LinearGradient) {
            paintMode = PaintMode.LINEAR;
        } else {
            assert value instanceof RadialGradient;
            paintMode = PaintMode.RADIAL;
        }
        startX_slider.setVisible(paintMode == PaintMode.LINEAR);
        startY_slider.setVisible(paintMode == PaintMode.LINEAR);
        endX_slider.setVisible(paintMode == PaintMode.LINEAR);
        endY_slider.setVisible(paintMode == PaintMode.LINEAR);
        centerX_slider.setVisible(paintMode == PaintMode.RADIAL);
        centerY_slider.setVisible(paintMode == PaintMode.RADIAL);
        radial_container.setVisible(paintMode == PaintMode.RADIAL);
        radial_container.setManaged(paintMode == PaintMode.RADIAL);
    }

    /**
     * Private
     */
    private void initialize() {

        // Add two default stops
        final GradientPickerStop black = addStop(0.0, 1.0, 0.0, Color.BLACK);
        addStop(0.0, 1.0, 1.0, Color.WHITE);
        // Select first default stop
        setSelectedStop(black);
        proportional_checkbox.setSelected(true);
        proportional_checkbox.selectedProperty().addListener((ChangeListener<Boolean>) (ov, oldValue, newValue) -> {
            final PaintMode paintMode = paintPicker.getMode();
            final Paint value = getValue(paintMode);
            // Update UI
            preview_rect.setFill(value);
            // Update model
            paintPicker.setPaintProperty(value);
        });
        proportional_checkbox.setOnAction((ActionEvent event) -> {
            event.consume();
        });

        cycleMethod_choicebox.setItems(FXCollections.observableArrayList(CycleMethod.values()));
        cycleMethod_choicebox.getSelectionModel().selectFirst();
        cycleMethod_choicebox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<CycleMethod>) (ov, oldValue, newValue) -> {
            final PaintMode paintMode = paintPicker.getMode();
            final Paint value = getValue(paintMode);
            // Update UI
            preview_rect.setFill(value);
            // Update model
            paintPicker.setPaintProperty(value);
        });
        cycleMethod_choicebox.addEventHandler(ActionEvent.ACTION, (Event event) -> {
            event.consume();
        });

        final ChangeListener<Number> onValueChange = (ov, oldValue, newValue) -> {
            final PaintMode paintMode = paintPicker.getMode();
            final Paint value = getValue(paintMode);
            // Update UI
            preview_rect.setFill(value);
            // Update model
            paintPicker.setPaintProperty(value);
        };
        startX_slider.valueProperty().addListener(onValueChange);
        startY_slider.valueProperty().addListener(onValueChange);
        endX_slider.valueProperty().addListener(onValueChange);
        endY_slider.valueProperty().addListener(onValueChange);

        centerX_slider.valueProperty().addListener(onValueChange);
        centerY_slider.valueProperty().addListener(onValueChange);
        focusAngleRotator.rotationProperty().addListener(onValueChange);
        focusDistanceSlider.getSlider().valueProperty().addListener(onValueChange);
        radiusSlider.getSlider().valueProperty().addListener(onValueChange);

        radial_container.getChildren().addAll(radiusSlider, focusDistanceSlider, focusAngleRotator);
        radial_container.setVisible(false);
        radial_container.setManaged(false);

        final ChangeListener<Boolean> liveUpdateListener = (ov, oldValue, newValue) -> paintPicker.setLiveUpdate(newValue);
        startX_slider.pressedProperty().addListener(liveUpdateListener);
        startY_slider.pressedProperty().addListener(liveUpdateListener);
        endX_slider.pressedProperty().addListener(liveUpdateListener);
        endY_slider.pressedProperty().addListener(liveUpdateListener);
        centerX_slider.pressedProperty().addListener(liveUpdateListener);
        centerY_slider.pressedProperty().addListener(liveUpdateListener);
        radiusSlider.pressedProperty().addListener(liveUpdateListener);
        focusDistanceSlider.pressedProperty().addListener(liveUpdateListener);
        focusAngleRotator.pressedProperty().addListener(liveUpdateListener);
        slider_container.pressedProperty().addListener(liveUpdateListener);
    }

    @FXML
    void sliderPressed(MouseEvent event) {
        double percentH = ((100.0 / track_pane.getWidth()) * event.getX()) / 100;
        final Color color = paintPicker.getColorPicker().getValue();
        addStop(0.0, 1.0, percentH, color);
        final PaintMode paintMode = paintPicker.getMode();
        final Paint value = getValue(paintMode);
        // Update UI
        preview_rect.setFill(value);
        // Update model
        paintPicker.setPaintProperty(value);
    }

    @FXML
    void sliderDragged(MouseEvent event) {
        final PaintMode paintMode = paintPicker.getMode();
        final Paint value = getValue(paintMode);
        // Update UI
        preview_rect.setFill(value);
        // Update model
        paintPicker.setPaintProperty(value);
    }

    GradientPickerStop addStop(double min, double max, double value, Color color) {
        if (gradientPickerStops.size() < maxStops) {
            final GradientPickerStop gradientStop
                    = new GradientPickerStop(this, min, max, value, color);
            track_pane.getChildren().add(gradientStop);
            gradientPickerStops.add(gradientStop);
            return gradientStop;
        }
        return null;
    }

    void removeStop(GradientPickerStop gradientStop) {
        track_pane.getChildren().remove(gradientStop);
        gradientPickerStops.remove(gradientStop);
    }

    void removeAllStops() {
        track_pane.getChildren().clear();
        gradientPickerStops.clear();
    }

    public void setSelectedStop(GradientPickerStop gradientStop) {
        for (GradientPickerStop stop : gradientPickerStops) {
            stop.setSelected(false); // turn them all false
        }
        if (gradientStop != null) {
            gradientStop.setSelected(true);
        }
    }

    private List<Stop> getStops() {
        final List<Stop> stops = new ArrayList<>();
        for (GradientPickerStop ges : getGradientStops()) {
            final Stop stop = new Stop(ges.getOffset(), ges.getColor());
            stops.add(stop);
        }
        return stops;
    }
}
