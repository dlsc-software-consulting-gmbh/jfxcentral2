package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.components.FileHandlerView;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.PaintPicker;
import com.dlsc.jfxcentral2.utils.NumberUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import org.kordamp.ikonli.Ikon;

public class EffectPropertyFactory {

    public static <T extends Enum<T>> EffectPropperty create(String title, Ikon ikon, Class<T> enumClass, StringConverter<T> stringConverter, ObjectProperty<T> valueProperty) {
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.getStyleClass().add("md-combo-box");
        comboBox.setFocusTraversable(false);
        comboBox.setConverter(stringConverter);
        comboBox.getItems().addAll(enumClass.getEnumConstants());
        comboBox.getSelectionModel().select(valueProperty.get());
        comboBox.valueProperty().addListener((ob, ov, nv) -> valueProperty.set(nv));

        return new EffectPropperty(title, ikon, comboBox);
    }

    public static EffectPropperty create(String title, Ikon ikon, double min, double max, double current, DoubleProperty valueProperty) {
        Slider slider = new Slider(min, max, current);
        slider.setFocusTraversable(false);
        slider.setMajorTickUnit(max);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener((ob, ov, nv) -> valueProperty.set(nv.doubleValue()));
        Label valueLabel = new Label();
        valueLabel.getStyleClass().add("value-label");
        valueLabel.textProperty().bind(Bindings.createObjectBinding(() -> {
            double value = valueProperty.get();
            slider.setValue(value);
            return NumberUtil.trimTrailingZeros(value);
        }, valueProperty));
        HBox sliderBox = new HBox(10, slider, valueLabel);
        sliderBox.getStyleClass().add("slider-box");
        return new EffectPropperty(title, ikon, sliderBox);
    }

    public static EffectPropperty create(String title, Ikon ikon, int min, int max, int current, IntegerProperty valueProperty) {
        Spinner<Integer> spinner = new Spinner<>(min, max, current);
        spinner.valueProperty().addListener((ob, ov, nv) -> valueProperty.set(nv));
        spinner.setFocusTraversable(false);
        return new EffectPropperty(title, ikon, spinner);
    }

    public static EffectPropperty create(String title, Ikon ikon, BooleanProperty valueProperty) {
        CheckBox checkBox = new CheckBox();
        checkBox.setFocusTraversable(false);
        checkBox.selectedProperty().bindBidirectional(valueProperty);
        return new EffectPropperty(title, ikon, checkBox);
    }

    public static EffectPropperty createWithColorPicker(String title, Ikon ikon, ObjectProperty<Color> valueProperty) {
        PaintPicker colorPicker = PaintPicker.createColorPicker();
        colorPicker.setFocusTraversable(false);
        colorPicker.setPaintProperty(valueProperty.get());
        colorPicker.paintProperty().addListener((ob, ov, nv) ->
                valueProperty.set(nv instanceof Color color ? color : Color.TRANSPARENT));
        return new EffectPropperty(title, ikon, colorPicker);
    }

    public static EffectPropperty createWithPaintPicker(String title, Ikon ikon, ObjectProperty<Paint> valueProperty) {
        PaintPicker paintPicker = new PaintPicker();
        paintPicker.setFocusTraversable(false);
        paintPicker.setPaintProperty(valueProperty.get());
        paintPicker.paintProperty().addListener((ob, ov, nv) -> valueProperty.set(nv));
        return new EffectPropperty(title, ikon, paintPicker);
    }

    public static EffectPropperty create(String title, Ikon ikon, ObjectProperty<Image> valueProperty) {
        TextField fileNameField = new TextField();
        fileNameField.getStyleClass().add("file-name-label");
        fileNameField.setEditable(false);
        fileNameField.setFocusTraversable(false);
        fileNameField.managedProperty().bind(fileNameField.visibleProperty());
        fileNameField.visibleProperty().bind(valueProperty.isNotNull());

        FileHandlerView fileHandlerView = new FileHandlerView(false, false, true);
        fileHandlerView.getSupportedExtensions().addAll(".png", ".jpg", ".jpeg", ".gif");
        fileHandlerView.setText("Click or drop Image file here");
        fileHandlerView.setOnUploadedFile(file -> {
            valueProperty.set(new Image(file.toURI().toString()));
            fileNameField.setText(file.getName());
        });

        VBox fileBox = new VBox(fileNameField, fileHandlerView);
        fileBox.getStyleClass().add("file-box");
        return new EffectPropperty(title, ikon, fileBox);
    }

}
