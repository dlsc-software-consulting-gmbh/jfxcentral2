package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Download;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class DownloadTileView extends TileView<Download>{

    public DownloadTileView(Download download) {
        this();
        setData(download);
    }

    public DownloadTileView() {
        getStyleClass().add("download-tile-view");

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        setButton1Action(() -> System.out.println("Discover download: " + (getData() != null ? getData().getUrl() : "..")));

        setButton2Text("DOWNLOAD");
        setButton2Graphic(new FontIcon(MaterialDesign.MDI_DOWNLOAD));
        setButton2Action(() -> System.out.println("Download: " + (getData() != null ? getData().getUrl() : "..")));
    }
}
