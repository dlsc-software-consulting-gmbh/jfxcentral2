package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

/**
 *   for a video.
 */
public class Video extends TileData {
    private int minutes;

    public Video() {
    }

    public Video(String title, String description, Image thumbnail, String url, boolean save, boolean like, int minutes) {
        super(title, description, thumbnail, url,save, like);
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
