package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;

public class DownloadDetailsPage extends DetailsPageBase<Download> {

    public DownloadDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Download.class, itemId);
    }
}
