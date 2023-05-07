package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Video;

public class VideoGalleryTileView extends VideoTileView {

    public VideoGalleryTileView(Video video) {
        this();
        setData(video);
    }

    public VideoGalleryTileView() {
        super();
        getStyleClass().add("video-gallery-tile");
    }

}
