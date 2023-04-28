package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.HomePageTopView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.WebsiteChangesView;
import com.dlsc.jfxcentral2.components.WeekLinksLiteView;
import com.dlsc.jfxcentral2.model.DateQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StartPage extends DefaultPage {

    @Override
    public String title() {
        return "title";
    }

    @Override
    public String description() {
        return "description";
    }

    public StartPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public Node content() {
        // HomePage TopView
        HomePageTopView homePageTopView = new HomePageTopView();
        homePageTopView.sizeProperty().bind(sizeProperty());

        // links of the Week
        WeekLinksLiteView weekLinksLiteView = new WeekLinksLiteView();
        weekLinksLiteView.sizeProperty().bind(sizeProperty());
        weekLinksLiteView.setMdString("""
                #### Hello World
                Foojay.io, the website for Friends Of OpenJDK, published the podcast ‘The State of JavaFX Framework, Libraries, and Projects’. These guests spoke about the JavaFX framework itself, but also about the libraries and applications that are built with it:
                > Pedro Duque Vieira
                                
                Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n
                Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n""");

        // website changes
        WebsiteChangesView websiteChangesView = new WebsiteChangesView();
        websiteChangesView.sizeProperty().bind(sizeProperty());
        websiteChangesView.getQuickLinks().setAll(generateQuickLinks());

        VBox uiBox = new VBox(homePageTopView, weekLinksLiteView, websiteChangesView);
        uiBox.setAlignment(Pos.BOTTOM_CENTER);
        uiBox.setMaxWidth(Region.USE_PREF_SIZE);

        StackPane.setAlignment(uiBox, Pos.TOP_CENTER);

        return wrapContent(uiBox);
    }

    private List<QuickLink> generateQuickLinks() {
        List<QuickLink> quickLinks = new ArrayList<>();
        Random random = new Random();
        int imageQuickLinkCount = random.nextInt(2) + 2;
        int dateQuickLinkCount = random.nextInt(2) + 3;
        int nullCount = 7 - imageQuickLinkCount - dateQuickLinkCount;

        for (int i = 0; i < dateQuickLinkCount; i++) {
            quickLinks.add(new DateQuickLink("JDKMon", "Download", null, "xxx url...", ZonedDateTime.now().plusDays(i)));
        }
        for (int i = 0; i < nullCount; i++) {
            quickLinks.add(null);
        }
        Collections.shuffle(quickLinks);
        return quickLinks;
    }
}
