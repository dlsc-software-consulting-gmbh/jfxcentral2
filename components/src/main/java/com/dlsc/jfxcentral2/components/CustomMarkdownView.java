package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import one.jpro.jproutils.treeshowing.TreeShowing;
import one.jpro.platform.mdfx.MarkdownView;
import one.jpro.platform.mdfx.extensions.ImageExtension;
import one.jpro.platform.mdfx.extensions.YoutubeExtension;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class CustomMarkdownView extends one.jpro.platform.mdfx.MarkdownView {

    static List<ImageExtension> extensions = MarkdownView.defaultExtensions();

    static {
        extensions.add(YoutubeExtension.create());
    }

    public CustomMarkdownView() {
        super("", extensions);

        getStyleClass().add("custom-markdown-view");
        getStylesheets().add(Objects.requireNonNull(CustomMarkdownView.class.getResource("markdown.css")).toExternalForm());

        TreeShowing.treeShowing(this).addListener(it -> setupWorkAroundForWebViewLayout());
        mdStringProperty().addListener(it -> Platform.runLater(this::setupWorkAroundForWebViewLayout));
    }

    private void setupWorkAroundForWebViewLayout() {
        List<WebView> webViews = new ArrayList<>();
        getChildrenUnmodifiable().forEach(child -> collectWebViews(child, webViews));
        webViews.forEach(view -> view.localToSceneTransformProperty().addListener((obs, oldV, newV) -> fixIt(view)));
    }

    boolean fixing = false;

    private void fixIt(WebView view) {
        if (!fixing) {
            fixing = true;
            view.setLayoutY(view.getLayoutY() + 1);
            double width = view.getWidth();
            double height = view.getHeight();
            view.resize(width + 1, height + 1);
            view.resize(width, height);
            Platform.runLater(() -> fixing = false);
        }
    }

    private void collectWebViews(Node node, List<WebView> webViews) {
        if (node instanceof WebView) {
            webViews.add((WebView) node);
        } else if (node instanceof Parent) {
            Parent parent = (Parent) node;
            parent.getChildrenUnmodifiable().forEach(child -> collectWebViews(child, webViews));
        }
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

    private final StringProperty baseURL = new SimpleStringProperty(this, "baseURL");

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
        if (StringUtils.isNotBlank(link)) {
            ExternalLinkUtil.setExternalLink(node, link, description);
        }
    }

    @Override
    public Node generateImage(String url) {
        if (url.startsWith("youtube")) {
            return super.generateImage(url);
        }

        if (url.startsWith("http")) {
            Node node = super.generateImage(url);
            configureNodeIfImageView(node);
            return node;
        }

        Node node = super.generateImage(getBaseURL() + "/" + url);
        configureNodeIfImageView(node);
        return node;
    }

    private void configureNodeIfImageView(Node node) {
        if (node instanceof ImageView imageView) {
            imageView.fitWidthProperty().bind(widthProperty());
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
