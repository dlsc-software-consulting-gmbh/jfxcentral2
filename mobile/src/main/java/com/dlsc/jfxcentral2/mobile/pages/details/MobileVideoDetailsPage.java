package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.VideoOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileVideoDetailsPage extends MobileDetailsPageBase<Video> {

    public MobileVideoDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Video.class, itemId);
    }

    @Override
    public List<Node> content() {
        Video video = getItem();

        // header
        MobileCategoryHeader header = new MobileCategoryHeader(){
            @Override
            protected String goBackLink() {
                return PagePath.VIDEOS;
            }
        };
        header.previewImageProperty().bind(ImageManager.getInstance().youTubeImageProperty(video));
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(video.getName());

        // overview
        VideoOverviewBox videoOverviewBox = new VideoOverviewBox(video);
        videoOverviewBox.sizeProperty().bind(sizeProperty());

        PrettyScrollPane detailsContentPane = new PrettyScrollPane(new StackPane(videoOverviewBox));
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);

        return List.of(header, detailsContentPane);
    }

}