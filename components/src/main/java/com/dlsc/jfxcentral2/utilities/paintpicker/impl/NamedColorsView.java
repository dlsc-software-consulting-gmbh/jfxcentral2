package com.dlsc.jfxcentral2.utilities.paintpicker.impl;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.NamedColor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Consumer;

public class NamedColorsView extends VBox {
    public NamedColorsView(List<NamedColor> namedColors, Consumer<Color> colorConsumer) {
        getStyleClass().add("named-colors-view");

        Label title = new Label("Named Colors");
        TilePane tilePane = new TilePane();
        tilePane.getStyleClass().add("tile-pane");
        for (NamedColor namedColor : namedColors) {
            Rectangle rectangle = new Rectangle(18, 18);
            rectangle.setUserData(namedColor);
            rectangle.getStyleClass().add("color-rect");
            Color color = Color.web(namedColor.hex());
            rectangle.setFill(color);
            rectangle.managedProperty().bind(rectangle.visibleProperty());
            rectangle.setOnMouseClicked(event -> {
                if (event.isStillSincePress()) {
                    colorConsumer.accept(color);
                }
            });
            tilePane.getChildren().add(rectangle);
        }

        SearchTextField searchField = new SearchTextField();
        searchField.setPromptText("Search color");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String result = StringUtils.trimToEmpty(newValue);
            if (StringUtils.isBlank(result)) {
                for (NamedColor namedColor : namedColors) {
                    Rectangle rectangle = (Rectangle) tilePane.getChildren().get(namedColors.indexOf(namedColor));
                    rectangle.setVisible(true);
                }
                return;
            }

            if (result.startsWith("#")){
                tilePane.getChildren().forEach(node -> {
                    Rectangle rectangle = (Rectangle) node;
                    rectangle.setVisible(StringUtils.containsIgnoreCase(((NamedColor)rectangle.getUserData()).hex(), result));
                });
            }else {
                tilePane.getChildren().forEach(node -> {
                    Rectangle rectangle = (Rectangle) node;
                    rectangle.setVisible(StringUtils.containsIgnoreCase(((NamedColor)rectangle.getUserData()).name(), result));
                });
            }
        });

        HBox topBox = new HBox(title,searchField);
        topBox.getStyleClass().add("top-box");
        getChildren().addAll(topBox, tilePane);


    }

}
