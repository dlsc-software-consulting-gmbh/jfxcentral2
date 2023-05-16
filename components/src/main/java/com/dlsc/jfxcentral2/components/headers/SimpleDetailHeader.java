package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * AppDetailHeader or BookDetailHeader
 */
public class SimpleDetailHeader<T extends ModelObject> extends DetailHeader<ModelObject> {

    public SimpleDetailHeader(T model) {
        this();
        setModel(model);
    }

    public SimpleDetailHeader() {
        getStyleClass().add("simple-detail-header");

        centerProperty().bind(Bindings.createObjectBinding(this::createCenterNode, modelProperty()));
    }

    protected Pane createCenterNode() {
        ModelObject header = getModel();
        if (header == null) {
            return null;
        }

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");

        Label nameLabel = new Label(header.getName());
        nameLabel.getStyleClass().add("name");
        nameLabel.setWrapText(true);

        Label descriptionLabel = null;
        if (header.getDescription() != null && !header.getDescription().isEmpty()) {
            descriptionLabel = new Label(header.getDescription());
            descriptionLabel.getStyleClass().add("description");
            descriptionLabel.setWrapText(true);
        }

        SaveAndLikeButton saveAndLikeButton = new SaveAndLikeButton();
        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(header));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(header));
        saveAndLikeButton.saveButtonSelectedProperty().addListener((ob, ov, nv) -> {
            System.out.println(header.getName() + " is saved: " + nv);
        });
        saveAndLikeButton.likeButtonSelectedProperty().addListener((ob, ov, nv) -> {
            System.out.println(header.getName() + " is liked: " + nv);
        });

        Button websiteButton = new Button();
        websiteButton.getStyleClass().addAll("website-button", "link-button");
        websiteButton.textProperty().bind(websiteButtonTextProperty());
        websiteButton.graphicProperty().bind(Bindings.createObjectBinding(() -> {
            Ikon icon = getWebsiteButtonIcon();
            if (icon == null) {
                return null;
            }
            return new FontIcon(icon);
        }, websiteButtonIconProperty()));
        websiteButton.setOnAction(e ->{
            if (onWebsite.get() != null) {
                onWebsite.get().run();
            }
        });

        Region separator = new Region();
        separator.getStyleClass().add("region-separator");

        HBox buttonBox = new HBox(saveAndLikeButton, separator, websiteButton);
        buttonBox.getStyleClass().add("button-box");

        if (descriptionLabel != null) {
            contentBox.getChildren().addAll(nameLabel, descriptionLabel, buttonBox);
        } else {
            contentBox.getChildren().addAll(nameLabel, buttonBox);
        }

        contentBox.setMaxWidth(Region.USE_PREF_SIZE);
        return contentBox;
    }

    private final StringProperty websiteButtonText = new SimpleStringProperty(this, "websiteButtonText");

    public String getWebsiteButtonText() {
        return websiteButtonText.get();
    }

    public StringProperty websiteButtonTextProperty() {
        return websiteButtonText;
    }

    public void setWebsiteButtonText(String websiteButtonText) {
        this.websiteButtonText.set(websiteButtonText);
    }

    private final ObjectProperty<Ikon> websiteButtonIcon = new SimpleObjectProperty<>(this, "websiteButtonIcon");

    public Ikon getWebsiteButtonIcon() {
        return websiteButtonIcon.get();
    }

    public ObjectProperty<Ikon> websiteButtonIconProperty() {
        return websiteButtonIcon;
    }

    public void setWebsiteButtonIcon(Ikon websiteButtonIcon) {
        this.websiteButtonIcon.set(websiteButtonIcon);
    }


    private final ObjectProperty<Runnable> onWebsite = new SimpleObjectProperty<>(this, "onWebsite");

    public Runnable getOnWebsite() {
        return onWebsite.get();
    }

    public ObjectProperty<Runnable> onWebsiteProperty() {
        return onWebsite;
    }

    public void setOnWebsite(Runnable onWebsite) {
        this.onWebsite.set(onWebsite);
    }
}
