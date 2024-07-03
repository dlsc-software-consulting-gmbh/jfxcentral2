package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.LibraryOverviewBox;
import com.dlsc.jfxcentral2.mobile.componenets.MobileCategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileLibraryDetailsPage extends MobileDetailsPageBase<Library> {

    public MobileLibraryDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Library.class, itemId);
    }

    @Override
    public List<Node> content() {
        Library library = getItem();

        // header
        MobileCategoryHeader header = new MobileCategoryHeader(){
            @Override
            protected String goBackLink() {
                return PagePath.LIBRARIES;
            }
        };
        header.previewImageProperty().bind(ImageManager.getInstance().libraryFeaturedImageProperty(library));
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(library.getName());

        // overview box
        LibraryOverviewBox libraryOverviewBox = new LibraryOverviewBox(library);
        libraryOverviewBox.sizeProperty().bind(sizeProperty());

        PrettyScrollPane detailsContentPane = new PrettyScrollPane(new StackPane(libraryOverviewBox));
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);

        return List.of(header, detailsContentPane);
    }
}
