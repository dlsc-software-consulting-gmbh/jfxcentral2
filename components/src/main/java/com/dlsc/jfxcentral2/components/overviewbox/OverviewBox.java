package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.model.NameProvider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.Objects;

public class OverviewBox<T extends ModelObject> extends PaneBase implements NameProvider {

    private final StackPane topWrapper;
    private final StackPane bottomWrapper;
    private final T model;

    public OverviewBox(T model) {
        this.model = Objects.requireNonNull(model, "model can not be null");

        getStyleClass().add("overview-box");

        Header header = new Header();
        header.titleProperty().bind(titleProperty());
        header.iconProperty().bind(iconProperty());

        topWrapper = new StackPane();
        topWrapper.getStyleClass().add("top-wrapper");

        CustomMarkdownView markdownView = new CustomMarkdownView();
        markdownView.mdStringProperty().bind(markdownProperty());
        markdownView.baseURLProperty().bind(baseURLProperty());

        bottomWrapper = new StackPane();
        bottomWrapper.getStyleClass().add("bottom-wrapper");

        VBox bodyBox = new VBox(topWrapper, markdownView, bottomWrapper);
        bodyBox.getStyleClass().add("body-box");

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.getChildren().addAll(header, bodyBox);

        layoutBySize();
        getChildren().setAll(contentBox);
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
    protected void layoutBySize() {
        topWrapper.getChildren().clear();
        Node topNode = createTopNode();
        if (topNode != null) {
            topWrapper.getChildren().add(topNode);
        }

        bottomWrapper.getChildren().clear();
        Node bottomNode = createBottomNode();
        if (bottomNode != null) {
            bottomWrapper.getChildren().add(bottomNode);
        }
    }

    protected Node createTopNode() {
        return null;
    }

    protected Node createBottomNode() {
        return null;
    }

    private final StringProperty markdown = new SimpleStringProperty(this, "markdown");

    public String getMarkdown() {
        return markdown.get();
    }

    public StringProperty markdownProperty() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown.set(markdown);
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", "Overview");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final ObjectProperty<Ikon> icon = new SimpleObjectProperty<>(this, "icon", MaterialDesignP.PLAYLIST_EDIT);

    public Ikon getIcon() {
        return icon.get();
    }

    public ObjectProperty<Ikon> iconProperty() {
        return icon;
    }

    public void setIcon(Ikon icon) {
        this.icon.set(icon);
    }

    public T getModel() {
        return model;
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

    @Override
    public String getName() {
        return "Overview";
    }
}
