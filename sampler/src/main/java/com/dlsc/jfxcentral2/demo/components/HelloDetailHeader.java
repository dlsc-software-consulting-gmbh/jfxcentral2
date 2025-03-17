package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.headers.BookDetailHeader;
import com.dlsc.jfxcentral2.components.headers.IconDetailHeader;
import com.dlsc.jfxcentral2.components.headers.PersonDetailHeader;
import com.dlsc.jfxcentral2.components.headers.ToolDetailHeader;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloDetailHeader extends JFXCentralSampleBase {

    private final SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);

    @Override
    protected Region createControl() {
        PersonDetailHeader personDetailHeader = new PersonDetailHeader(getPerson());
        personDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());

//        AppDetailHeader appDetailHeader = new AppDetailHeader(getApp());
//        appDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());
//        appDetailHeader.setBackgroundImage(new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/quick-link-lg2.png").toExternalForm()));

        BookDetailHeader bookDetailHeader = new BookDetailHeader(getBook());
        bookDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());

        IconDetailHeader iconDetailHeader = new IconDetailHeader(getIcon());
        iconDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());

        ToolDetailHeader toolDetailHeader = new ToolDetailHeader(getTool());
        toolDetailHeader.sizeProperty().bind(sizeComboBox.valueProperty());
        toolDetailHeader.setImage(new Image(ToolDetailHeader.class.getResource("/com/dlsc/jfxcentral2/images/duke.png").toExternalForm()));

        return new ScrollPane(new VBox(30, personDetailHeader, bookDetailHeader, toolDetailHeader));
    }

    private Tool getTool() {
        Tool tool = new Tool();
        tool.setName("JFXCentral");
        tool.setDescription("JFXCentral is a community for JavaFX, gathering all news, tools, apps, blogs, videos, talks, podcasts, books, and training related to the JavaFX ecosystem.");
        return tool;
    }

    private IkonliPack getIcon() {
        IkonliPack icon = new IkonliPack();
        icon.setName("Icons");
        icon.setUrl("https://www.jfx-central.com/");
        return icon;
    }

    private Book getBook() {
        Book book = new Book();
        book.setName("The Definitive Guide to Modern Java Clients with JavaFX");
        book.setDescription("Leveraging the JavaFX APIs");
        return book;
    }

    private RealWorldApp getApp() {
        RealWorldApp app = new RealWorldApp();
        app.setName("JFXCentral");
        app.setUrl("https://www.jfx-central.com/home");
        return app;
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
