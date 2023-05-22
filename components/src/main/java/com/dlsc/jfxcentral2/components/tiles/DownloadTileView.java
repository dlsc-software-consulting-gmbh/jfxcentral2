package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class DownloadTileView extends TileView<Download>{

    public DownloadTileView() {
        getStyleClass().add("download-tile-view");

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));

        setButton2Text("DOWNLOAD");
        setButton2Graphic(new FontIcon(IkonUtil.download));

        dataProperty().addListener(it -> {
            Download download = getData();
            if (download != null) {
                LinkUtil.setLink(getButton1(), "/downloads/" + download.getId());
            }
        });
    }
}
