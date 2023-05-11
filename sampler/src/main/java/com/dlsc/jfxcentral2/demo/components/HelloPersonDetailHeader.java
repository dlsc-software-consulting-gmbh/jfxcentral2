package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.headers.PersonDetailHeader;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloPersonDetailHeader extends JFXCentralSampleBase {

    private PersonDetailHeader personDetailHeader;

    @Override
    protected Region createControl() {

        Person person = new Person();
        person.setName("Dirk Lemmermann");
        person.setDescription("JavaFX Fan Boy. Java Champion. Java One Rockstar. Open Source Contributor. Freelancer. Consultant. Speaker. Entrepreneur.");
        person.setId("d.lemmermann");
        person.setEmail("dlemmermann@gmail.com");
        person.setWebsite("https://www.dlsc.com");
        person.setLinkedIn("https://www.linkedin.com/in/dlemmermann");
        person.setGitHub("https://github.com/dlemmermann");
        person.setTwitter("https://twitter.com/dlemmermann");
        person.setChampion(true);
        person.setRockstar(true);

        personDetailHeader = new PersonDetailHeader(person);

        return new ScrollPane(new StackPane(personDetailHeader));
    }

    @Override
    public Node getControlPanel() {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton largeBox = new RadioButton("Large");
        RadioButton mediumBox = new RadioButton("Medium");
        RadioButton smallBox = new RadioButton("Small");
        toggleGroup.getToggles().addAll(largeBox, mediumBox, smallBox);
        toggleGroup.selectedToggleProperty().addListener((ob, ov, nv) -> {
            if (nv == largeBox) {
                personDetailHeader.setSize(Size.LARGE);
            } else if (nv == mediumBox) {
                personDetailHeader.setSize(Size.MEDIUM);
            } else {
                personDetailHeader.setSize(Size.SMALL);
            }
        });
        mediumBox.setSelected(true);
        return new VBox(20, largeBox, mediumBox, smallBox);
    }

    @Override
    public String getSampleName() {
        return "PersonDetailHeader";
    }
}
