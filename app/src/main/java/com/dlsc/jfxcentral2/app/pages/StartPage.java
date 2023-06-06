package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.HomePageTopView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.VideoGalleryView;
import com.dlsc.jfxcentral2.components.WebsiteChangesView;
import com.dlsc.jfxcentral2.components.WeekLinksLiteView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.QuickLinksGenerator;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

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
        List<LinksOfTheWeek> linksOfTheWeek = DataRepository2.getInstance().getLinksOfTheWeek();

        WeekLinksLiteView weekLinksLiteView = new WeekLinksLiteView();
        weekLinksLiteView.sizeProperty().bind(sizeProperty());
        weekLinksLiteView.setMdString(DataRepository2.getInstance().getLinksOfTheWeekReadMe(linksOfTheWeek.get(linksOfTheWeek.size() - 1)));

        // website changes
        WebsiteChangesView websiteChangesView = new WebsiteChangesView();
        websiteChangesView.sizeProperty().bind(sizeProperty());
        websiteChangesView.getQuickLinks().setAll(QuickLinksGenerator.generateWebsiteChangesQuickLinks(sizeProperty()));
        websiteChangesView.setVisible(DataRepository2.getInstance().getRecentItems().size() > 0);
        websiteChangesView.setManaged(DataRepository2.getInstance().getRecentItems().size() > 0);

        // video gallery
        VideoGalleryView videoGallery = new VideoGalleryView();
        videoGallery.sizeProperty().bind(sizeProperty());
        videoGallery.getVideos().setAll(randomSubList(DataRepository2.getInstance().getVideos(), 12));

        return wrapContent(homePageTopView, weekLinksLiteView, websiteChangesView, videoGallery);
    }
}
