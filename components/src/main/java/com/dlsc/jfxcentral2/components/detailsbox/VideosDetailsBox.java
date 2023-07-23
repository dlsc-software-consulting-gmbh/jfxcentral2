package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.VideoViewFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class VideosDetailsBox extends DetailsBoxBase<Video> {

    public VideosDetailsBox() {
        getStyleClass().addAll("videos-details-box");
        setTitle("VIDEOS");
        setIkon(IkonUtil.getModelIkon(Video.class));
        setMaxItemsPerPage(3);
        setExtrasProvider(video -> VideoViewFactory.createVideoViewNode(video, true));
    }

    @Override
    protected List<Node> createActionButtons(Video video) {
        Button playButton = new Button("PLAY", new FontIcon(IkonUtil.play));
        playButton.setFocusTraversable(false);
        playButton.getStyleClass().add("play-button");
        playButton.managedProperty().bind(playButton.visibleProperty());
        playButton.setOnAction(evt -> setSelectedItem(video));

        Button youTubeButton = new Button("YouTube");
        youTubeButton.setFocusTraversable(false);
        youTubeButton.getStyleClass().add("youtube-button");
        LinkUtil.setExternalLink(youTubeButton, "https://youtu.be/" + video.getId(), "https://youtu.be/" + video.getId());

        return List.of(playButton, youTubeButton);
    }
}
