package com.dlsc.jfxcentral2.mobile.home;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.PlatformLinkUtil;
import com.dlsc.jfxcentral2.utils.SocialUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class CategoryPreviewView extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "category-preview-view";
    private final Button moreButton;

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public CategoryPreviewView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Label titleLabel = new Label();
        titleLabel.textProperty().bind(titleProperty());
        titleLabel.getStyleClass().add("title");

        moreButton = new Button("More", new FontIcon("mdi-chevron-right"));
        moreButton.getStyleClass().add("more-button");

        HBox header = new HBox(titleLabel, new Spacer(), moreButton);
        header.getStyleClass().add("header");

        VBox content = new VBox();
        content.getStyleClass().add("content");

        itemsProperty().addListener((observable, oldValue, newValue) -> {
            content.getChildren().clear();
            newValue.forEach(item -> {
                CategoryCell cell = new CategoryCell();
                cell.setTitle(item.title());
                cell.setDescription(item.description());

                // must enable social features
                cell.setLikesNumber(item.likesNumber());
                cell.setSaveNumber(item.saveNumber());
                cell.setViewsNumber(item.viewsNumber());

                if (item.imageProperty() != null) {
                    cell.imageProperty().bind(item.imageProperty());
                }

                PlatformLinkUtil.setLink(cell, item.url());


                content.getChildren().add(cell);
            });
        });

        getChildren().addAll(header, content);
    }

    /**
     * Represents a single item in the category preview.
     *
     * @param likesNumber : must enable social features
     * @param viewsNumber : must enable social features
     */
    public record CategoryItem(String title, String description, ObjectProperty<Image> imageProperty, String url,
                               int likesNumber,
                               int saveNumber,
                               int viewsNumber) {
        public CategoryItem(String title, String description, ObjectProperty<Image> imageProperty, String url) {
            this(title, description, imageProperty, url, 0, 0, 0);
        }
    }

    // size

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    // title

    private final StringProperty title = new SimpleStringProperty(this, "title");

    public final StringProperty titleProperty() {
        return title;
    }

    public final String getTitle() {
        return titleProperty().get();
    }

    public final void setTitle(String title) {
        titleProperty().set(title);
    }

    // show all url

    private final StringProperty showAllUrl = new SimpleStringProperty(this, "showAllUrl") {
        @Override
        protected void invalidated() {
            PlatformLinkUtil.setLink(moreButton, getShowAllUrl());
        }
    };

    public StringProperty showAllUrlProperty() {
        return showAllUrl;
    }

    public String getShowAllUrl() {
        return showAllUrlProperty().get();
    }

    public void setShowAllUrl(String showAllUrl) {
        showAllUrlProperty().set(showAllUrl);
    }

    // items

    private final ListProperty<CategoryItem> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ListProperty<CategoryItem> itemsProperty() {
        return items;
    }

    public ObservableList<CategoryItem> getItems() {
        return itemsProperty().get();
    }

    public void setItems(ObservableList<CategoryItem> items) {
        itemsProperty().set(items);
    }

    // CategoryCell

    private static class CategoryCell extends HBox {

        private static final String DEFAULT_STYLE_CLASS = "category-cell";
        private static final PseudoClass SOCIAL_FEATURES_ENABLED = PseudoClass.getPseudoClass("social-enabled");

        public CategoryCell() {
            getStyleClass().add(DEFAULT_STYLE_CLASS);

            // left side
            AvatarView avatarView = new AvatarView();
            avatarView.imageProperty().bind(imageProperty());
            avatarView.setType(AvatarView.Type.SQUARE);

            // right side
            Label titleLabel = new Label();
            titleLabel.getStyleClass().add("title");
            titleLabel.textProperty().bind(titleProperty());

            Label descriptionLabel = new Label();
            descriptionLabel.getStyleClass().add("description");
            descriptionLabel.setWrapText(true);
            descriptionLabel.setMaxHeight(Double.MAX_VALUE);
            descriptionLabel.textProperty().bind(descriptionProperty());
            VBox.setVgrow(descriptionLabel, Priority.ALWAYS);

            Label likesLabel = new Label();
            likesLabel.getStyleClass().add("likes-label");
            likesLabel.setGraphic(new FontIcon("mdi-heart"));
            likesLabel.textProperty().bind(likesNumberProperty().asString());

            Label savesLabel = new Label();
            savesLabel.getStyleClass().add("saves-label");
            savesLabel.setGraphic(new FontIcon("mdi-bookmark"));
            savesLabel.textProperty().bind(likesNumberProperty().asString());

            Label viewsLabel = new Label();
            viewsLabel.getStyleClass().add("views-label");
            viewsLabel.setGraphic(new FontIcon("mdi-eye"));
            viewsLabel.textProperty().bind(viewsNumberProperty().asString());

            HBox footer = new HBox(likesLabel, savesLabel, viewsLabel);
            footer.getStyleClass().add("footer");
            footer.managedProperty().bind(footer.visibleProperty());
            footer.setVisible(SocialUtil.isSocialFeaturesEnabled());
            pseudoClassStateChanged(SOCIAL_FEATURES_ENABLED, footer.isVisible());
            footer.visibleProperty().addListener(it-> pseudoClassStateChanged(SOCIAL_FEATURES_ENABLED, footer.isVisible()));

            VBox cellContent = new VBox(titleLabel, descriptionLabel, footer);
            cellContent.getStyleClass().add("cell-content");
            HBox.setHgrow(cellContent, Priority.ALWAYS);

            this.maxHeightProperty().bind(Bindings.createObjectBinding(() -> avatarView.getAvatarSize() + this.getInsets().getTop() + this.getInsets().getBottom(), avatarView.avatarSizeProperty(), this.insetsProperty()));
            getChildren().addAll(avatarView, cellContent);
        }

        // title

        private final StringProperty title = new SimpleStringProperty(this, "title");

        public final StringProperty titleProperty() {
            return title;
        }

        public final String getTitle() {
            return titleProperty().get();
        }

        public final void setTitle(String title) {
            titleProperty().set(title);
        }

        // description

        private final StringProperty description = new SimpleStringProperty(this, "description");

        public final StringProperty descriptionProperty() {
            return description;
        }

        public final String getDescription() {
            return descriptionProperty().get();
        }

        public final void setDescription(String description) {
            descriptionProperty().set(description);
        }

        // image

        private final ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

        public final ObjectProperty<Image> imageProperty() {
            return image;
        }

        public final Image getImage() {
            return imageProperty().get();
        }

        public final void setImage(Image image) {
            imageProperty().set(image);
        }

        // likesNumber must enable social features

        private IntegerProperty likesNumber;

        public final IntegerProperty likesNumberProperty() {
            if (likesNumber == null) {
                likesNumber = new SimpleIntegerProperty(this, "likesNumber");
            }
            return likesNumber;
        }

        public final int getLikesNumber() {
            return likesNumber == null ? 0 : likesNumber.get();
        }

        public final void setLikesNumber(int likesNumber) {
            likesNumberProperty().set(likesNumber);
        }

        // saveNumber must enable social features

        private IntegerProperty saveNumber;

        public final IntegerProperty saveNumberProperty() {
            if (saveNumber == null) {
                saveNumber = new SimpleIntegerProperty(this, "saveNumber");
            }
            return saveNumber;
        }

        public final int getSaveNumber() {
            return saveNumber == null ? 0 : saveNumber.get();
        }

        public final void setSaveNumber(int saveNumber) {
            saveNumberProperty().set(saveNumber);
        }

        // viewsNumber must enable social features

        private IntegerProperty viewsNumber;

        public final IntegerProperty viewsNumberProperty() {
            if (viewsNumber == null) {
                viewsNumber = new SimpleIntegerProperty(this, "viewsNumber");
            }
            return viewsNumber;
        }

        public final int getViewsNumber() {
            return viewsNumber == null ? 0 : viewsNumber.get();
        }

        public final void setViewsNumber(int viewsNumber) {
            viewsNumberProperty().set(viewsNumber);
        }
    }
}
