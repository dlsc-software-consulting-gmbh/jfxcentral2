package com.dlsc.jfxcentral2.mobile.home;

import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class HomePageHeader extends HBox {

    private static final String DEFAULT_STYLE_CLASS = "home-page-header";
    private static final Image jfxcentralLogoImage = new Image(Objects.requireNonNull(HomePageHeader.class.getResource("jfx-logo-primary-full-color.png")).toExternalForm(), true);
    private static final Image fxLogoImage = new Image(Objects.requireNonNull(HomePageHeader.class.getResource("fxlogo.png")).toExternalForm(), true);

    private final SizeSupport sizeSupport = new SizeSupport(this);
    private final Label label1;
    private final CustomImageView fxLogo;
    private final Label label2;
    private final CustomImageView logo;

    public HomePageHeader() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // left side
        logo = new CustomImageView();
        logo.setImage(jfxcentralLogoImage);
        logo.getStyleClass().add("jfxcentral-logo");

        // right side
        label1 = new Label("Home to anything");
        label1.getStyleClass().add("label1");

        fxLogo = new CustomImageView();
        fxLogo.getStyleClass().add("fx-logo");
        fxLogo.setImage(fxLogoImage);
        // make the logo black and white
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1.0);
        colorAdjust.setBrightness(-1.0);
        colorAdjust.setBrightness(-1.0);
        fxLogo.setEffect(colorAdjust);

        label2 = new Label("related");
        label2.getStyleClass().add("label2");

        setMaxHeight(Region.USE_PREF_SIZE);

        updateView();
        sizeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Size.SMALL || oldValue == Size.SMALL) {
                updateView();
            }
        });
    }

    private void updateView() {
        getChildren().clear();
        if (getSize() == Size.SMALL) {
            HBox row2 = new HBox(fxLogo, label2);
            row2.getStyleClass().add("row2");

            VBox rightSide = new VBox(label1, row2);
            rightSide.getStyleClass().add("right-side");
            rightSide.setMaxHeight(Region.USE_PREF_SIZE);

            getChildren().setAll(logo, rightSide);
        } else {
            getChildren().setAll(logo, label1, fxLogo, label2);
        }
    }

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

}
