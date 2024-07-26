package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.ShowcaseOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.LinkedObjectsBox;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileShowcaseMobileDetailsPage extends MobileDetailsPageBase<RealWorldApp> {

    public MobileShowcaseMobileDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, RealWorldApp.class, itemId);
    }

    @Override
    public List<Node> content() {
        RealWorldApp app = getItem();

        // header
        MobilePageHeader header = new MobilePageHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(app.getName());

        // overview box
        ShowcaseOverviewBox appOverviewBox = new ShowcaseOverviewBox(app);
        appOverviewBox.sizeProperty().bind(sizeProperty());
        appOverviewBox.setIcon(null);
        appOverviewBox.setTitle(null);

        // linked objects
        LinkedObjectsBox<RealWorldApp> linkedObjectsBox = new LinkedObjectsBox<>(app);
        linkedObjectsBox.sizeProperty().bind(sizeProperty());

        VBox detailsPageContentWrapper = new VBox(appOverviewBox, linkedObjectsBox);
        detailsPageContentWrapper.getStyleClass().add("details-page-content-wrapper");

        PrettyScrollPane scrollPane = new PrettyScrollPane(detailsPageContentWrapper);
        scrollPane.getStyleClass().add("mobile");

        return List.of(header, scrollPane);
    }
}
