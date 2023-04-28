package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.AppTileData;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class AppTileView extends TileView<AppTileData> {

    public AppTileView(AppTileData app) {
        this();
        setData(app);
    }

    public AppTileView() {
        getStyleClass().add("app-tile-view");

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        setButton1Action(() -> System.out.println("Discover app: " + (getData() != null ? getData().getUrl() : "..")));

    }
}
