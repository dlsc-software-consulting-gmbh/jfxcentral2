package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral2.model.tiles.TileData;
import javafx.scene.image.Image;

public class Download extends TileData {

    public Download() {
    }

    public Download(String title, String description, Image thumbnail, String url, boolean save, boolean like) {
        super(title, description, thumbnail, url, save, like);
    }
}
