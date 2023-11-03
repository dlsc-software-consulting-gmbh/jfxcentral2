package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.components.FileHandlerView;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.PaintPicker;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.NumberUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import javafx.util.StringConverter;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.jam.Jam;
import org.kordamp.ikonli.javafx.FontIcon;

public class EffectPropertyFactory {
    /**
     * Light property, This value can be freely adjusted,
     * but please be mindful that if the range is unreasonable,the affected area may be too large or too small.
     */
    private static final int LIGHT_PROPERTY_MAX_VALUE = 350;
    private static final double IMAGE_INPUT_REQUESTED_SIZE = 200;


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
        Slider slider = createSlider(min, max, current);
        slider.valueProperty().addListener((ob, ov, nv) -> valueProperty.set(nv.doubleValue()));

        Label valueLabel = createValueLabel();
        valueLabel.textProperty().bind(Bindings.createObjectBinding(() -> {
            double value = valueProperty.get();
            slider.setValue(value);
            return NumberUtil.trimTrailingZeros(value);
        }, valueProperty));
        HBox sliderBox = new HBox(10, slider, valueLabel);
        sliderBox.getStyleClass().add("slider-box");
        return new EffectPropperty(title, ikon, sliderBox);
    }

    private static Label createValueLabel() {
        Label valueLabel = new Label();
        valueLabel.getStyleClass().add("value-label");
        return valueLabel;
    }

    private static Slider createSlider(double min, double max, double current) {
        Slider slider = new Slider(min, max, current);
        slider.setFocusTraversable(false);
        slider.setMajorTickUnit(max);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        return slider;
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
        PaintPicker colorPicker = createColorPicker(valueProperty);
        colorPicker.paintProperty().addListener((ob, ov, nv) ->
                valueProperty.set(nv instanceof Color color ? color : Color.TRANSPARENT));
        return new EffectPropperty(title, ikon, colorPicker);
    }

    private static PaintPicker createColorPicker(ObjectProperty<Color> valueProperty) {
        PaintPicker colorPicker = PaintPicker.createColorPicker();
        colorPicker.setFocusTraversable(false);
        colorPicker.setPaintProperty(valueProperty.get());
        return colorPicker;
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
        fileNameField.textProperty().bind(valueProperty.map(image -> {
            if (image == null || image.getUrl() == null) {
                return "";
            }
            String url = image.getUrl();
            return url.substring(url.lastIndexOf("/") + 1);
        }));

        FileHandlerView fileHandlerView = new FileHandlerView(false, false, true);
        fileHandlerView.getSupportedExtensions().addAll(".png", ".jpg", ".jpeg", ".gif");
        fileHandlerView.setText("Click or drop Image file here");
        fileHandlerView.setOnUploadedFile(file -> {
            Image image = new Image(file.toURI().toString(), IMAGE_INPUT_REQUESTED_SIZE, IMAGE_INPUT_REQUESTED_SIZE, true, true);
            valueProperty.set(image);
        });

        VBox fileBox = new VBox(fileNameField, fileHandlerView);
        fileBox.getStyleClass().add("file-box");
        return new EffectPropperty(title, ikon, fileBox);
    }

    public static EffectPropperty createLightEditor(Lighting effect) {
        String[] lightBoxStyleClazz = {"distant", "point", "spot", "none"};

        StackPane lightPaneWrapper = new StackPane();
        lightPaneWrapper.getStyleClass().add("light-pane-wrapper");

        ComboBox<String> comboBox = createLightModeBox(effect.lightProperty());
        lightModeHandler(effect, comboBox.getSelectionModel().getSelectedItem(), lightPaneWrapper, comboBox);
        comboBox.addEventFilter(MouseEvent.ANY, event -> lightPaneWrapper.requestFocus());
        comboBox.getSelectionModel().selectedItemProperty().addListener((ob, ov, selectedItem) -> {
            lightModeHandler(effect, selectedItem, lightPaneWrapper, comboBox);
        });

        VBox lightBox = new VBox(comboBox, lightPaneWrapper);
        lightBox.getStyleClass().add("light-box");
        lightBox.getStyleClass().add(lightBoxStyleClazz[comboBox.getSelectionModel().getSelectedIndex()]);
        comboBox.getSelectionModel().selectedIndexProperty().addListener((ob, ov, nv) -> {
            lightBox.getStyleClass().removeAll(lightBoxStyleClazz);
            lightBox.getStyleClass().add(lightBoxStyleClazz[nv.intValue()]);
        });

        return new EffectPropperty("Light", Jam.FLASHLIGHT_ON, lightBox);
    }

    private static void lightModeHandler(Lighting effect, String selectedLightMode, StackPane lightPaneWrapper, ComboBox<String> comboBox) {
        switch (selectedLightMode) {
            case "Distant Light" -> {
                Pair<GridPane, ObjectProperty<Light>> distantLightPane = createDistantLightPane();
                lightPaneWrapper.getChildren().setAll(distantLightPane.getKey());
                if (effect.lightProperty().isBound()) {
                    effect.lightProperty().unbind();
                }
                effect.lightProperty().bind(distantLightPane.getValue());
            }
            case "Point Light" -> {
                Pair<GridPane, ObjectProperty<Light>> pointLightPane = createPointLightPane();
                lightPaneWrapper.getChildren().setAll(pointLightPane.getKey());
                if (effect.lightProperty().isBound()) {
                    effect.lightProperty().unbind();
                }
                effect.lightProperty().bind(pointLightPane.getValue());
            }
            case "Spot Light" -> {
                Pair<GridPane, ObjectProperty<Light.Spot>> spotLightPane = createSpotLightPane();
                lightPaneWrapper.getChildren().setAll(spotLightPane.getKey());
                if (effect.lightProperty().isBound()) {
                    effect.lightProperty().unbind();
                }
                effect.lightProperty().bind(spotLightPane.getValue());
            }
            default -> {
                lightPaneWrapper.getChildren().clear();
                if (effect.lightProperty().isBound()) {
                    effect.lightProperty().unbind();
                }
                effect.lightProperty().set(null);
            }
        }
    }

    private static ComboBox<String> createLightModeBox(ObjectProperty<Light> valueProperty) {
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("Distant Light", "Point Light", "Spot Light", "None"));
        comboBox.getStyleClass().add("md-combo-box");
        comboBox.setFocusTraversable(false);

        if (valueProperty.get() instanceof Light.Distant) {
            comboBox.getSelectionModel().select("Distant Light");
        } else if (valueProperty.get() instanceof Light.Point) {
            comboBox.getSelectionModel().select("Point Light");
        } else if (valueProperty.get() instanceof Light.Spot) {
            comboBox.getSelectionModel().select("Spot Light");
        } else {
            comboBox.getSelectionModel().select("None");
        }
        return comboBox;
    }

    private static void createSliderWithLabel(Slider slider, ObservableValue<String> labelProperty, Ikon iconType, String iconText, GridPane lightPane, int row) {
        Label label = new Label(iconText, new FontIcon(iconType));
        lightPane.add(label, 0, row);

        Label valueLabel = createValueLabel();
        valueLabel.textProperty().bind(labelProperty);

        HBox sliderBox = new HBox(10, slider, valueLabel);
        sliderBox.getStyleClass().add("slider-box");
        lightPane.add(sliderBox, 1, row);

    }

    private static void createCommonColorPicker(ObjectProperty<? extends Light> lightProperty, GridPane lightPane, int row) {
        PaintPicker colorPicker = createColorPicker(lightProperty.get().colorProperty());
        colorPicker.paintProperty().addListener((ob, ov, nv) -> {
            if (nv instanceof Color color) {
                lightProperty.get().setColor(color);
            }
        });
        lightPane.add(colorPicker, 0, row, 2, 1);
    }

    private static Pair<GridPane, ObjectProperty<Light>> createPointLightPane() {
        Light.Point point = new Light.Point();
        ObjectProperty<Light> valueProperty = new SimpleObjectProperty<>(point);

        GridPane lightPane = new GridPane();
        lightPane.getStyleClass().add("light-pane");

        Slider xSlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        xSlider.valueProperty().bindBidirectional(point.xProperty());
        createSliderWithLabel(xSlider, point.xProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.x, "X", lightPane, 0);

        Slider ySlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        createSliderWithLabel(ySlider, point.yProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.y, "Y", lightPane, 1);
        ySlider.valueProperty().bindBidirectional(point.yProperty());

        Slider zSlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        createSliderWithLabel(zSlider, point.zProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.z, "Z", lightPane, 2);
        zSlider.valueProperty().bindBidirectional(point.zProperty());

        createCommonColorPicker(valueProperty, lightPane, 3);

        return new Pair<>(lightPane, valueProperty);
    }

    private static Pair<GridPane, ObjectProperty<Light>> createDistantLightPane() {
        Light.Distant distant = new Light.Distant();
        ObjectProperty<Light> valueProperty = new SimpleObjectProperty<>(distant);

        GridPane lightPane = new GridPane();
        lightPane.getStyleClass().add("light-pane");

        Slider azimuthSlider = createSlider(0, 360, 45);
        createSliderWithLabel(azimuthSlider, distant.azimuthProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.azimuth, "Azimuth", lightPane, 0);
        azimuthSlider.valueProperty().bindBidirectional(distant.azimuthProperty());

        Slider elevationSlider = createSlider(0, 360, 45);
        createSliderWithLabel(elevationSlider, distant.elevationProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.elevation, "Elevation", lightPane, 1);
        elevationSlider.valueProperty().bindBidirectional(distant.elevationProperty());

        createCommonColorPicker(valueProperty, lightPane, 2);

        return new Pair<>(lightPane, valueProperty);
    }

    private static Pair<GridPane, ObjectProperty<Light.Spot>> createSpotLightPane() {
        Light.Spot spot = new Light.Spot();
        ObjectProperty<Light.Spot> valueProperty = new SimpleObjectProperty<>(spot);

        GridPane lightPane = new GridPane();
        lightPane.getStyleClass().add("light-pane");

        Slider xSlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        createSliderWithLabel(xSlider, spot.xProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.x, "X", lightPane, 0);
        xSlider.valueProperty().bindBidirectional(spot.xProperty());

        Slider ySlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        ySlider.valueProperty().bindBidirectional(spot.yProperty());
        createSliderWithLabel(ySlider, spot.yProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.y, "Y", lightPane, 1);

        Slider zSlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        zSlider.valueProperty().bindBidirectional(spot.zProperty());
        createSliderWithLabel(zSlider, spot.zProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.z, "Z", lightPane, 2);

        Slider positionAtXSlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        createSliderWithLabel(positionAtXSlider, spot.pointsAtXProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.x, "Points\nAt X", lightPane, 3);
        positionAtXSlider.valueProperty().bindBidirectional(spot.pointsAtXProperty());

        Slider positionAtYSlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        createSliderWithLabel(positionAtYSlider, spot.pointsAtYProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.x, "Points\nAt Y", lightPane, 4);
        positionAtYSlider.valueProperty().bindBidirectional(spot.pointsAtYProperty());

        Slider positionAtZSlider = createSlider(-LIGHT_PROPERTY_MAX_VALUE, LIGHT_PROPERTY_MAX_VALUE, 0);
        createSliderWithLabel(positionAtZSlider, spot.pointsAtZProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.x, "Points\nAt Z", lightPane, 5);
        positionAtZSlider.valueProperty().bindBidirectional(spot.pointsAtZProperty());

        Slider specularExponentSlider = createSlider(0, 4, 1);
        createSliderWithLabel(specularExponentSlider, spot.specularExponentProperty().map(value -> NumberUtil.trimTrailingZeros(value.doubleValue())), IkonUtil.elevation, "Specular\nExponent", lightPane, 6);
        specularExponentSlider.valueProperty().bindBidirectional(spot.specularExponentProperty());

        createCommonColorPicker(valueProperty, lightPane, 7);

        return new Pair<>(lightPane, valueProperty);
    }

}
