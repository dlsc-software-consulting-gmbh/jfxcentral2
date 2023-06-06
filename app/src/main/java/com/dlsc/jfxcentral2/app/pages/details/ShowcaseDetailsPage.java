package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.AppDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.AppOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class ShowcaseDetailsPage extends DetailsPageBase<RealWorldApp> {

    public ShowcaseDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, RealWorldApp.class, itemId);
    }

    @Override
    public Node content() {
        RealWorldApp app = getItem();

        // header
        AppDetailHeader showcaseDetailHeader = new AppDetailHeader(app);
        showcaseDetailHeader.sizeProperty().bind(sizeProperty());

        // overview box
        AppOverviewBox appOverviewBox = new AppOverviewBox(app);
        appOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(appOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(showcaseDetailHeader, detailsContentPane);
    }
}
