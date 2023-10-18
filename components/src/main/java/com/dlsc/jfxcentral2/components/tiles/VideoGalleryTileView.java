package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.jpro.webapi.WebAPI;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class VideoGalleryTileView extends TileView<Video> {

    public VideoGalleryTileView(Video video) {
        super(video);

        getStyleClass().add("video-gallery-tile");

        setButton1Text("PLAY");
        setButton1Graphic(new FontIcon(IkonUtil.play));

        imageProperty().bind(ImageManager.getInstance().youTubeImageProperty(video));

        setRemark(video.getMinutes() + " mins");

        if (WebAPI.isBrowser()) {
            ExternalLinkUtil.setExternalLink(getButton2(), "https://www.youtube.com/watch?v=" + video.getId());
        }
    }
}
