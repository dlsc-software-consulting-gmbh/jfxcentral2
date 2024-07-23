package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.LearnOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileLearnDetailsPage extends MobileDetailsPageBase<Learn> {

    public MobileLearnDetailsPage(ObjectProperty<Size> size, Class<? extends Learn> clazz, String itemId) {
        super(size, clazz, itemId);
    }

    @Override
    public List<Node> content() {
        Learn learn = getItem();

        // header
        MobileCategoryHeader header = new MobileCategoryHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(learn.getName());

        // overview box
        LearnOverviewBox learnOverviewBox = new LearnOverviewBox(learn);
        learnOverviewBox.sizeProperty().bind(sizeProperty());
        learnOverviewBox.setIcon(null);
        learnOverviewBox.setTitle(null);

        PrettyScrollPane detailsContentPane = new PrettyScrollPane(new StackPane(learnOverviewBox));
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);

        return List.of(header, detailsContentPane);
    }

}
