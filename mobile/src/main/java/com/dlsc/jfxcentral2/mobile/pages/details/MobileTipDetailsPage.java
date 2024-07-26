package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.TipOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.LinkedObjectsBox;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileTipDetailsPage extends MobileDetailsPageBase<Tip> {

    public MobileTipDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tip.class, itemId);
    }

    @Override
    public List<Node> content() {
        Tip tip = getItem();

        // header
        MobilePageHeader header = new MobilePageHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(tip.getName());

        // overview
        TipOverviewBox tipOverviewBox = new TipOverviewBox(tip);
        tipOverviewBox.sizeProperty().bind(sizeProperty());
        tipOverviewBox.setIcon(null);
        tipOverviewBox.setTitle(null);

        // linked objects
        LinkedObjectsBox<Tip> linkedObjectsBox = new LinkedObjectsBox<>(tip);
        linkedObjectsBox.sizeProperty().bind(sizeProperty());

        VBox detailsPageContentWrapper = new VBox(tipOverviewBox, linkedObjectsBox);
        detailsPageContentWrapper.getStyleClass().add("details-page-content-wrapper");

        PrettyScrollPane detailsContentPane = new PrettyScrollPane(detailsPageContentWrapper);
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);


        return List.of(header, detailsContentPane);
    }
}
