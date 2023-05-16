package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;

public class LibraryDetailsPage extends DetailsPageBase<Library> {

    public LibraryDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Library.class, itemId);
    }
}
