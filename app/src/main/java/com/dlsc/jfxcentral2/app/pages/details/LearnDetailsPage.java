package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.LearnDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.LearnOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class LearnDetailsPage extends DetailsPageBase<Learn> {

    public LearnDetailsPage(ObjectProperty<Size> size, Class<? extends Learn> clazz, String itemId) {
        super(size, clazz, itemId);
    }

    @Override
    public Node content() {
        Learn learn = getItem();

        // header
        LearnDetailHeader learnDetailHeader = new LearnDetailHeader(learn);
        learnDetailHeader.sizeProperty().bind(sizeProperty());

        // overview box
        LearnOverviewBox learnOverviewBox = new LearnOverviewBox(learn);
        learnOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getCenterNodes().add(learnOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(learnDetailHeader, detailsContentPane);
    }


}
