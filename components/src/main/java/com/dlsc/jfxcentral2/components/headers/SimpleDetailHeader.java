package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.utils.IkonUtil;
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
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * AppDetailHeader or BookDetailHeader
 */
public class SimpleDetailHeader<T extends ModelObject> extends DetailHeader<T> {

    private Button websiteButton;

    public SimpleDetailHeader(T model) {
        this();
        setModel(model);
    }

    public SimpleDetailHeader() {
        getStyleClass().add("simple-detail-header");

        centerProperty().bind(Bindings.createObjectBinding(this::createCenterNode, modelProperty()));
    }

    public Button getWebsiteButton() {
        return websiteButton;
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

        websiteButton = new Button();
        websiteButton.visibleProperty().bind(websiteProperty().isNotNull());
        websiteButton.managedProperty().bind(websiteProperty().isNotNull());
        websiteButton.getStyleClass().addAll("website-button", "link-button");
        websiteButton.textProperty().bind(websiteButtonTextProperty());
        websiteButton.graphicProperty().bind(Bindings.createObjectBinding(() -> {
            Ikon icon = getWebsiteButtonIcon();
            if (icon == null) {
                return null;
            }
            return new FontIcon(icon);
        }, websiteButtonIconProperty()));

        websiteProperty().addListener(it -> {
            String url = getWebsite();
            if (StringUtils.isNotBlank(url)) {
                LinkUtil.setExternalLink(websiteButton, url);
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

    private final StringProperty websiteButtonText = new SimpleStringProperty(this, "websiteButtonText", "WEBSITE");

    public String getWebsiteButtonText() {
        return websiteButtonText.get();
    }

    public StringProperty websiteButtonTextProperty() {
        return websiteButtonText;
    }

    public void setWebsiteButtonText(String websiteButtonText) {
        this.websiteButtonText.set(websiteButtonText);
    }

    private final ObjectProperty<Ikon> websiteButtonIcon = new SimpleObjectProperty<>(this, "websiteButtonIcon", IkonUtil.website);

    public Ikon getWebsiteButtonIcon() {
        return websiteButtonIcon.get();
    }

    public ObjectProperty<Ikon> websiteButtonIconProperty() {
        return websiteButtonIcon;
    }

    public void setWebsiteButtonIcon(Ikon websiteButtonIcon) {
        this.websiteButtonIcon.set(websiteButtonIcon);
    }

    private final StringProperty website = new SimpleStringProperty(this, "website");

    public String getWebsite() {
        return website.get();
    }

    public StringProperty websiteProperty() {
        return website;
    }

    public void setWebsite(String website) {
        this.website.set(website);
    }
}
