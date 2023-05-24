package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.detailsbox.VideosDetailsBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloDetailsBox2 extends JFXCentralSampleBase {

    private final SizeComboBox sizeComboBox = new SizeComboBox();
    private final Random random = new Random();
    private VideosDetailsBox videosDetailsBox;

    @Override
    protected Region createControl() {

        VideosDetailsBox videosBox = createVideosDetailsBox();
        videosBox.sizeProperty().bind(sizeComboBox.valueProperty());

        return new ScrollPane(new VBox(15, videosBox));
    }

    private List<Video> createVideo() {
        ArrayList<Video> videos = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Video video = new Video();
            //every video need a different id
            video.setId("id-video-" + i);
            video.setName(i + " Video Title");
            video.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
            video.setMinutes(random.nextInt(5, 30));
            videos.add(video);
        }
        return videos;
    }

    private VideosDetailsBox createVideosDetailsBox() {
        videosDetailsBox = new VideosDetailsBox();
        videosDetailsBox.getItems().addAll(createVideo());
        return videosDetailsBox;
    }

    @Override
    public Node getControlPanel() {
        TextField textField = new TextField();
        textField.setPromptText("Select Item (index)");
        textField.setOnAction(e -> {
            ObservableList<Video> items = videosDetailsBox.getItems();
            try {
                int index = Integer.parseInt(textField.getText());
                if (index >= 0 && index < items.size()) {
                    videosDetailsBox.setSelectedItem(items.get(index));
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid index");
            }
        });
        return new VBox(sizeComboBox,new Label("Enter index of the Item"), textField);
    }

    @Override
    public double getControlPanelDividerPosition() {
        return 0.6d;
    }

    @Override
    public String getSampleName() {
        return "DetailsBox";
    }
}
