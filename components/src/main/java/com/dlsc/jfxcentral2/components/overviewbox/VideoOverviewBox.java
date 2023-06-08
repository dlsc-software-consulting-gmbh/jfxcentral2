package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.VideoViewFactory;
import javafx.scene.Node;

public class VideoOverviewBox extends SimpleOverviewBox<Video> {

    public VideoOverviewBox(Video video) {
        super(video);
        getStyleClass().add("video-overview-box");

        // there is no markdown for videos, but we might add it in the future,
        // so let's specify the base url ... just to be sure
        setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "videos/" + video.getId());
    }

    @Override
    protected Node createBottomNode() {
        return VideoViewFactory.createVideoViewNode(getModel());
    }
}
