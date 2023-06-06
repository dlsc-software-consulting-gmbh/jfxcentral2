package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.detailsbox.LibraryCoordinatesBox;
import com.dlsc.jfxcentral2.components.detailsbox.LibraryEnsembleBox;
import com.dlsc.jfxcentral2.components.headers.LibraryDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.LibraryOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class LibraryDetailsPage extends DetailsPageBase<Library> {

    public LibraryDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Library.class, itemId);
    }

    @Override
    public Node content() {
        Library library = getItem();

        // header
        LibraryDetailHeader header = new LibraryDetailHeader(library);
        header.sizeProperty().bind(sizeProperty());

        // overview box
        LibraryOverviewBox libraryOverviewBox = new LibraryOverviewBox(library);
        libraryOverviewBox.sizeProperty().bind(sizeProperty());

        // coordinates box
        LibraryCoordinatesBox coordinatesBox = new LibraryCoordinatesBox(library);
        coordinatesBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());
        detailsContentPane.getCenterNodes().addAll(libraryOverviewBox, coordinatesBox);

        // ensemble box if online demos are indeed available
        if (library.isEnsemble()) {
            LibraryEnsembleBox ensembleBox = new LibraryEnsembleBox(library);
            ensembleBox.sizeProperty().bind(sizeProperty());
            detailsContentPane.getCenterNodes().add(1, ensembleBox);
        }

        return wrapContent(header, detailsContentPane);
    }
}
