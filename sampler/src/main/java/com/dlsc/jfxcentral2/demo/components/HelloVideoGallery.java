package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.VideoGalleryView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Video;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class HelloVideoGallery extends JFXCentralSampleBase {

    private VideoGalleryView videoGallery;

    @Override
    protected Region createControl() {
        videoGallery = new VideoGalleryView();
        videoGallery.getVideos().setAll(createVideos());
        ScrollPane scrollPane = new ScrollPane(new StackPane(videoGallery));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    private List<Video> createVideos() {
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            Video video = new Video(
                    (i+1)+" Having Fun with Java and JavaFX on the Raspberry Pi | Frank Delporte @ JFX Days 2020",
                    "Java, JavaFX and a Raspberry Pi are an ideal combination for any project where you want to connect software and hardware (LEDs, buttons, sensors...) with a beautiful, easy-to-use user interface. In this talk, we will go through the process of building a JavaFX touchscreen user interface to control a relay board and an Arduino with LED strips that were needed to solve the problem of getting my son to the dinner table while he is playing the drums...This includes selecting a Java JDK for Raspberry Pi, setting up a Mosquitto queue, programming the Arduino, understanding the GPIO's on the Raspberry Pi, including a web server, Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut ",
                    new Image(VideoGalleryView.class.getResource("/com/dlsc/jfxcentral2/demoimages/video-thumbnail-01.png").toExternalForm()),
                    "https://www.youtube.com/watch?v=jmKYWdNbty0",
                    false,
                    false,
                    5);
            list.add(video);
        }
        return list;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        videoGallery.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "VideoGallery";
    }
}
