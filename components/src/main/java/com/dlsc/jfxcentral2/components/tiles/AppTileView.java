package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class AppTileView extends TileView<RealWorldApp> {

    public AppTileView(RealWorldApp app) {
        this();
        setData(app);
    }

    public AppTileView() {
        getStyleClass().add("app-tile-view");

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        setButton1Action(() -> System.out.println("Discover app: " + (getData() != null ? getData().getUrl() : "..")));

    }
}
