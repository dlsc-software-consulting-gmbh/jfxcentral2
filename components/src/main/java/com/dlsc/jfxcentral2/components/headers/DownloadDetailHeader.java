package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Download;

public class DownloadDetailHeader extends SimpleDetailHeader<Download>  {

    public DownloadDetailHeader(Download download) {
        super(download);

        getStyleClass().add("download-detail-header");

        setWebsite(getModel().getHomepage());
        setShareUrl("downloads/" + download.getId());
        setShareText("Found this download on @JFXCentral: " + download.getName());
        setShareTitle("Download: " + download.getName());
        setBackText("ALL DOWNLOADS");
        setBackUrl("/downloads");
    }
}
