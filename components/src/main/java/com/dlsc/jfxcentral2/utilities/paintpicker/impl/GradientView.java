package com.dlsc.jfxcentral2.utilities.paintpicker.impl;

import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.Gradient;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.LinearGradientPOJO;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.StopPOJO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GradientView extends VBox {
    private static final Logger LOGGER = LogManager.getLogger(GradientView.class);

    public GradientView(ObjectProperty<Size> sizeObjectProperty, PaintPicker paintPicker) {
        getStyleClass().add("linear-gradient-view");

        Label title = new Label();
        title.getStyleClass().add("title");
        title.textProperty().bind(paintPicker.paintProperty().map(paint -> paint instanceof LinearGradient ? "Linear Gradient" : "Radial Gradient"));

        FlowPane flowPane = new FlowPane();
        flowPane.getStyleClass().add("flow-pane");

        List<Gradient> linearGradients = loadLinearGradients();
        for (Gradient linearGradient : linearGradients) {
            Rectangle rectangle = new Rectangle(70, 32);
            rectangle.widthProperty().bind(sizeObjectProperty.map(size -> size.isSmall() ? 52 : 68));

            rectangle.widthProperty().bind(Bindings.createDoubleBinding(() -> {
                Paint paint = paintPicker.paintProperty().get();
                Size size = sizeObjectProperty.get();

                if (!(paint instanceof RadialGradient)) {
                    return size.isSmall() ? 52d : 68d;
                }
                return size.isSmall() ? 30d : 55d;

            }, sizeObjectProperty, paintPicker.paintProperty()));

            rectangle.heightProperty().bind(Bindings.createDoubleBinding(() -> {
                Paint paint = paintPicker.paintProperty().get();
                Size size = sizeObjectProperty.get();

                if (paint instanceof RadialGradient) {
                    return size.isSmall() ? 32d : 55d;
                }
                return 32d;
            }, sizeObjectProperty, paintPicker.paintProperty()));

            rectangle.getStyleClass().add("color-rect");
            rectangle.managedProperty().bind(rectangle.visibleProperty());
            rectangle.fillProperty().bind(paintPicker.paintProperty().map(paint -> paint instanceof LinearGradient ? linearGradient.linearGradient() : linearGradient.radialGradient()));
            rectangle.setOnMouseClicked(event -> paintPicker.setPaintProperty(rectangle.getFill()));
            flowPane.getChildren().add(rectangle);
        }

        getChildren().addAll(title, flowPane);

    }

    public List<Gradient> loadLinearGradients() {
        List<LinearGradientPOJO> pojos = loadLinearGradientPOJOs();
        return pojos.stream()
                .map(pojo -> new Gradient(convertToLinearGradient(pojo), convertToRadialGradient(pojo)))
                .collect(Collectors.toList());
    }

    private List<LinearGradientPOJO> loadLinearGradientPOJOs() {
        String filePath = "/com/dlsc/jfxcentral2/utilities/paintpicker/linear-gradient-colors.json";
        Type listType = new TypeToken<List<LinearGradientPOJO>>() {
        }.getType();
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            return new Gson().fromJson(bufferedReader, listType);
        } catch (IOException e) {
            LOGGER.error("Error loading linear gradient colors", e);
            return Collections.emptyList();
        }
    }

    private LinearGradient convertToLinearGradient(LinearGradientPOJO pojo) {
        List<Stop> stops = pojo.stops().stream()
                .map(this::convertToStop)
                .collect(Collectors.toList());
        return new LinearGradient(
                pojo.startX(), pojo.startY(), pojo.endX(), pojo.endY(),
                pojo.proportional(), CycleMethod.valueOf(pojo.cycleMethod()), stops
        );
    }

    private RadialGradient convertToRadialGradient(LinearGradientPOJO pojo) {
        List<Stop> stops = pojo.stops().stream()
                .map(this::convertToStop)
                .collect(Collectors.toList());
        return new RadialGradient(0.0, 0.0, 0.5, 0.5, 0.5,
                pojo.proportional(), CycleMethod.valueOf(pojo.cycleMethod()), stops);
    }

    private Stop convertToStop(StopPOJO pojo) {
        Color color = new Color(pojo.color().red(), pojo.color().green(), pojo.color().blue(), pojo.color().opacity());
        return new Stop(pojo.offset(), color);
    }

}
