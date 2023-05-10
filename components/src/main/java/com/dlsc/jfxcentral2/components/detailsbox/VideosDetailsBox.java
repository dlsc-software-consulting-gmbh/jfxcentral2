package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.VideosDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignV;

import java.util.List;
import java.util.function.Consumer;

public class VideosDetailsBox extends DetailsBoxBase<VideosDetailsObject> {

    public VideosDetailsBox() {
        getStyleClass().addAll("videos-details-box");
        setTitle("VIDEOS");
        setIkon(MaterialDesignV.VIDEO_OUTLINE);
        setMaxItemsPerPage(3);

        setOnPlay(detailsObject -> {
            System.out.println("On Play: " + detailsObject.getTitle());
        });

        setOnYoutube(detailsObject -> {
            System.out.println("On Youtube: " + detailsObject.getTitle());
        });
    }

    @Override
    protected List<Node> createActionButtons(VideosDetailsObject model) {
        Button playButton = new Button("PLAY", new FontIcon(MaterialDesign.MDI_PLAY));
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

    private final ObjectProperty<Consumer<VideosDetailsObject>> onPlay = new SimpleObjectProperty<>(this, "onPlay");

    public Consumer<VideosDetailsObject> getOnPlay() {
        return onPlay.get();
    }

    public ObjectProperty<Consumer<VideosDetailsObject>> onPlayProperty() {
        return onPlay;
    }

    public void setOnPlay(Consumer<VideosDetailsObject> onPlay) {
        this.onPlay.set(onPlay);
    }

    private final ObjectProperty<Consumer<VideosDetailsObject>> onYoutube = new SimpleObjectProperty<>(this, "onYoutube");

    public Consumer<VideosDetailsObject> getOnYoutube() {
        return onYoutube.get();
    }

    public ObjectProperty<Consumer<VideosDetailsObject>> onYoutubeProperty() {
        return onYoutube;
    }

    public void setOnYoutube(Consumer<VideosDetailsObject> onYoutube) {
        this.onYoutube.set(onYoutube);
    }
}
