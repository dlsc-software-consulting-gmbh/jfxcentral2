package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.TipDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.TipOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class MobileTipDetailsPage extends MobileDetailsPageBase<Tip> {

    public MobileTipDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tip.class, itemId);
    }

    @Override
    public List<Node> content() {
        Tip tip = getItem();

        // header
        TipDetailHeader header = new TipDetailHeader(tip);
        header.sizeProperty().bind(sizeProperty());

        // overview
        TipOverviewBox tipOverviewBox = new TipOverviewBox(tip);
        tipOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(tipOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return List.of(header, detailsContentPane);
    }
}
