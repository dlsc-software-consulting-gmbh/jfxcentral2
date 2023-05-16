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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VideoGalleryView extends PaneBase {

    private final BorderPane contentPane;

    public VideoGalleryView() {
        getStyleClass().add("video-gallery-view");

        contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");
        getChildren().setAll(contentPane);

        layoutBySize();
        videosProperty().addListener((ob, ov, nv) -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        Label title = new Label("Video Gallery");
        title.getStyleClass().add("title");

        Button button = new Button("VIEW ALL VIDEOS");
        button.getStyleClass().add("view-all");

        Pane pane = isSmall() ?
                new VBox(title, new Spacer(Orientation.VERTICAL), button) :
                new HBox(title, new Spacer(), button);
        pane.getStyleClass().add("title-box");
        contentPane.setTop(pane);

        HBox videosBox = new HBox();
        videosBox.getStyleClass().add("videos-box");

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
            int maxItemsPerPage = isLarge() ? 3 : (isMedium() ? 2 : 1);
            int startIndex = index * maxItemsPerPage;
            int endIndex = Math.min(startIndex + maxItemsPerPage, videos.getSize());
            for (int i = startIndex; i < endIndex; i++) {
                VideoGalleryTileView videoTileView = new VideoGalleryTileView(videos.get(i));
                videoTileView.sizeProperty().bind(pagination.sizeProperty());
                videosBox.getChildren().add(videoTileView);
            }
            return videosBox;
        });

        contentPane.setCenter(pagination);
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
