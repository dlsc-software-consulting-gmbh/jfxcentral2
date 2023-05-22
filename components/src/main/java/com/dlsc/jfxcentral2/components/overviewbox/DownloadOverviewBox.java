package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Download;
import javafx.beans.binding.Bindings;

public class DownloadOverviewBox extends SimpleOverviewBox<Download> {

    public DownloadOverviewBox() {
        super();
        getStyleClass().add("download-overview-box");
        baseURLProperty().bind(Bindings.createStringBinding(() -> DataRepository.getInstance().getRepositoryDirectoryURL() + "downloads/" + getData().getId(), dataProperty()));
    }

    public DownloadOverviewBox(Download data) {
        this();
        setData(data);
    }

}
