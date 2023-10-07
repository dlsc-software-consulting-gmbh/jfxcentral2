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
package com.dlsc.jfxcentral2.utilities.paintpicker.impl;

import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.colorpicker.ColorPicker;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.PaintMode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 * Paint editor control.
 */
public class PaintPicker extends Pane {
    private VBox root_vbox;
    private ToggleButton colorToggleButton;
    private ToggleButton linearToggleButton;
    private ToggleButton radialToggleButton;

    private final PaintPickerController controller;

    public PaintPicker() {
        this((s, objects) -> {});
    }

    public PaintPicker(Paint paint) {
        this();
        setPaintProperty(paint);
    }


    public PaintPicker(Delegate delegate) {
        getStyleClass().add("paint-picker");
        ToggleGroup modeSwitch = new ToggleGroup();

        colorToggleButton = new CustomToggleButton("Color");
        colorToggleButton.getStyleClass().add("left-pill");
        HBox.setHgrow(colorToggleButton, Priority.ALWAYS);
        colorToggleButton.setSelected(true);

        linearToggleButton = new CustomToggleButton("Linear Gradient");
        linearToggleButton.getStyleClass().add("center-pill");
        HBox.setHgrow(linearToggleButton, Priority.ALWAYS);

        radialToggleButton = new CustomToggleButton("Radial Gradient");
        radialToggleButton.getStyleClass().add("right-pill");
        HBox.setHgrow(radialToggleButton, Priority.ALWAYS);

        modeSwitch.getToggles().addAll(colorToggleButton, linearToggleButton, radialToggleButton);

        HBox hBox = new HBox( colorToggleButton, linearToggleButton, radialToggleButton);
        hBox.setAlignment(Pos.CENTER_LEFT);

        root_vbox = new VBox(hBox);
        root_vbox.setAlignment(Pos.CENTER);
        root_vbox.setMinHeight(-1.0);
        root_vbox.setPrefHeight(-1.0);
        root_vbox.setPrefWidth(-1.0);
        root_vbox.setSpacing(5.0);
        getChildren().add(root_vbox);

        controller = new PaintPickerController(root_vbox, colorToggleButton, linearToggleButton, radialToggleButton);
        this.controller.setDelegate(delegate);
        controller.getColorPicker().getBottomPane().setPaintPickerController(controller);

        setMaxWidth(USE_PREF_SIZE);
        setMaxHeight(USE_PREF_SIZE);

        getStylesheets().add(PaintPicker.class.getResource("/com/dlsc/jfxcentral2/utilities/paintpicker/paint-picker.css").toExternalForm());
    }

    public PaintPicker(Delegate delegate, PaintMode paintMode) {
        this(delegate);
        controller.setSingleMode(paintMode);
    }



    public final ObjectProperty<Paint> paintProperty() {
        return controller.paintProperty();
    }

    public final void setPaintProperty(Paint value) {
        // Update model
        controller.setPaintProperty(value);
        // Update UI
        controller.updateUI(value);
    }

    public final Paint getPaintProperty() {
        return controller.getPaintProperty();
    }

    public final ReadOnlyBooleanProperty liveUpdateProperty() {
        return controller.liveUpdateProperty();
    }

    public boolean isLiveUpdate() {
        return controller.isLiveUpdate();
    }

    public static interface Delegate {
        public void handleError(String warningKey, Object... arguments);
    }

    public ColorPicker getColorPicker() {
        return controller.getColorPicker();
    }

}
