package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral2.model.Video;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class VideoTileView extends TileView<Video> {

    public VideoTileView(Video video) {
        this();
        setData(video);
    }

    public VideoTileView() {
        getStyleClass().add("video-tile-view");

        setButton1Text("PLAY");
        setButton1Graphic(new FontIcon(MaterialDesign.MDI_PLAY));
        setButton1Action(() -> System.out.println("Play video: " + (getData() != null ? getData().getUrl() : "..")));

        setButton2Text("YouTube");
        setButton2Graphic(new FontIcon(MaterialDesign.MDI_YOUTUBE_PLAY));
        setButton2Action(() -> System.out.println("Open YouTube: " + (getData() != null ? getData().getUrl() : "..")));

        dataProperty().addListener((ob, ov, newVideo) -> setRemark(newVideo.getMinutes() + " mins"));
    }

}
