package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.TutorialDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.TutorialOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class MobileTutorialDetailsPage extends MobileDetailsPageBase<Tutorial> {

    public MobileTutorialDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tutorial.class, itemId);
    }

    @Override
    public List<Node> content() {
        Tutorial tutorial = getItem();

        // header
        TutorialDetailHeader header = new TutorialDetailHeader(tutorial);
        header.sizeProperty().bind(sizeProperty());

        // overview
        TutorialOverviewBox tutorialOverviewBox = new TutorialOverviewBox(tutorial);
        tutorialOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(tutorialOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return List.of(header, detailsContentPane);
    }
}
