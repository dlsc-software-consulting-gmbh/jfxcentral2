package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.WebsiteChangesView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.DateQuickLink;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HelloWebsiteChangesView extends JFXCentralSampleBase {

    private WebsiteChangesView viewLarger;
    private WebsiteChangesView viewMedium;
    private WebsiteChangesView viewSmall;

    @Override
    protected Region createControl() {
        viewLarger = new WebsiteChangesView();
        viewLarger.getQuickLinks().setAll(generateQuickLinks(Size.LARGE));

        viewMedium = new WebsiteChangesView();
        viewMedium.setSize(Size.MEDIUM);
        viewMedium.getQuickLinks().setAll(generateQuickLinks(Size.MEDIUM));

        viewSmall = new WebsiteChangesView();
        viewSmall.setSize(Size.SMALL);
        viewSmall.getQuickLinks().setAll(generateQuickLinks(Size.SMALL));

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(
                new Tab("Small", viewSmall),
                new Tab("Medium", viewMedium),
                new Tab("Larger", viewLarger));
        return tabPane;
    }

    @Override
    public Node getControlPanel() {
        Button button = new Button("Refresh");
        button.setOnAction(event -> {
            viewLarger.setQuickLinks(FXCollections.observableList(generateQuickLinks(Size.LARGE)));
            viewMedium.setQuickLinks(FXCollections.observableList(generateQuickLinks(Size.MEDIUM)));
            viewSmall.setQuickLinks(FXCollections.observableList(generateQuickLinks(Size.SMALL)));
        });
        return new VBox(20, button);
    }

    private List<QuickLink> generateQuickLinks(Size size) {
        List<QuickLink> quickLinks = new ArrayList<>();
        if (size == Size.SMALL) {
            for (int i = 0; i < 3; i++) {
                quickLinks.add(new DateQuickLink("", "Download", MaterialDesignT.TOOLS, "xxx url...", ZonedDateTime.now().plusDays(i)));
            }
            Collections.shuffle(quickLinks);

        } else {
            Random random = new Random();
            int imageQuickLinkCount = random.nextInt(2) + 2;
            int dateQuickLinkCount = random.nextInt(2) + 3;
            int nullCount = 7 - imageQuickLinkCount - dateQuickLinkCount;
            List<String> imageUrlList = new ArrayList<>();
            //currently there are only 3 images for testing
            for (int i = 0; i < 3; i++) {
                imageUrlList.add("/com/dlsc/jfxcentral2/demo/components/images/" + (size == Size.LARGE ? "quick-link-lg" : "website-changes-view-md") + i + ".png");
            }
            Collections.shuffle(imageUrlList);
            imageUrlList.subList(0, imageQuickLinkCount).forEach(url -> {
                quickLinks.add(new ImageQuickLink(HelloQuickLinksContainer.class.getResource(url).toExternalForm()));
            });

            for (int i = 0; i < dateQuickLinkCount; i++) {
                quickLinks.add(new DateQuickLink("JDKMon", "Download", null, "xxx url...", ZonedDateTime.now().plusDays(i)));
            }
            for (int i = 0; i < nullCount; i++) {
                quickLinks.add(null);
            }
            Collections.shuffle(quickLinks);
        }
        return quickLinks;
    }

    @Override
    public String getSampleName() {
        return "WebsiteChangesView";
    }
}
