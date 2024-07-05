package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.ShowcaseOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.List;

public class MobileShowcaseMobileDetailsPage extends MobileDetailsPageBase<RealWorldApp> {

    public MobileShowcaseMobileDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, RealWorldApp.class, itemId);
    }

    @Override
    public List<Node> content() {
        RealWorldApp app = getItem();

        // header
        MobileCategoryHeader header = new MobileCategoryHeader(){
            @Override
            protected String goBackLink() {
                return PagePath.SHOWCASES;
            }
        };
        header.previewImageProperty().bind(ImageManager.getInstance().realWorldAppBannerImageProperty(app));
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(app.getName());

        // overview box
        ShowcaseOverviewBox appOverviewBox = new ShowcaseOverviewBox(app);
        appOverviewBox.sizeProperty().bind(sizeProperty());
        PrettyScrollPane scrollPane = new PrettyScrollPane(new StackPane(appOverviewBox));
        scrollPane.getStyleClass().add("mobile");

        return List.of(header, scrollPane);
    }
}