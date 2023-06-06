package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.EnumSet;

public class IconPreviewPane extends PaneBase {

    public IconPreviewPane() {
        getStyleClass().add("icon-preview-pane");
        modelProperty().addListener(it -> requestLayout());
    }

    @Override
    protected void layoutBySize() {
        if (getModel() == null) {
            getChildren().clear();
            return;
        }
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("icon-grid-pane");
        IkonliPack ikonPackModel = getModel();
        ObservableList<? extends Ikon> icons = FXCollections.observableArrayList();
        IkonProvider ikonProvider = IkonliPackUtil.getInstance().getIkonData(ikonPackModel.getName()).getIkonProvider();
        EnumSet enumSet = EnumSet.allOf(ikonProvider.getIkon());
        icons.addAll(enumSet);
        FXCollections.shuffle(icons);
        int columnCount = isMedium() ? 4 : 6;
        for (int i = 0; i < 24 && i < icons.size(); i++) {
            FontIcon fontIcon = new FontIcon(icons.get(i));
            fontIcon.getStyleClass().add("icon-font");
            fontIcon.setFill(generateColor());
            gridPane.add(fontIcon, i % columnCount, i / columnCount);
        }
        gridPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Label countLabel = new Label("Icons: " + icons.size());
        countLabel.getStyleClass().add("count-label");

        //IconStyle iconStyle = iconModel.getIconStyle();
        //Label styleLabel = new Label("Style: " + iconStyle.toString());
        //styleLabel.getStyleClass().add("style-label");

        HBox infoBox = new HBox(new Spacer(), countLabel);
        infoBox.getStyleClass().add("info-box");
        infoBox.setMaxHeight(Region.USE_PREF_SIZE);
        StackPane.setAlignment(infoBox, Pos.BOTTOM_CENTER);

        getStyleClass().add("icon-grid-wrapper");
        getChildren().setAll(gridPane, infoBox);
    }

    /**
     * Generates a random color.
     * Can't all be 1.0, otherwise white icons may appear
     */
    public static Color generateColor() {
        double red = Math.random() * 0.75 + 0.2;
        double green = Math.random() * 0.75 + 0.2;
        double blue = Math.random() * 0.75 + 0.2;
        return new Color(red, green, blue, 1.0);
    }

    private final ObjectProperty<IkonliPack> model = new SimpleObjectProperty<>(this, "model");

    public IkonliPack getModel() {
        return model.get();
    }

    public ObjectProperty<IkonliPack> modelProperty() {
        return model;
    }

    public void setModel(IkonliPack ikonPackModel) {
        this.model.set(ikonPackModel);
    }
}
