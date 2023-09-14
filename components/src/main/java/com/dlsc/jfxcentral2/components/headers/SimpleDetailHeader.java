package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * AppDetailHeader or BookDetailHeader
 */
public class SimpleDetailHeader<T extends ModelObject> extends DetailHeader<T> {
    private static final Logger LOGGER = LogManager.getLogger(SimpleDetailHeader.class);
    private Button websiteButton;

    public SimpleDetailHeader(T model) {
        super(model);

        getStyleClass().add("simple-detail-header");

        setCenter(createCenterNode());
        setSummary(model.getSummary());
        sizeProperty().addListener(it-> setCenter(createCenterNode()));
    }

    public Button getWebsiteButton() {
        return websiteButton;
    }

    protected Pane createCenterNode() {
        ModelObject model = getModel();
        if (model == null) {
            return null;
        }

        CustomImageView logoImageView = new CustomImageView();
        logoImageView.getStyleClass().add("logo-image-view");
        logoImageView.imageProperty().bind(imageProperty());
        logoImageView.managedProperty().bind(logoImageView.visibleProperty());
        logoImageView.visibleProperty().bind(imageProperty().isNotNull());

        BooleanBinding needAdjustmentToLeft = Bindings.createBooleanBinding(() -> getImage() != null && !isSmall(), imageProperty(), sizeProperty());

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.alignmentProperty().bind(Bindings.when(needAdjustmentToLeft).then(Pos.TOP_LEFT).otherwise(Pos.CENTER));

        Label nameLabel = new Label(model.getName());
        nameLabel.getStyleClass().add("name");
        nameLabel.setWrapText(true);
        nameLabel.textAlignmentProperty().bind(Bindings.when(needAdjustmentToLeft).then(TextAlignment.LEFT).otherwise(TextAlignment.CENTER));

        Label summaryLabel = new Label();
        summaryLabel.visibleProperty().bind(summaryLabel.textProperty().isNotEmpty());
        summaryLabel.managedProperty().bind(summaryLabel.textProperty().isNotEmpty());
        summaryLabel.textProperty().bind(summaryProperty());
        summaryLabel.getStyleClass().add("description");
        summaryLabel.setWrapText(true);
        summaryLabel.textAlignmentProperty().bind(nameLabel.textAlignmentProperty());
        summaryLabel.setMinHeight(Region.USE_PREF_SIZE);

        SaveAndLikeButton saveAndLikeButton = new SaveAndLikeButton();
        saveAndLikeButton.setMinWidth(Region.USE_PREF_SIZE);
        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(model));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(model));
        saveAndLikeButton.saveButtonSelectedProperty().addListener((ob, ov, nv) ->
                LOGGER.info("{} Save: {}",model.getName(), nv? "YES" : "NO"));
        saveAndLikeButton.likeButtonSelectedProperty().addListener((ob, ov, nv) ->
                LOGGER.info("{} Like: {}",model.getName(), nv? "YES" : "NO"));

        websiteButton = new Button();
        websiteButton.setFocusTraversable(false);
        websiteButton.setMinWidth(Region.USE_PREF_SIZE);
        websiteButton.visibleProperty().bind(websiteProperty().isNotEmpty());
        websiteButton.managedProperty().bind(websiteProperty().isNotEmpty());
        websiteButton.getStyleClass().addAll("website-button", "link-button");
        websiteButton.textProperty().bind(websiteButtonTextProperty());
        websiteButton.graphicProperty().bind(Bindings.createObjectBinding(() -> {
            Ikon icon = getWebsiteButtonIcon();
            if (icon == null) {
                return null;
            }
            return new FontIcon(icon);
        }, websiteButtonIconProperty()));

        String website = getWebsite();
        if (StringUtils.isNotBlank(website)) {
            LinkUtil.setExternalLink(websiteButton, website);
        }

        websiteProperty().addListener(it -> {
            String url = getWebsite();
            if (StringUtils.isNotBlank(url)) {
                LinkUtil.setExternalLink(websiteButton, url);
            }
        });

        Region separator = new Region();
        separator.getStyleClass().add("region-separator");
        separator.managedProperty().bind(separator.visibleProperty());
        separator.visibleProperty().bind(websiteButton.visibleProperty().and(saveAndLikeButton.visibleProperty()));

        HBox buttonBox = new HBox(saveAndLikeButton, separator, websiteButton);
        buttonBox.getStyleClass().add("button-box");
        buttonBox.alignmentProperty().bind(Bindings.when(needAdjustmentToLeft).then(Pos.CENTER_LEFT).otherwise(Pos.CENTER));

        contentBox.getChildren().addAll(nameLabel, summaryLabel, buttonBox);

        Pane contentPane = isSmall() ? new VBox(logoImageView, contentBox) : new FlowPane(logoImageView, contentBox);
        contentPane.getStyleClass().add("flow-pane");
        return contentPane;
    }

    private final StringProperty summary = new SimpleStringProperty(this, "summary");

    public String getSummary() {
        return summary.get();
    }

    public StringProperty summaryProperty() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary.set(summary);
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

    private final ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }
}
