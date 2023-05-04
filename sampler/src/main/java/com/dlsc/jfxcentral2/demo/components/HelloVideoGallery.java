package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.VideoGallery;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloVideoGallery extends JFXCentralSampleBase {

    private VideoGallery videoGallery;

    @Override
    protected Region createControl() {
        videoGallery = new VideoGallery();

        ScrollPane scrollPane = new ScrollPane(new StackPane(videoGallery));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
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
