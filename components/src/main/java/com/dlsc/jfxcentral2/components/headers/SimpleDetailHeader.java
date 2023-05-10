package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.model.SimpleHeaderInfo;
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
public class SimpleDetailHeader extends DetailHeader {

    public SimpleDetailHeader(SimpleHeaderInfo simpleHeaderInfo) {
        this();
        setSimpleHeaderInfo(simpleHeaderInfo);
    }

    public SimpleDetailHeader() {
        getStyleClass().add("simple-detail-header");

        centerProperty().bind(Bindings.createObjectBinding(this::createCenterNode, simpleHeaderInfoProperty()));
    }

    protected Pane createCenterNode() {
        SimpleHeaderInfo header = getSimpleHeaderInfo();
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
        saveAndLikeButton.setSaveButtonSelected(header.isSaved());
        saveAndLikeButton.setLikeButtonSelected(header.isLiked());
        saveAndLikeButton.saveButtonSelectedProperty().addListener((ob, ov, nv) -> {
            header.setSaved(nv);
            System.out.println(header.getName() + " is saved: " + nv);
        });
        saveAndLikeButton.likeButtonSelectedProperty().addListener((ob, ov, nv) -> {
            header.setLiked(nv);
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
        websiteButton.setOnAction(e -> System.out.println("Open website " + header.getWebsite()));

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

    private final ObjectProperty<SimpleHeaderInfo> simpleHeaderInfo = new SimpleObjectProperty<>(this, "simpleHeaderInfo");

    public SimpleHeaderInfo getSimpleHeaderInfo() {
        return simpleHeaderInfo.get();
    }

    public ObjectProperty<SimpleHeaderInfo> simpleHeaderInfoProperty() {
        return simpleHeaderInfo;
    }

    public void setSimpleHeaderInfo(SimpleHeaderInfo simpleHeaderInfo) {
        this.simpleHeaderInfo.set(simpleHeaderInfo);
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




}
