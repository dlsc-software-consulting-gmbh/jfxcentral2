package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.utils.IkonUtil;
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
        setButton1Graphic(new FontIcon(IkonUtil.link));
        setButton1Action(() -> System.out.println("Discover download: " + (getData() != null ? getData().getName() : "..")));

        setButton2Text("DOWNLOAD");
        setButton2Graphic(new FontIcon(IkonUtil.download));
        setButton2Action(() -> System.out.println("Download: " + (getData() != null ? getData().getName() : "..")));
    }
}
