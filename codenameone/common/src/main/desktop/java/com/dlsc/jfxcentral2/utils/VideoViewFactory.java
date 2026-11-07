package com.dlsc.jfxcentral2.utils;

import com.codename1.ui.CN;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.icons.FontIcon;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/// A video is its YouTube thumbnail with a play badge; a tap hands the video to
/// the YouTube app or the browser. The desktop app embedded a WebView player,
/// which a phone does better natively.
public class VideoViewFactory {

    public static Node createVideoViewNode(Video video) {
        return createVideoViewNode(video, false);
    }

    public static Node createVideoViewNode(Video video, boolean closeable) {
        ImageView thumbnail = new ImageView();
        thumbnail.imageProperty().bind(ImageManager.getInstance().youTubeImageProperty(video));
        thumbnail.setPreserveRatio(true);

        FontIcon play = new FontIcon(IkonUtil.play);
        play.getStyleClass().add("video-play-icon");

        StackPane pane = new StackPane(thumbnail, play);
        pane.getStyleClass().add("video-view");
        thumbnail.fitWidthProperty().bind(pane.widthProperty());
        pane.setMinHeight(Region.USE_PREF_SIZE);
        pane.setOnMouseClicked(evt -> CN.execute("https://www.youtube.com/watch?v=" + video.getId()));
        return pane;
    }
}
