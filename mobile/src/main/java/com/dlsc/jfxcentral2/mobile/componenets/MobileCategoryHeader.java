package com.dlsc.jfxcentral2.mobile.componenets;

import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public class MobileCategoryHeader extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "mobile-category-header";
    private final SizeSupport sizeSupport = new SizeSupport(this);

    public MobileCategoryHeader() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Button backButton = new Button();
        backButton.getStyleClass().add("back-button");
        backButton.setGraphic(new FontIcon(FontAwesome.ANGLE_LEFT));
        MobileLinkUtil.setLink(backButton, PagePath.HOME);

        HBox topBox = new HBox(backButton);
        topBox.getStyleClass().add("top-box");

        Label categoryTitle = new Label();
        categoryTitle.getStyleClass().add("category-title");
        categoryTitle.textProperty().bind(titleProperty());
        FontIcon categoryIcon = new FontIcon();
        categoryIcon.iconCodeProperty().bind(iconProperty());
        categoryTitle.setGraphic(categoryIcon);

        getChildren().addAll(topBox, categoryTitle);
        setMaxHeight(Region.USE_PREF_SIZE);
    }

    // size support

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    // category icon

    private final ObjectProperty<Ikon> icon = new SimpleObjectProperty<>(this, "icon");

    public final ObjectProperty<Ikon> iconProperty() {
        return icon;
    }

    public final Ikon getIcon() {
        return icon.get();
    }

    public final void setIcon(Ikon icon) {
        iconProperty().set(icon);
    }

    // category title

    private final ObjectProperty<String> title = new SimpleObjectProperty<>(this, "title");

    public final ObjectProperty<String> titleProperty() {
        return title;
    }

    public final String getTitle() {
        return title.get();
    }

    public final void setTitle(String title) {
        titleProperty().set(title);
    }

}
