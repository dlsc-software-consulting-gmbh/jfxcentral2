package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.MarkdownView;
import com.dlsc.jfxcentral2.components.PaneBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

public class OverviewBox<T extends ModelObject> extends PaneBase {

    private final StackPane topWrapper;
    private final StackPane bottomWrapper;

    public OverviewBox(T modelObject) {
        getStyleClass().add("overview-box");

        Header header = new Header();
        header.titleProperty().bind(titleProperty());
        header.iconProperty().bind(iconProperty());

        topWrapper = new StackPane();
        topWrapper.getStyleClass().add("top-wrapper");

        MarkdownView markdownView = new MarkdownView();
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

        setData(modelObject);
    }

    public OverviewBox() {
        this(null);
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

    private final ObjectProperty<T> data = new SimpleObjectProperty<>(this, "data");

    public T getData() {
        return data.get();
    }

    public ObjectProperty<T> dataProperty() {
        return data;
    }

    public void setData(T data) {
        this.data.set(data);
    }
}
