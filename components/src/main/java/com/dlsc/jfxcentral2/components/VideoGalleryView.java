package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.tiles.VideoGalleryTileView;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import com.dlsc.jfxcentral2.utils.VideoViewFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;

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

    private final BooleanProperty blocking = new SimpleBooleanProperty(this, "blocking");

    public boolean isBlocking() {
        return blocking.get();
    }

    public BooleanProperty blockingProperty() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking.set(blocking);
    }

    private final ObjectProperty<Runnable> onCloseGlassPane = new SimpleObjectProperty<>(this, "onCloseGlassPane");

    public Runnable getOnCloseGlassPane() {
        return onCloseGlassPane.get();
    }

    public ObjectProperty<Runnable> onCloseGlassPaneProperty() {
        return onCloseGlassPane;
    }

    public void setOnCloseGlassPane(Runnable onCloseGlasspane) {
        this.onCloseGlassPane.set(onCloseGlasspane);
    }

    @Override
    protected void layoutBySize() {
        contentPane.getChildren().clear();
        Label title = new Label("Video Gallery");
        title.getStyleClass().add("title");

        Button button = new Button("VIEW ALL VIDEOS");
        button.getStyleClass().add("view-all");
        LinkUtil.setLink(button, PagePath.VIDEOS);

        Pane pane = isSmall() ?
                new VBox(title, new Spacer(), button) :
                new HBox(title, new Spacer(), button);
        pane.getStyleClass().add("title-box");
        contentPane.getChildren().add(pane);

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
            HBox videosBox = new HBox();
            videosBox.getStyleClass().add("videos-box");

            VBox centerPlayBox = new VBox(videosBox);
            centerPlayBox.getStyleClass().add("center-play-box");

            int maxItemsPerPage = isLarge() ? 3 : (isMedium() ? 2 : 1);
            int startIndex = index * maxItemsPerPage;
            int endIndex = Math.min(startIndex + maxItemsPerPage, videos.getSize());
            for (int i = startIndex; i < endIndex; i++) {
                Video video = videos.get(i);
                VideoGalleryTileView videoTileView = new VideoGalleryTileView(video);
                videoTileView.sizeProperty().bind(pagination.sizeProperty());
                //Play button action
                videoTileView.getButton1().setOnAction(evt -> {
                    if (OSUtil.isAndroidOrIOS()) {
                        setBlocking(true);
                        setOnCloseGlassPane(() -> {
                            setBlocking(false);
                            setOnCloseGlassPane(null);
//                            YTService.create().ifPresentOrElse(service -> {
//                                service.hide();
//                            }, () -> System.err.println("no youtube service created"));
                        });

//                        YTService.create().ifPresentOrElse(service -> {
//                            service.setPosition(Pos.CENTER, 20, 20, 20, 20);
//                            service.play(video.getId());
//                        }, () -> System.err.println("no youtube service created"));
                    } else {
                        if (centerPlayBox.getChildren().size() > 1) {
                            centerPlayBox.getChildren().remove(1);
                        }
                        centerPlayBox.getChildren().add(VideoViewFactory.createVideoViewNode(video, true));
                    }
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
