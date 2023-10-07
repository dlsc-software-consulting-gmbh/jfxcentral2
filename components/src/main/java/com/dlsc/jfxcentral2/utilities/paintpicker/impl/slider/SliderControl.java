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


package com.dlsc.jfxcentral2.utilities.paintpicker.impl.slider;

import com.dlsc.jfxcentral2.components.DoubleTextField;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SliderControl extends GridPane {

    protected final Label slider_label;
    protected final DoubleTextField slider_textfield;
    protected final Slider slider_slider;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;

    private final int roundingFactor = 100; // 2 decimals rounding

    public SliderControl(String text, double min, double max, double initVal) {
        slider_label = new Label();
        slider_textfield = new DoubleTextField();
        slider_slider = new Slider();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();

        setId("GridPane");

        GridPane.setColumnIndex(slider_label, 0);
        GridPane.setRowIndex(slider_label, 0);
        slider_label.setText("Label");

        GridPane.setColumnIndex(slider_textfield, 1);
        GridPane.setRowIndex(slider_textfield, 0);
        slider_textfield.setOnKeyPressed(this::sliderKeyPressed);

        GridPane.setColumnIndex(slider_slider, 2);
        GridPane.setRowIndex(slider_slider, 0);
        slider_slider.setMax(1.0);
        slider_slider.setPrefWidth(100.0);
        slider_slider.setValue(0.5);

        columnConstraints.setHalignment(javafx.geometry.HPos.RIGHT);
        columnConstraints.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPercentWidth(40.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints0.setMinWidth(-1.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.NEVER);
        columnConstraints1.setMinWidth(-1.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        getChildren().add(slider_label);
        getChildren().add(slider_textfield);
        getChildren().add(slider_slider);
        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getColumnConstraints().add(columnConstraints1);
        getRowConstraints().add(rowConstraints);

        //事件

        //初始化
        initialize(text, min, max, initVal);
    }

    public final Slider getSlider() {
        return slider_slider;
    }

    /**
     * Private
     */
    private void initialize(String text, double min, double max, double initVal) {


        assert slider_label != null;
        assert slider_slider != null;
        assert slider_textfield != null;

        slider_label.setText(text);
        slider_slider.setMin(min);
        slider_slider.setMax(max);
        slider_slider.setValue(initVal);
        slider_textfield.setText(Double.toString(initVal));

        slider_slider.valueProperty().addListener((ChangeListener<Number>) (ov, oldValue, newValue) -> {
            double rounded = round(newValue.doubleValue(), roundingFactor);
            slider_textfield.setText(Double.toString(rounded));
        });
    }

    @FXML
    void sliderAction(ActionEvent event) {
        double value = Double.parseDouble(slider_textfield.getText());
        double rounded = round(value, roundingFactor);
        slider_slider.setValue(rounded);
        if (rounded > slider_slider.getMax()) {
            rounded = slider_slider.getMax();
            slider_textfield.setText(Double.toString(rounded));
        }
        if (rounded < slider_slider.getMin()) {
            rounded = slider_slider.getMin();
            slider_textfield.setText(Double.toString(rounded));
        }
        slider_textfield.selectAll();
        event.consume();
    }

    @FXML
    void sliderKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case UP:
                incOrDecFieldValue(e, 0.1);
                break;
            case DOWN:
                incOrDecFieldValue(e, -0.1);
                break;
            default:
                break;
        }
    }

    private void incOrDecFieldValue(KeyEvent e, double x) {

        if (!(e.getSource() instanceof TextField)) {
            return; // check it's a textField
        }        // increment or decrement the value
        final TextField tf = (TextField) e.getSource();
        final Double newValue = Double.valueOf(tf.getText()) + x;
        double rounded = round(newValue, roundingFactor);
        slider_slider.setValue(rounded);
        tf.setText(Double.toString(newValue));
        // Avoid using runLater
        // This should be done somewhere else (need to investigate)
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                // position caret after new value for easy editing
//                tf.positionCaret(tf.getText().length());
//            }
//        });
    }

    private double round(double value, int roundingFactor) {
        double doubleRounded = Math.round(value * roundingFactor);
        return doubleRounded / roundingFactor;
    }
}
