package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.Video;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;

public class VideoGalleryView extends PaneBase {

    private final VBox contentPane;

    public VideoGalleryView() {
        getStyleClass().add("video-gallery-view");

        contentPane = new VBox();
        contentPane.getStyleClass().add("content-pane");
        getChildren().setAll(contentPane);
        setMaxHeight(USE_PREF_SIZE);
        layoutBySize();
        videosProperty().addListener((ob, ov, nv) -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        contentPane.getChildren().clear();
        Label title = new Label("Video Gallery");
        title.getStyleClass().add("title");

        Button button = new Button("VIEW ALL VIDEOS");
        button.getStyleClass().add("view-all");
        LinkUtil.setLink(button, "/videos");

        Pane pane = isSmall() ?
                new VBox(title, new Spacer(Orientation.VERTICAL), button) :
                new HBox(title, new Spacer(), button);
        pane.getStyleClass().add("title-box");
        contentPane.getChildren().add(pane);

        HBox videosBox = new HBox();
        videosBox.getStyleClass().add("videos-box");

        VBox centerPlayBox = new VBox(videosBox);
        centerPlayBox.getStyleClass().add("center-play-box");

        PaginationControl pagination = new PaginationControl();
        pagination.pageCountProperty().bind(Bindings.createIntegerBinding(() -> {
            int pageCount;
            if (isLarge()) {
                pageCount = (int) Math.ceil(videosProperty().sizeProperty().get() / 3.0);
            } else if (isMedium()) {
                pageCount = (int) Math.ceil(videosProperty().sizeProperty().get() / 2.0);
            } else {
                pageCount = videosProperty().sizeProperty().get();
            }
            return pageCount;
        }, videosProperty().sizeProperty(), sizeProperty()));

        pagination.sizeProperty().bind(sizeProperty());
        pagination.setPageFactory(index -> {
            videosBox.getChildren().clear();
            centerPlayBox.getChildren().removeIf(node -> node != videosBox);
            int maxItemsPerPage = isLarge() ? 3 : (isMedium() ? 2 : 1);
            int startIndex = index * maxItemsPerPage;
            int endIndex = Math.min(startIndex + maxItemsPerPage, videos.getSize());
            for (int i = startIndex; i < endIndex; i++) {
                VideoGalleryTileView videoTileView = new VideoGalleryTileView(videos.get(i));
                videoTileView.sizeProperty().bind(pagination.sizeProperty());
                //Play button action
                videoTileView.setButton1Action(() -> {
                    centerPlayBox.getChildren().removeIf(node -> node != videosBox);

                    //play video
                    StackPane videoPane = new StackPane();
                    videoPane.setMinSize(200, 260);
                    videoPane.setStyle("-fx-background-color: black;");
                    centerPlayBox.getChildren().add(videoPane);
                });
                videosBox.getChildren().add(videoTileView);
            }

            return centerPlayBox;
        });

        contentPane.getChildren().add(pagination);
    }

    private final ListProperty<Video> videos = new SimpleListProperty<>(this, "videos", FXCollections.observableArrayList());

    public ObservableList<Video> getVideos() {
        return videos.get();
    }

    public ListProperty<Video> videosProperty() {
        return videos;
    }

    public void setVideos(ObservableList<Video> videos) {
        this.videos.set(videos);
    }
}
