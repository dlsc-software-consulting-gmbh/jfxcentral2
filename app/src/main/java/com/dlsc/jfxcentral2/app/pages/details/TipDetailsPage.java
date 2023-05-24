package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.TipDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.TipOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class TipDetailsPage extends DetailsPageBase<Tip> {

    public TipDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tip.class, itemId);
    }

    @Override
    public Node content() {

        // header
        TipDetailHeader header = new TipDetailHeader(getItem());
        header.sizeProperty().bind(sizeProperty());
        
        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(new TipOverviewBox(getItem()));
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }
}
