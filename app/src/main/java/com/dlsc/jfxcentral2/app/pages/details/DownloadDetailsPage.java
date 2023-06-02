package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.DownloadsBox;
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
        Download download = getItem();

        // header
        DownloadDetailHeader header = new DownloadDetailHeader(download);
        header.sizeProperty().bind(sizeProperty());

        // overview
        DownloadOverviewBox downloadOverviewBox = new DownloadOverviewBox(download);
        downloadOverviewBox.sizeProperty().bind(sizeProperty());

        // downloads
        DownloadsBox downloadsBox = new DownloadsBox(download);
        downloadsBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().addAll(downloadOverviewBox, downloadsBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }
}
