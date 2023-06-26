package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.model.Size;
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
    private int columnCount = 4;
    private Size cachedSize;

    public IconPreviewPane() {
        getStyleClass().addAll("icon-preview-pane", "icon-grid-wrapper");
        modelProperty().addListener(it -> requestLayout());
        widthProperty().addListener((ob, ov, nv) -> {
            Size size = getSize();
            int tempColumnCount = (int) (isSmall() ? nv.doubleValue() / 60 : isMedium() ? nv.doubleValue() / 55 : nv.doubleValue() / 76);
            if ((tempColumnCount == columnCount || tempColumnCount < 1) && cachedSize == size) {
                return;
            }
            cachedSize = size;
            columnCount = tempColumnCount;
            layoutBySize();
        });
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
        //PaymentFont is a very big ,the width is too large;
        if (ikonPackModel.getName().equalsIgnoreCase("PaymentFont")) {
            getStyleClass().add("payment-font-preview");
        }

        ObservableList<? extends Ikon> icons = FXCollections.observableArrayList();
        IkonProvider ikonProvider = IkonliPackUtil.getInstance().getIkonData(ikonPackModel.getName()).getIkonProvider();
        EnumSet enumSet = EnumSet.allOf(ikonProvider.getIkon());
        icons.addAll(enumSet);
        FXCollections.shuffle(icons);

        for (int i = 0; i < columnCount * 4 && i < icons.size(); i++) {
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
        model.set(ikonPackModel);
    }
}
