package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.TutorialOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.LinkedObjectsBox;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileTutorialDetailsPage extends MobileDetailsPageBase<Tutorial> {

    public MobileTutorialDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tutorial.class, itemId);
    }

    @Override
    public List<Node> content() {
        Tutorial tutorial = getItem();

        // header
        MobilePageHeader header = new MobilePageHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(tutorial.getName());

        // overview
        TutorialOverviewBox tutorialOverviewBox = new TutorialOverviewBox(tutorial);
        tutorialOverviewBox.sizeProperty().bind(sizeProperty());
        tutorialOverviewBox.setIcon(null);
        tutorialOverviewBox.setTitle(null);

        // linked objects
        LinkedObjectsBox<Tutorial> linkedObjectsBox = new LinkedObjectsBox<>(tutorial);
        linkedObjectsBox.sizeProperty().bind(sizeProperty());

        VBox detailsPageContentWrapper = new VBox(tutorialOverviewBox, linkedObjectsBox);
        detailsPageContentWrapper.getStyleClass().add("details-page-content-wrapper");

        PrettyScrollPane detailsContentPane = new PrettyScrollPane(detailsPageContentWrapper);
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);

        return List.of(header, detailsContentPane);
    }
}
