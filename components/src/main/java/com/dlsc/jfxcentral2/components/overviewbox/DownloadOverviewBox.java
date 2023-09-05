package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Download;

/*
 * We do not display an image in the overview box for downloads. Not needed.
 */
public class DownloadOverviewBox extends SimpleOverviewBox<Download> {

    public DownloadOverviewBox(Download download) {
        super(download);
        getStyleClass().add("download-overview-box");
        setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "downloads/" + getModel().getId());
        setMarkdown(DataRepository2.getInstance().getDownloadReadMe(download));
    }
}
