package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral2.icons.MaterialIcon;

import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import com.dlsc.jfxcentral2.icons.Ikon;
import com.dlsc.jfxcentral2.icons.FontIcon;

public class MobilePageHeader extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "mobile-page-header";
    private final SizeSupport sizeSupport = new SizeSupport(this);
    private final Label categoryTitle;

    public MobilePageHeader() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Button backButton = new Button();
        backButton.getStyleClass().add("back-button");
        backButton.setGraphic(new FontIcon(MaterialIcon.ARROW_LEFT));
        MobileLinkUtil.setGoToBackLink(backButton);

        HBox topBox = new HBox(backButton);
        topBox.getStyleClass().add("top-box");

        categoryTitle = new Label();
        categoryTitle.getStyleClass().add("title");
        categoryTitle.setWrapText(true);
        categoryTitle.setMinHeight(-1);
        categoryTitle.setMaxHeight(Double.MAX_VALUE);

        // categoryTitle.
        categoryTitle.textProperty().bind(titleProperty());
        categoryTitle.graphicProperty().bind(Bindings.createObjectBinding(() -> {
            Ikon icon = getIcon();
            Image image = getPreviewImage();
            if (image != null) {
                AvatarView avatar = new AvatarView(image);
                // mobile.css's -fx-avatar-size: 25px; the layer has no
                // CssMetaData for a custom property.
                avatar.setAvatarSize(25);
                return avatar;
            } else if (icon != null) {
                return new FontIcon(icon);
            }
            return null;
        }, iconProperty(), previewImageProperty()));

        BooleanBinding longTitleProperty = Bindings.createBooleanBinding(() -> {
            String title = getTitle();
            boolean isShortTitle = title == null || (title.split(" ").length < 7 && title.length() < 35);
            return getIcon() == null && getPreviewImage() == null && !isShortTitle;
        }, widthProperty(), iconProperty(), previewImageProperty(), titleProperty());

        updateLongTitle(longTitleProperty.get());
        longTitleProperty.addListener((obs, oldVal, newVal) -> updateLongTitle(newVal));

        getChildren().addAll(categoryTitle, topBox);
        setMaxHeight(Region.USE_PREF_SIZE);
    }

    private void updateLongTitle(boolean isLongTitle) {
        if (isLongTitle) {
            if (!categoryTitle.getStyleClass().contains("long-title")) {
                categoryTitle.getStyleClass().add("long-title");
            }
        } else {
            getStyleClass().remove("long-title");
        }
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
