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

        // header
        AppDetailHeader showcaseDetailHeader = new AppDetailHeader(getItem());
        showcaseDetailHeader.sizeProperty().bind(sizeProperty());
        showcaseDetailHeader.set
        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(new AppOverviewBox(getItem()));
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(showcaseDetailHeader, detailsContentPane);
    }
}
