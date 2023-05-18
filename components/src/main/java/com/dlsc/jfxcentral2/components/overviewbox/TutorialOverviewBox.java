package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.model.Tutorial;

public class TutorialOverviewBox extends SimpleOverviewBox<Tutorial> {

    public TutorialOverviewBox() {
        super();
    }

    public TutorialOverviewBox(Tutorial data) {
        super(data);
        getStyleClass().add("tutorial-overview-box");
    }

}
