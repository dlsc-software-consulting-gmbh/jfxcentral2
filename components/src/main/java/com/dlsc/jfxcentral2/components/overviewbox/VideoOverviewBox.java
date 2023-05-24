package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.VideoViewFactory;
import javafx.scene.Node;

public class VideoOverviewBox extends SimpleOverviewBox<Video> {

    public VideoOverviewBox(Video data) {
        super(data);
        getStyleClass().add("video-overview-box");
    }

    @Override
    protected Node createBottomNode() {
        return VideoViewFactory.createViewViewNode(getModel());
    }
}
