package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.gridview.VideoGridView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class HelloVideoGridView extends JFXCentralSampleBase {

    private VideoGridView modelGridView;

    @Override
    protected Region createControl() {
        modelGridView = new VideoGridView();
        //modelGridView.setColumns(2);
        // Rows represents the number of loads each time on a small screen
        //modelGridView.setRows(2);
        modelGridView.getItems().setAll(createVideo());
        return new ScrollPane(modelGridView);
    }

    private List<Video> createVideo() {
        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            Video video = new Video();
            video.setName("["+(i+1)+"] Having Fun with Java and JavaFX on the Raspberry Pi | Frank Delporte @ JFX Days 2020");
            video.setDescription("Java, JavaFX and a Raspberry Pi are an ideal combination for any project where you");
            video.setMinutes(6);
            videos.add(video);

        }
        return videos;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        modelGridView.sizeProperty().bind(sizeComboBox.sizeProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "VideoGridView";
    }
}
