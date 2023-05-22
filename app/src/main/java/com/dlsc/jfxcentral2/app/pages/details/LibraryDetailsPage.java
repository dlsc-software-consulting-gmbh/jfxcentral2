package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.DetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.LibraryOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class LibraryDetailsPage extends DetailsPageBase<Library> {

    public LibraryDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Library.class, itemId);
    }

    @Override
    public Node content() {

        // header
        DetailHeader<Library> header = new DetailHeader<>(getItem());
        header.setTitle(getItem().getName());
        header.setIkon(IkonUtil.getModelIkon(Library.class));
        header.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());
        detailsContentPane.getCenterNodes().add(new LibraryOverviewBox(getItem()));

        return wrapContent(header, detailsContentPane);
    }
}
