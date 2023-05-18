package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.model.Download;

public class DownloadOverviewBox extends SimpleOverviewBox<Download> {

    public DownloadOverviewBox() {
        super();
        getStyleClass().add("download-overview-box");
    }

    public DownloadOverviewBox(Download data) {
        this();
        setData(data);
    }

}
