package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Download;

public class DownloadOverviewBox extends SimpleOverviewBox<Download> {

    public DownloadOverviewBox(Download download) {
        super(download);
        getStyleClass().add("download-overview-box");
        setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "downloads/" + getModel().getId());
    }
}
