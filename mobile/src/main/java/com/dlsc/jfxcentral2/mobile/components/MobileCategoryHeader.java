package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public class MobileCategoryHeader extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "mobile-category-header";
    private final SizeSupport sizeSupport = new SizeSupport(this);

    public MobileCategoryHeader() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Button backButton = new Button();
        backButton.getStyleClass().add("back-button");
        backButton.setGraphic(new FontIcon(FontAwesome.ANGLE_LEFT));
        MobileLinkUtil.setLink(backButton, goBackLink());

        HBox topBox = new HBox(backButton);
        topBox.getStyleClass().add("top-box");

        Label categoryTitle = new Label();
        categoryTitle.getStyleClass().add("category-title");
        categoryTitle.textProperty().bind(titleProperty());
        categoryTitle.graphicProperty().bind(Bindings.createObjectBinding(() -> {
            Ikon icon = getIcon();
            Image image = getPreviewImage();
            if (image != null) {
                return new AvatarView(image);
            } else if (icon != null) {
                return new FontIcon(icon);
            }
            return null;
        }, iconProperty(), previewImageProperty()));

        getChildren().addAll(categoryTitle, topBox);
        setMaxHeight(Region.USE_PREF_SIZE);
    }

    protected String goBackLink() {
        return PagePath.HOME;
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

    // preview image

    private final ObjectProperty<Image> previewImage = new SimpleObjectProperty<>(this, "previewImage");

    public final ObjectProperty<Image> previewImageProperty() {
        return previewImage;
    }

    public final Image getPreviewImage() {
        return previewImage.get();
    }

    public final void setPreviewImage(Image previewImage) {
        previewImageProperty().set(previewImage);
    }

}
