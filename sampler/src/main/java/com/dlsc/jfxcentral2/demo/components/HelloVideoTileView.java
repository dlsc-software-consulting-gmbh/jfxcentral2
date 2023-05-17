package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.VideoTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloVideoTileView extends JFXCentralSampleBase {

    private VideoTileView tileView;

    @Override
    protected Region createControl() {
        Video video = new Video();
        video.setName("JavaFX on Raspberry Pi");
        video.setDescription("Java, JavaFX and a Raspberry Pi are an ideal combination for any project where you want to connect software and hardware (LEDs, buttons, sensors...) with a beautiful, easy-to-use user interface. In this talk, we will go through the process of building a JavaFX touchscreen user interface to control a relay board and an Arduino with LED strips that were needed to solve the problem of getting my son to the dinner table while he is playing the drums...This includes selecting a Java JDK for Raspberry Pi, setting up a Mosquitto queue, programming the Arduino, understanding the GPIO's on the Raspberry Pi, including a web server, Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut ");
        video.setMinutes(5);
        tileView = new VideoTileView(video);
        return new StackPane(tileView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        tileView.sizeProperty().bind(sizeComboBox.valueProperty());

        CheckBox smallCheckBox = new CheckBox("Video Gallery Tile");
        smallCheckBox.selectedProperty().addListener(it -> {
            // css selector: .video-tile-view:sm.small or .tile-view:sm.small
            if (smallCheckBox.isSelected() && !tileView.getStyleClass().contains("video-gallery-tile")) {
                tileView.getStyleClass().add("video-gallery-tile");
            } else {
                tileView.getStyleClass().remove("video-gallery-tile");
            }
        });
        return new VBox(10, smallCheckBox, sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "VideoTileView";
    }
}
