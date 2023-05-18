package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LibraryInfo;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.LibraryPreviewBox;
import com.dlsc.jfxcentral2.components.MarkdownView;
import com.dlsc.jfxcentral2.components.PaneBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

public class LibraryOverviewBox extends PaneBase {

    private final LibraryPreviewBox libraryPreviewBox;

    public LibraryOverviewBox(Library library) {
        this();
        setData(library);
    }

    public LibraryOverviewBox() {
        getStyleClass().add("overview-box");
        Header header = new Header();
        header.titleProperty().bind(titleProperty());
        header.iconProperty().bind(iconProperty());

        MarkdownView markdownView = new MarkdownView();
        markdownView.mdStringProperty().bind(markdownProperty());

        libraryPreviewBox = new LibraryPreviewBox();
        libraryPreviewBox.sizeProperty().bind(sizeProperty());

        VBox bodyBox = new VBox(markdownView, libraryPreviewBox);
        bodyBox.getStyleClass().add("body-box");

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.getChildren().addAll(header, bodyBox);

        layoutBySize();
        getChildren().setAll(contentBox);

        dataProperty().addListener(it -> layoutBySize());
        libraryInfoProperty().addListener(it -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        Library library = getData();
        setMarkdown(library != null ? library.getDescription() : "");

        libraryPreviewBox.setLibrary(library);
        libraryPreviewBox.setLibraryInfo(getLibraryInfo());
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

    private final ObjectProperty<Library> data = new SimpleObjectProperty<>(this, "data");

    public Library getData() {
        return data.get();
    }

    public ObjectProperty<Library> dataProperty() {
        return data;
    }

    public void setData(Library data) {
        this.data.set(data);
    }

    private final ObjectProperty<LibraryInfo> libraryInfo = new SimpleObjectProperty<>(this, "libraryInfo");

    public LibraryInfo getLibraryInfo() {
        return libraryInfo.get();
    }

    public ObjectProperty<LibraryInfo> libraryInfoProperty() {
        return libraryInfo;
    }

    public void setLibraryInfo(LibraryInfo libraryInfo) {
        this.libraryInfo.set(libraryInfo);
    }
}