package com.dlsc.jfxcentral2.demo.components.tileview;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.PersonTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.List;

public class HelloPersonTileView extends JFXCentralSampleBase {

    private PersonTileView personTileView;

    @Override
    protected Region createControl() {
        Person person = new Person();
        person.setName("Very long Name and lo");
        person.setChampion(true);
        person.setRockstar(true);
        person.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor ");
        person.setLinkedIn("dlemmermann");
        person.setWebsite("https://www.dlsc.com");
        person.setEmail("dlemmermann@gmail.com");
        person.setGitHub("https://github.com/dlsc-software-consulting-gmbh");
        person.setBookIds(List.of("Book1", "Book2", "Book3"));
        person.setDownloadIds(List.of("Download1", "Download2", "Download3"));
        person.setVideoIds(List.of("Video1", "Video2", "Video3"));
        personTileView = new PersonTileView(person);
        return new StackPane(personTileView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        personTileView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "PersonTileView";
    }
}
