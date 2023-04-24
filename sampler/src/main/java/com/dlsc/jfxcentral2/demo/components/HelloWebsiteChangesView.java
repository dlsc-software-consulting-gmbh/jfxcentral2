package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.WebsiteChangesView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.DateQuickLink;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HelloWebsiteChangesView extends JFXCentralSampleBase {

    private PaneBase controlPane;
    private WebsiteChangesView view;

    @Override
    protected Region createControl() {
        view = new WebsiteChangesView();
        view.setTitle("Website\nChanges");
        view.setDescription("Libraries, news, books, people, etc... that have recently been added or updated");
        view.setQuickLinks(generateQuickLinks(Size.LARGE));
        controlPane = new PaneBase() {
            @Override
            protected void layoutBySize() {
                Size size = getSize();
                 view = new WebsiteChangesView();
                view.setDescription("Libraries, news, books, people, etc... that have recently been added or updated");
                if (size == Size.LARGE) {
                    view.setTitle("Website\nChanges");
                    view.setQuickLinks(generateQuickLinks(Size.LARGE));
                } else if (size == Size.MEDIUM) {
                    view.setTitle("Website\nChanges");
                    view.setQuickLinks(generateQuickLinks(Size.MEDIUM));
                } else {
                    view.setTitle("Website Changes");
                    view.setQuickLinks(generateQuickLinks(Size.SMALL));
                }
                getChildren().setAll(view);
            }
        };
        controlPane.getChildren().setAll(view);
        return controlPane;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        controlPane.sizeProperty().bind(sizeComboBox.sizeProperty());

        Button button = new Button("Refresh");
        button.setOnAction(event -> {
            view.setQuickLinks(FXCollections.observableList(generateQuickLinks(sizeComboBox.getSize())));
        });

        return new VBox(20, sizeComboBox, button);
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
                quickLinks.add(new ImageQuickLink(HelloQuickLinkContainer.class.getResource(url).toExternalForm()));
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
