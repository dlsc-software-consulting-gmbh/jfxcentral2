package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.LearnPaginationBox;
import com.dlsc.jfxcentral2.components.headers.LearnDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.LearnOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class MobileLearnDetailsPage extends MobileDetailsPageBase<Learn> {

    public MobileLearnDetailsPage(ObjectProperty<Size> size, Class<? extends Learn> clazz, String itemId) {
        super(size, clazz, itemId);
    }

    @Override
    public List<Node> content() {
        Learn learn = getItem();

        // header
        LearnDetailHeader learnDetailHeader = new LearnDetailHeader(learn);
        learnDetailHeader.sizeProperty().bind(sizeProperty());

        // overview box
        LearnOverviewBox learnOverviewBox = new LearnOverviewBox(learn);
        learnOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.getStyleClass().add("learn-details-content-pane");
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getCenterNodes().add(learnOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());
        LearnPaginationBox paginationBox = new LearnPaginationBox(learn, LearnPaginationBox.Position.LEFT, sizeProperty());
        detailsContentPane.setLeftTopExtraNode(paginationBox);

        return List.of(learnDetailHeader, detailsContentPane);
    }

}
