package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.VideoViewFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class VideosDetailsBox extends DetailsBoxBase<Video> {

    public VideosDetailsBox() {
        getStyleClass().addAll("videos-details-box");
        setTitle("VIDEOS");
        setIkon(IkonUtil.getModelIkon(Video.class));
        setMaxItemsPerPage(3);
        if (!OSUtil.isNative()) {
            setExtrasProvider(video -> VideoViewFactory.createVideoViewNode(video, true));
        }
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
        ExternalLinkUtil.setExternalLink(youTubeButton, "https://youtu.be/" + video.getId(), "https://youtu.be/" + video.getId());

        if (OSUtil.isNative()) {
            return List.of(youTubeButton);
        }
        return List.of(playButton, youTubeButton);
    }
}
