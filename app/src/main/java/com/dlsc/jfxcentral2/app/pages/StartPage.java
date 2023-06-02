package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.HomePageTopView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.VideoGalleryView;
import com.dlsc.jfxcentral2.components.WebsiteChangesView;
import com.dlsc.jfxcentral2.components.WeekLinksLiteView;
import com.dlsc.jfxcentral2.model.DateQuickLink;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StartPage extends PageBase {

    public StartPage(ObjectProperty<Size> size) {
        super(size, TopMenuBar.Mode.DARK);
    }

    @Override
    public String title() {
        return "JFXCentral";
    }

    @Override
    public String description() {
        return "A central place for anything related to JavaFX.";
    }

    @Override
    public Node content() {
        // HomePage TopView
        HomePageTopView homePageTopView = new HomePageTopView();
        homePageTopView.sizeProperty().bind(sizeProperty());

        // links of the Week
        ObservableList<LinksOfTheWeek> linksOfTheWeek = DataRepository.getInstance().getLinksOfTheWeek();

        WeekLinksLiteView weekLinksLiteView = new WeekLinksLiteView();
        weekLinksLiteView.sizeProperty().bind(sizeProperty());
        weekLinksLiteView.mdStringProperty().bind(DataRepository.getInstance().linksOfTheWeekTextProperty(linksOfTheWeek.get(linksOfTheWeek.size() - 1)));

        // website changes
        WebsiteChangesView websiteChangesView = new WebsiteChangesView();
        websiteChangesView.sizeProperty().bind(sizeProperty());
//        websiteChangesView.getQuickLinks().setAll(generateQuickLinks());

        // video gallery
        VideoGalleryView videoGallery = new VideoGalleryView();
        videoGallery.sizeProperty().bind(sizeProperty());
        videoGallery.getVideos().setAll(randomSubList(DataRepository.getInstance().getVideos(), 12));

        return wrapContent(homePageTopView, weekLinksLiteView, websiteChangesView, videoGallery);
    }

    private List<QuickLink> generateQuickLinks() {
        List<QuickLink> quickLinks = new ArrayList<>();
        if (getSize() != Size.SMALL) {
            Random random = new Random();
            int imageQuickLinkCount = random.nextInt(2) + 2;
            int dateQuickLinkCount = random.nextInt(2) + 3;
            int nullCount = 7 - imageQuickLinkCount - dateQuickLinkCount;

            //normal QuickLinks
            for (int i = 0; i < dateQuickLinkCount; i++) {
                quickLinks.add(new DateQuickLink("JDKMon", "Download", null, "xxx url...", ZonedDateTime.now().plusDays(i)));
            }

            //image QuickLinks
            for (int i = 0; i < imageQuickLinkCount; i++) {
                String imgUrl = "/com/dlsc/jfxcentral2/test/images/" + (getSize() == Size.LARGE ? "quick-link-lg" : "website-changes-view-md") + i + ".png";
                quickLinks.add(new ImageQuickLink(ImageQuickLink.class.getResource(imgUrl).toExternalForm()));
            }

            //empty QuickLinks
            for (int i = 0; i < nullCount; i++) {
                quickLinks.add(null);
            }
            Collections.shuffle(quickLinks);
        }else { //small size

            //normal QuickLinks
            for (int i = 0; i < 3; i++) {
                quickLinks.add(new DateQuickLink("JDKMon", "Download", null, "xxx url...", ZonedDateTime.now().plusDays(i)));
            }
        }

        return quickLinks;
    }
}
