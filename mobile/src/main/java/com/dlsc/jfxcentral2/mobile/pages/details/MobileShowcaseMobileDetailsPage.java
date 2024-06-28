package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.components.headers.ShowcaseDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.ShowcaseOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class MobileShowcaseMobileDetailsPage extends MobileDetailsPageBase<RealWorldApp> {

    public MobileShowcaseMobileDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, RealWorldApp.class, itemId);
    }

    @Override
    public List<Node> content() {
        RealWorldApp app = getItem();

        // header
        ShowcaseDetailHeader showcaseDetailHeader = new ShowcaseDetailHeader(app);
        showcaseDetailHeader.sizeProperty().bind(sizeProperty());

        // overview box
        ShowcaseOverviewBox appOverviewBox = new ShowcaseOverviewBox(app);
        appOverviewBox.sizeProperty().bind(sizeProperty());

        return List.of(showcaseDetailHeader, appOverviewBox);
    }
}
