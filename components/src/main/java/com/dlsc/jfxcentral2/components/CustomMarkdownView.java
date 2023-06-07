package com.dlsc.jfxcentral2.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import one.jpro.routing.LinkUtil;

import java.util.function.Consumer;

public class CustomMarkdownView extends com.sandec.mdfx.MarkdownView {

    public CustomMarkdownView() {
        getStyleClass().add("custom-markdown-view");
        getStylesheets().add(CustomMarkdownView.class.getResource("markdown.css").toExternalForm());
    }

    public CustomMarkdownView(String mdString) {
        this();
        setMdString(mdString);
    }

    private final ObjectProperty<Consumer<Image>> onImageClick = new SimpleObjectProperty<>(this, "onImageClick");

    public Consumer<Image> getOnImageClick() {
        return onImageClick.get();
    }

    public ObjectProperty<Consumer<Image>> onImageClickProperty() {
        return onImageClick;
    }

    public void setOnImageClick(Consumer<Image> onImageClick) {
        this.onImageClick.set(onImageClick);
    }

    private StringProperty baseURL = new SimpleStringProperty(this, "baseURL");

    public String getBaseURL() {
        return baseURL.get();
    }

    public StringProperty baseURLProperty() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL.set(baseURL);
    }

    @Override
    public void setLink(Node node, String link, String description) {
        LinkUtil.setExternalLink(node, link, description);
    }

    @Override
    public Node generateImage(String url) {
        if (url.startsWith("http")) {
            Node node = super.generateImage(url);
            configureImage(node);
            return node;
        }

        Node node = super.generateImage(getBaseURL() + "/" + url);
        configureImage(node);
        return node;
    }

    private void configureImage(Node node) {
        if (node instanceof ImageView imageView) {
            imageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> computeFitWidth(imageView), widthProperty(), imageView.getImage().progressProperty()));
            imageView.setPreserveRatio(true);
            imageView.visibleProperty().bind(showImagesProperty());
            imageView.managedProperty().bind(showImagesProperty());
            imageView.setOnMouseClicked(evt -> {
                Consumer<Image> onImageClick = getOnImageClick();
                if (evt.isStillSincePress() && onImageClick != null) {
                    imageView.getStyleClass().add("clickable-image");
                    onImageClick.accept(imageView.getImage());
                }
            });
        }
    }

    private double computeFitWidth(ImageView imageView) {
//        Image image = imageView.getImage();
//        return Math.min(image.getWidth(), getWidth());
        return getWidth();
    }

    private final BooleanProperty showImages = new SimpleBooleanProperty(this, "showImages", true);

    public boolean isShowImages() {
        return showImages.get();
    }

    public BooleanProperty showImagesProperty() {
        return showImages;
    }

    public void setShowImages(boolean showImages) {
        this.showImages.set(showImages);
    }
}
