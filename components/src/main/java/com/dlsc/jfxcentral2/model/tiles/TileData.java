package com.dlsc.jfxcentral2.model.tiles;

import com.dlsc.jfxcentral2.model.SaveAndLike;
import javafx.scene.image.Image;

public class TileData implements SaveAndLike {

    private String title;
    private String description;
    private Image thumbnail;
    private boolean save;
    private boolean like;
    private String url;

    public TileData() {
    }

    public TileData(String title, String description, Image thumbnail, String url, boolean save, boolean like) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.url = url;
        this.save = save;
        this.like = like;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean isSave() {
        return save;
    }

    @Override
    public void setSave(boolean save) {
        this.save = save;
    }

    @Override
    public boolean isLike() {
        return like;
    }

    @Override
    public void setLike(boolean like) {
        this.like = like;
    }
}
