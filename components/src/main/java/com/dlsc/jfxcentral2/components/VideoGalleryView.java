package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Video;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VideoGalleryView extends PaneBase {

    private static final int TOTAL_ITEMS = 6;

    private final BorderPane contentPane;

    public VideoGalleryView() {
        getStyleClass().add("video-gallery-view");

        contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");
        getChildren().setAll(contentPane);

        layoutBySize();
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
        pagination.setPageCount(TOTAL_ITEMS);
        pagination.setMaxItemsPerPage(isLarge() ? 3 : (isMedium() ? 2 : 1));
        pagination.sizeProperty().bind(sizeProperty());
        pagination.setPageFactory(index -> {
            videosBox.getChildren().clear();
            int itemsInCurrentPage;
            int maxItemsPerPage = pagination.getMaxItemsPerPage();
            int actualPageCount = pagination.getActualPageCount();
            if (index < actualPageCount - 1) {
                itemsInCurrentPage = maxItemsPerPage;
            } else {
                itemsInCurrentPage = pagination.getPageCount() % maxItemsPerPage;
                if (itemsInCurrentPage == 0) {
                    itemsInCurrentPage = maxItemsPerPage;
                }
            }

            for (int i = 0; i < itemsInCurrentPage; i++) {
                //In order to distinguish different tiles during the test, the serial number is also displayed
                int serialNumber = index * pagination.getMaxItemsPerPage() + i + 1;
                VideoGalleryTileView videoTileView = createVideoTileView(serialNumber);
                videoTileView.sizeProperty().bind(pagination.sizeProperty());
                videosBox.getChildren().add(videoTileView);
            }
            return videosBox;
        });

        contentPane.setCenter(pagination);
    }

    private VideoGalleryTileView createVideoTileView(int serialNumber) {
        Video video = new Video(
                "[" + serialNumber + "] Having Fun with Java and JavaFX on the Raspberry Pi | Frank Delporte @ JFX Days 2020",
                "Java, JavaFX and a Raspberry Pi are an ideal combination for any project where you want to connect software and hardware (LEDs, buttons, sensors...) with a beautiful, easy-to-use user interface. In this talk, we will go through the process of building a JavaFX touchscreen user interface to control a relay board and an Arduino with LED strips that were needed to solve the problem of getting my son to the dinner table while he is playing the drums...This includes selecting a Java JDK for Raspberry Pi, setting up a Mosquitto queue, programming the Arduino, understanding the GPIO's on the Raspberry Pi, including a web server, Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut ",
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/video-thumbnail-01.png").toExternalForm()),
                "https://www.youtube.com/watch?v=jmKYWdNbty0",
                false,
                false,
                5);
        return new VideoGalleryTileView(video);
    }
}
