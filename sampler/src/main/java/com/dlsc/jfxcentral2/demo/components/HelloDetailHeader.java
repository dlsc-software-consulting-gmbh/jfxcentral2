package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.headers.AppDetailHeader;
import com.dlsc.jfxcentral2.components.headers.BookDetailHeader;
import com.dlsc.jfxcentral2.components.headers.PeopleDetailHeader;
import com.dlsc.jfxcentral2.components.headers.PersonDetailHeader;
import com.dlsc.jfxcentral2.components.headers.VideoDetailHeader;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.App;
import com.dlsc.jfxcentral2.model.Book;
import com.dlsc.jfxcentral2.model.SimpleHeaderInfo;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloDetailHeader extends JFXCentralSampleBase {

    private final SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);

    @Override
    protected Region createControl() {
        PersonDetailHeader personDetailHeader = new PersonDetailHeader(getPerson());
        personDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());

        AppDetailHeader appDetailHeader = new AppDetailHeader(getApp());
        appDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());

        BookDetailHeader bookDetailHeader = new BookDetailHeader(getBook());
        bookDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());

        VideoDetailHeader videoDetailHeader = new VideoDetailHeader();
        videoDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());

        PeopleDetailHeader peopleDetailHeader = new PeopleDetailHeader();
        peopleDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());

        return new ScrollPane(new VBox(30, personDetailHeader, appDetailHeader, bookDetailHeader, videoDetailHeader, peopleDetailHeader));
    }

    private SimpleHeaderInfo getBook() {
        return new Book("The Definitive Guide to Modern Java Clients with JavaFX", "Leveraging the JavaFX APIs", "https://www.jfx-central.com/home", true, false);
    }

    private App getApp() {
        return new App("JFXCentral", "https://www.jfx-central.com/home", false, true);
    }

    private Person getPerson() {
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
        return person;
    }

    @Override
    public Node getControlPanel() {
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "DetailHeader";
    }
}
