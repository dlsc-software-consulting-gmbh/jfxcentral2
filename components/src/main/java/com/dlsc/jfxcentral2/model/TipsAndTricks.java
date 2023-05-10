package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral2.model.tiles.TileData;
import javafx.scene.image.Image;

import java.time.ZonedDateTime;

public class TipsAndTricks extends TileData {

    private ZonedDateTime date;

    public TipsAndTricks() {
    }

    public TipsAndTricks(String title, String description, Image thumbnail, String url, boolean save, boolean like, ZonedDateTime date) {
        super(title, description, thumbnail, url, save, like);
        this.date = date;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
