package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.jpro.webapi.WebAPI;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class VideoGalleryTileView extends TileView<Video> {

    public VideoGalleryTileView(Video video) {
        super(video);

        getStyleClass().add("video-gallery-tile");

        setButton1Text("PLAY");
        setButton1Graphic(new FontIcon(IkonUtil.play));

        setButton2Text("YouTube");
        setButton2Graphic(new FontIcon(JFXCentralIcon.YOUTUBE));

        setRemark(video.getMinutes() + " mins");

        if (WebAPI.isBrowser()) {
            LinkUtil.setExternalLink(getButton2(), "https://www.youtube.com/watch?v=" + video.getId());
        }
    }
}
