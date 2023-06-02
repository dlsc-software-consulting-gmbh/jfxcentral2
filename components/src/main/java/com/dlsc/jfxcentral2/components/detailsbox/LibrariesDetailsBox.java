package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LibraryInfo;
import com.dlsc.jfxcentral2.components.LibraryPreviewBox;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class LibrariesDetailsBox extends DetailsBoxBase<Library> {

    public LibrariesDetailsBox() {
        getStyleClass().add("libraries-details-box");
        setTitle("LIBRARIES");
        setIkon(IkonUtil.library);
        setMaxItemsPerPage(3);
        setHomepageUrlProvider(library -> {
            String url = library.getHomepage();
            if (StringUtils.isBlank(url)) {
                url = library.getRepository();
            }
            return url;
        });
        libraryInfoProperty().addListener(it -> layoutBySize());
    }

    @Override
    protected Node createPreviewsBox(Library library) {
        LibraryPreviewBox previewsBox = new LibraryPreviewBox();
        previewsBox.sizeProperty().bind(sizeProperty());
        previewsBox.setLibrary(library);
        previewsBox.libraryInfoProperty().bind(libraryInfoProperty());
        return previewsBox;
    }

    @Override
    protected List<Node> createActionButtons(Library model) {
        return List.of(createDetailsButton(model), createHomepageButton(model));
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
