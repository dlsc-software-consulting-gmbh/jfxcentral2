package com.dlsc.jfxcentral2.utilities.paintpicker;

import com.dlsc.jfxcentral2.components.TextCopyView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.ColorUtil;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.GradientView;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.NamedColorsView;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.PaintConvertUtil;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.PaintPicker;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.NamedColor;
import com.dlsc.jfxcentral2.utils.LOGGER;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GradientDesignerView extends FlowPane {
    private final PaintPicker paintPicker;

    private final List<NamedColor> namedColors;

    public GradientDesignerView(ObjectProperty<Size> sizeProperty) {
        getStyleClass().add("gradient-designer-view");

        namedColors = loadNamedColors();

        paintPicker = new PaintPicker(Color.web("#0e4ae4"));

        VBox additionalPane = new VBox();
        additionalPane.getStyleClass().add("additional-pane");

        TextCopyView copyView = new TextCopyView();
        copyView.getOptions().addAll("Java", "Css");
        copyView.setSelectedIndex(0);
        copyView.textProperty().bind(Bindings.createStringBinding(() -> {
            String selectedItem = copyView.getSelectedItem();
            Paint paint = paintPicker.paintProperty().get();
            if (paint instanceof Color color) {
                if ("Java".equals(selectedItem)) {
                    for (NamedColor namedColor : namedColors) {
                        if (namedColor.hex().equals(ColorUtil.colorToWebHex(color))) {
                            return "Color paint = Color." + namedColor.name().toUpperCase(Locale.ROOT) + ";";
                        }
                    }
                    return PaintConvertUtil.convertPaintToJavaCode(paint);
                } else {
                    for (NamedColor namedColor : namedColors) {
                        if (namedColor.hex().equals(ColorUtil.colorToWebHex(color))) {
                            return namedColor.name().toLowerCase(Locale.ROOT);
                        }
                    }
                    return PaintConvertUtil.convertPaintToCss(paint);
                }

            }

            if ("Java".equals(selectedItem)) {
                return PaintConvertUtil.convertPaintToJavaCode(paint);
            } else {

                return PaintConvertUtil.convertPaintToCss(paint);
            }
        }, copyView.selectedItemProperty(), paintPicker.paintProperty()));
        VBox.setVgrow(copyView, Priority.ALWAYS);


        //RecentView<Paint> recentPaintView = new RecentView<>();
        //recentPaintView.getStyleClass().add("recent-paint-view");

        Node splitComplementaryColorsBox = createSplitComplementaryColorsNode();
        splitComplementaryColorsBox.getStyleClass().add("split-complementary-colors-box");

        Node triadicHarmoniesBox = createTriadicHarmoniesNode();
        triadicHarmoniesBox.getStyleClass().add("triadic-harmonies-box");

        Node complementaryColorsBox = createComplementaryColor();
        complementaryColorsBox.getStyleClass().add("complementary-colors-box");

        Node monochromaticColorsBox = createMonochromaticColorsBox();
        monochromaticColorsBox.getStyleClass().add("monochromatic-colors-box");

        Node analogousColorsBox = createAnalogousColorsBox();
        analogousColorsBox.getStyleClass().add("analogous-colors-box");

        Node tetradicColorsBox = createTetradicColorsBox();
        tetradicColorsBox.getStyleClass().add("tetradic-colors-box");

        VBox detailsColorsBox = new VBox( complementaryColorsBox, splitComplementaryColorsBox, triadicHarmoniesBox, tetradicColorsBox, analogousColorsBox, monochromaticColorsBox);
        detailsColorsBox.getStyleClass().add("details-colors-box");
        detailsColorsBox.managedProperty().bind(detailsColorsBox.visibleProperty());
        detailsColorsBox.visibleProperty().bind(paintPicker.paintProperty().map(paint -> paint instanceof Color));

        GradientView gradientView = new GradientView(sizeProperty, paintPicker);
        gradientView.managedProperty().bind(gradientView.visibleProperty());
        gradientView.visibleProperty().bind(paintPicker.paintProperty().map(paint -> ! (paint instanceof Color)));

        additionalPane.getChildren().addAll(copyView, detailsColorsBox, gradientView);

        copyView.prefHeightProperty().bind(paintPicker.paintProperty().map(paint -> paint instanceof Color ? 130 : 280));
        copyView.minHeightProperty().bind(copyView.prefHeightProperty());
        copyView.maxHeightProperty().bind(copyView.prefHeightProperty());

        NamedColorsView namedColorsView = new NamedColorsView(namedColors, paintPicker::setPaintProperty);
        namedColorsView.managedProperty().bind(namedColorsView.visibleProperty());
        namedColorsView.visibleProperty().bind(paintPicker.paintProperty().map(paint -> paint instanceof Color));

        VBox leftBox = new VBox(paintPicker, namedColorsView);
        leftBox.getStyleClass().add("left-box");

        paintPicker.paintProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof Color) {
                this.getStyleClass().remove("gradient-mode");
            }else {
                this.getStyleClass().add("gradient-mode");
            }
        });



        getChildren().addAll(leftBox, additionalPane);
    }

    private Node createTetradicColorsBox() {
        ObjectBinding<List<Color>> binding = Bindings.createObjectBinding(() -> {
            Paint paint = paintPicker.getPaintProperty();
            if (paint instanceof Color color) {
                return reverseImmutableList(ColorUtil.tetradicColors(color));
            }
            return Collections.nCopies(4, Color.TRANSPARENT);
        }, paintPicker.paintProperty());
        return createColorsBox("Tetradic Colors", binding, 50, 30);
    }

    private Node createAnalogousColorsBox() {
        ObjectBinding<List<Color>> binding = Bindings.createObjectBinding(() -> {
            Paint paint = paintPicker.getPaintProperty();
            if (paint instanceof Color color) {
                return ColorUtil.generateAnalogousColors(color, 4);
            }
            return Collections.nCopies(5, Color.TRANSPARENT);
        }, paintPicker.paintProperty());

        return createColorsBox("Analogous Colors", binding, 50, 30);
    }

    private Node createMonochromaticColorsBox() {
        ObjectBinding<List<Color>> binding = Bindings.createObjectBinding(() -> {
            Paint paint = paintPicker.getPaintProperty();
            if (paint instanceof Color color) {
                return ColorUtil.generateMonochromaticColors(color, 10);
            }
            return Collections.nCopies(10, Color.TRANSPARENT);
        }, paintPicker.paintProperty());

        return createColorsBox("Monochromatic Colors", binding, 28, 28);
    }

    private Node createTriadicHarmoniesNode() {
        ObjectBinding<List<Color>> binding = Bindings.createObjectBinding(() -> {
            Paint paint = paintPicker.getPaintProperty();
            if (paint instanceof Color color) {
                return ColorUtil.triadicHarmonies(color);
            }
            return List.of(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
        }, paintPicker.paintProperty());

        return createColorsBox("Triadic Harmonies", binding);
    }

    private Node createSplitComplementaryColorsNode() {
        ObjectBinding<List<Color>> binding = Bindings.createObjectBinding(() -> {
            Paint paint = paintPicker.getPaintProperty();
            if (paint instanceof Color color) {
                return reverseImmutableList(ColorUtil.splitComplementaryColors(color));
            }
            return List.of(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
        }, paintPicker.paintProperty());

        return createColorsBox("Split Complementary Colors", binding);
    }

    private Node createComplementaryColor() {
        ObjectBinding<List<Color>> binding = Bindings.createObjectBinding(() -> {
            Paint paint = paintPicker.getPaintProperty();
            if (paint instanceof Color color) {
                return ColorUtil.complementaryColors(color);
            }
            return List.of(Color.TRANSPARENT, Color.TRANSPARENT);
        }, paintPicker.paintProperty());

        return createColorsBox("Complementary Colors", binding);
    }

    private VBox createColorsBox(String title, ObjectBinding<List<Color>> binding) {
        return createColorsBox(title, binding, 50, 30);
    }

    private VBox createColorsBox(String title, ObjectBinding<List<Color>> binding, double w, double h) {
        HBox colorsBox = new HBox();
        colorsBox.getStyleClass().add("colors-box");

        Region graphic = new Region();
        graphic.getStyleClass().add("graphic");
        Label label = new Label(title, graphic);
        label.getStyleClass().add("title");

        VBox colorsBoxWrapper = new VBox(label, colorsBox);
        colorsBoxWrapper.getStyleClass().add("colors-box-wrapper");

        int size = binding.get().size();
        for (int i = 0; i < size; i++) {
            Rectangle rectangle = createRectangle(w, h);
            int finalI = i;
            rectangle.fillProperty().bind(binding.map(list -> list.get(finalI)));
            colorsBox.getChildren().add(rectangle);
        }

        return colorsBoxWrapper;
    }

    private Rectangle createRectangle(double w, double h) {
        Rectangle rectangle = new Rectangle(w, h);
        rectangle.getStyleClass().addAll("color-rect");
        rectangle.setOnMouseClicked(e -> {
            if (e.isStillSincePress()) {
                Paint paint = rectangle.getFill();
                paintPicker.setPaintProperty(paint);
            }
        });
        return rectangle;
    }

    /**
     * Reverses the given unmodifiable list and returns an unmodifiable list.
     */
    public static <T> List<T> reverseImmutableList(List<T> original) {
        List<T> reversed = new ArrayList<>(original);
        Collections.reverse(reversed);
        return Collections.unmodifiableList(reversed);
    }

    private List<NamedColor> loadNamedColors() {
        String filePath = "/com/dlsc/jfxcentral2/utilities/paintpicker/javafx-named-colors.json";
        Type listType = new TypeToken<List<NamedColor>>() {
        }.getType();
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            return new Gson().fromJson(bufferedReader, listType);
        } catch (IOException e) {
            LOGGER.error("Failed to load named colors from " + filePath, e);
            return Collections.emptyList();
        }
    }

}
