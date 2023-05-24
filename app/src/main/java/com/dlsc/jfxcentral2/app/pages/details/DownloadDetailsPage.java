package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.DownloadDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.DownloadOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class DownloadDetailsPage extends DetailsPageBase<Download> {

    public DownloadDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Download.class, itemId);
    }

    @Override
    public Node content() {
        // header
        DownloadDetailHeader header = new DownloadDetailHeader(getItem());
        header.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(new DownloadOverviewBox(getItem()));
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }
}
