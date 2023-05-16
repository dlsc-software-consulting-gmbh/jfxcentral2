package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.tiles.VideoTileView;

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
