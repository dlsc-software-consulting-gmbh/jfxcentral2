package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.VideoDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.VideoOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class VideoDetailsPage extends DetailsPageBase<Video> {

    public VideoDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Video.class, itemId);
    }

    @Override
    public Node content() {
        Video video = getItem();

        // header
        VideoDetailHeader header = new VideoDetailHeader(video);
        header.sizeProperty().bind(sizeProperty());

        // overview
        VideoOverviewBox videoOverviewBox = new VideoOverviewBox(video);
        videoOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(videoOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }

}
