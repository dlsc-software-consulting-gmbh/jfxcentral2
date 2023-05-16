package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.function.Consumer;

public class VideosDetailsBox extends DetailsBoxBase<Video> {

    public VideosDetailsBox() {
        getStyleClass().addAll("videos-details-box");
        setTitle("VIDEOS");
        setIkon(IkonUtil.video);
        setMaxItemsPerPage(3);

        setOnPlay(detailsObject -> {
            System.out.println("On Play: " + detailsObject.getName());
        });

        setOnYoutube(detailsObject -> {
            System.out.println("On Youtube: " + detailsObject.getName());
        });
    }

    @Override
    protected List<Node> createActionButtons(Video model) {
        Button playButton = new Button("PLAY", new FontIcon(IkonUtil.play));
        playButton.getStyleClass().add("play-button");
        playButton.managedProperty().bind(playButton.visibleProperty());
        playButton.visibleProperty().bind(onPlayProperty().isNotNull());
        playButton.setOnAction(evt -> {
            if (getOnPlay() != null) {
                getOnPlay().accept(model);
            }
        });

        Button youtubeButton = new Button("Youtube");
        youtubeButton.getStyleClass().add("youtube-button");
        youtubeButton.managedProperty().bind(youtubeButton.visibleProperty());
        youtubeButton.visibleProperty().bind(onYoutubeProperty().isNotNull());
        youtubeButton.setOnAction(evt -> {
            if (getOnYoutube() != null) {
                getOnYoutube().accept(model);
            }
        });

        return List.of(playButton, youtubeButton);
    }

    private final ObjectProperty<Consumer<Video>> onPlay = new SimpleObjectProperty<>(this, "onPlay");

    public Consumer<Video> getOnPlay() {
        return onPlay.get();
    }

    public ObjectProperty<Consumer<Video>> onPlayProperty() {
        return onPlay;
    }

    public void setOnPlay(Consumer<Video> onPlay) {
        this.onPlay.set(onPlay);
    }

    private final ObjectProperty<Consumer<Video>> onYoutube = new SimpleObjectProperty<>(this, "onYoutube");

    public Consumer<Video> getOnYoutube() {
        return onYoutube.get();
    }

    public ObjectProperty<Consumer<Video>> onYoutubeProperty() {
        return onYoutube;
    }

    public void setOnYoutube(Consumer<Video> onYoutube) {
        this.onYoutube.set(onYoutube);
    }
}
