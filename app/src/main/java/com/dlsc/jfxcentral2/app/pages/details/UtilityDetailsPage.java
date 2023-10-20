package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.UtilityDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.UtilityOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class UtilityDetailsPage extends DetailsPageBase<Utility> {

    public UtilityDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Utility.class, itemId);
    }

    @Override
    public Node content() {
        Utility utility = getItem();

        // header
        UtilityDetailHeader header = new UtilityDetailHeader(utility);
        header.sizeProperty().bind(sizeProperty());

        // overview
        UtilityOverviewBox overviewBox = new UtilityOverviewBox(utility);
        overviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(overviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }


}
