/*
 * Copyright (c) 2016, Gluon and/or its affiliates.
 * Copyright (c) 2012, 2015, Oracle and/or its affiliates.
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

import com.dlsc.jfxcentral2.components.DoubleTextField;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.PaintMode;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.PaintPickerController;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.colorpicker.ColorPicker;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * Controller class for the gradient editor stop.
 */
public class GradientPickerStop extends VBox {

    protected final Button stop_button;
    protected final ContextMenu context_menu;
    protected final CustomMenuItem custom_menu_item;
    protected final DoubleTextField offset_textfield;
    protected final StackPane stackPane;
    protected final Rectangle rectangle;
    protected final Rectangle chip_rect;
    protected final ImageView indicator_image;

    private final double min;
    private final double max;
    private double offset;
    private Color color;
    private boolean isSelected;
    private double origX;
    private double startDragX;
    private double thumbWidth;
    private final double edgeMargin = 2.0;
    private final GradientPicker gradientPicker;



    /*
     * Clamp value to be between min and max.
     */
    private static double clamp(double min, double value, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public GradientPickerStop(GradientPicker ge, double mini, double maxi, double val, Color c) {
        gradientPicker = ge;
        min = mini;
        max = maxi;
        offset = val;
        color = c;


        //初始化组件
        stop_button = new Button();
        context_menu = new ContextMenu();
        custom_menu_item = new CustomMenuItem();
        offset_textfield = new DoubleTextField();
        stackPane = new StackPane();
        rectangle = new Rectangle();
        chip_rect = new Rectangle();
        indicator_image = new ImageView();

        stop_button.setMnemonicParsing(false);
        stop_button.setOnKeyPressed(this::thumbKeyPressed);
        stop_button.setOnMouseDragged(this::thumbMouseDragged);
        stop_button.setOnMousePressed(this::thumbMousePressed);
        stop_button.setOnMouseReleased(this::thumbMouseReleased);
        stop_button.getStyleClass().add("gradient-stop-thumb");
        stop_button.setText("");

        context_menu.setAutoHide(false);

        custom_menu_item.setHideOnClick(false);
        custom_menu_item.setMnemonicParsing(false);
        custom_menu_item.setText("Unspecified Action");

        custom_menu_item.setContent(offset_textfield);
        stop_button.setContextMenu(context_menu);

        rectangle.setArcHeight(0.0);
        rectangle.setArcWidth(0.0);
        rectangle.setFill(Color.WHITE);
        rectangle.setHeight(12.0);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeType(StrokeType.INSIDE);
        rectangle.setStrokeWidth(1.0);
        rectangle.getStyleClass().add("gradient-stop-chip");
        rectangle.setWidth(12.0);

        chip_rect.setArcHeight(0.0);
        chip_rect.setArcWidth(0.0);
        chip_rect.setFill(Color.DODGERBLUE);
        chip_rect.setHeight(8.0);
        chip_rect.setStroke(Color.BLACK);
        chip_rect.setStrokeType(StrokeType.INSIDE);
        chip_rect.setStrokeWidth(0.0);
        chip_rect.setWidth(8.0);
        //stackPane.setPadding(new Insets(2.0));
        stop_button.setGraphic(stackPane);

        indicator_image.setImage(new Image(getClass().getResource("/com/dlsc/jfxcentral2/utilities/paintpicker/stop-indicator.png").toExternalForm()));

        context_menu.getItems().add(custom_menu_item);
        stackPane.getChildren().add(rectangle);
        stackPane.getChildren().add(chip_rect);
        getChildren().add(stop_button);
        getChildren().add(indicator_image);
        initialize();
    }

    public void setOffset(double val) {
        offset = clamp(min, val, max);
        valueToPixels();
    }

    public double getOffset() {
        return offset;
    }

    public void setColor(Color c) {
        color = c;
        chip_rect.setFill(c);
    }

    public Color getColor() {
        return color;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if (selected) {
            indicator_image.setVisible(true);
        } else {
            indicator_image.setVisible(false);
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    private void initialize() {
        //
        //final FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(GradientPickerStop.class.getResource("GradientPickerStop.fxml")); //NOI18N
        //loader.setController(this);
        //loader.setRoot(this);
        //try {
        //    loader.load();
        //} catch (IOException ex) {
        //    Logger.getLogger(GradientPicker.class.getName()).log(Level.SEVERE, null, ex);
        //}

        assert offset_textfield != null;
        assert chip_rect != null;

        offset_textfield.setText("" + offset); //NOI18N

        chip_rect.setFill(color);
        gradientPicker.setSelectedStop(this);
        
        stop_button.setOnAction((ActionEvent event) -> {
            event.consume();
        });

        // when we detect a width change, we know node layout is resolved so we position stop in track
        widthProperty().addListener((ChangeListener<Number>) (ov, oldValue, newValue) -> {
            if (newValue.doubleValue() > 0) {
                thumbWidth = newValue.doubleValue();
                valueToPixels();
            }
        });
    }
    
    @FXML
    void stopAction(ActionEvent event) {
        double val = Double.valueOf(offset_textfield.getText());
        setOffset(val);
        showHUD();
        // Called when moving a gradient stop :
        // - update gradient preview accordingly
        // - update model
        final PaintPickerController paintPicker
                = gradientPicker.getPaintPickerController();
        final PaintMode paintMode = paintPicker.getMode();
        final Paint value = gradientPicker.getValue(paintMode);
        gradientPicker.updatePreview(value);
        // Update model
        paintPicker.setPaintProperty(value);
    }

    @FXML
    void thumbKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.DELETE) {
            gradientPicker.removeStop(this);
            // Called when removing a gradient stop :
            // - update gradient preview accordingly
            // - update model
            final PaintPickerController paintPicker
                    = gradientPicker.getPaintPickerController();
            final PaintMode paintMode = paintPicker.getMode();
            final Paint value = gradientPicker.getValue(paintMode);
            gradientPicker.updatePreview(value);
            // Update model
            paintPicker.setPaintProperty(value);
            e.consume();
        }
    }

    @FXML
    void thumbMousePressed(MouseEvent event) {
        gradientPicker.setSelectedStop(this);
        startDragX = event.getSceneX();
        origX = getLayoutX();
        toFront(); // make sure this stop is in highest z-order
//        showHUD();
        pixelsToValue();
        // Called when selecting a gradient stop :
        // - update color preview accordingly
        // - do not update the model
        final PaintPickerController paintPicker
                = gradientPicker.getPaintPickerController();
        final ColorPicker colorPicker = paintPicker.getColorPicker();
        colorPicker.updateUI(color);
        stop_button.requestFocus();
    }

    @FXML
    void thumbMouseReleased(MouseEvent event) {
        pixelsToValue();
    }

    @FXML
    void thumbMouseDragged(MouseEvent event) {
        double dragValue = event.getSceneX() - startDragX;
        double deltaX = origX + dragValue;
        double trackWidth = getParent().getBoundsInLocal().getWidth();
        final Double newX = clamp(edgeMargin, deltaX, (trackWidth - (getWidth() + edgeMargin)));
        setLayoutX(newX);
//        showHUD();
        pixelsToValue();
        // Called when moving a gradient stop :
        // - update gradient preview accordingly
        // - update model
        final PaintPickerController paintPicker
                = gradientPicker.getPaintPickerController();
        final PaintMode paintMode = paintPicker.getMode();
        final Paint value = gradientPicker.getValue(paintMode);
        gradientPicker.updatePreview(value);
        // Update model
        paintPicker.setPaintProperty(value);
    }

    private void showHUD() {
        offset_textfield.setText(Double.toString(offset));
        context_menu.show(this, Side.BOTTOM, 0, 5); // better way to center?
    }

    private void valueToPixels() {
        double stopValue = clamp(min, offset, max);
        double availablePixels = getParent().getLayoutBounds().getWidth() - (thumbWidth + edgeMargin);
        double range = max - min;
        double pixelPosition = ((availablePixels / range) * stopValue);
        setLayoutX(pixelPosition);
    }

    private void pixelsToValue() {
        double range = max - min;
        double availablePixels = getParent().getLayoutBounds().getWidth() - (thumbWidth + edgeMargin);
        setOffset(min + (getLayoutX() * (range / availablePixels)));
    }
}
