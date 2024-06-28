package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.headers.LibraryDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.LibraryOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class MobileLibraryDetailsPage extends MobileDetailsPageBase<Library> {

    public MobileLibraryDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Library.class, itemId);
    }

    @Override
    public List<Node> content() {
        Library library = getItem();

        // header
        LibraryDetailHeader header = new LibraryDetailHeader(library);
        header.sizeProperty().bind(sizeProperty());

        // overview box
        LibraryOverviewBox libraryOverviewBox = new LibraryOverviewBox(library);
        libraryOverviewBox.sizeProperty().bind(sizeProperty());

        return List.of(header, libraryOverviewBox);
    }
}
