package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.TutorialDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.TutorialOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class TutorialDetailsPage extends DetailsPageBase<Tutorial> {

    public TutorialDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tutorial.class, itemId);
    }

    @Override
    public Node content() {
        // header
        TutorialDetailHeader header = new TutorialDetailHeader(getItem());
        header.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(new TutorialOverviewBox(getItem()));
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }
}
