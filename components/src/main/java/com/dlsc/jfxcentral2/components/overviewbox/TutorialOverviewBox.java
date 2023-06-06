package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tutorial;

public class TutorialOverviewBox extends SimpleOverviewBox<Tutorial> {

    public TutorialOverviewBox(Tutorial tutorial) {
        super(tutorial);
        getStyleClass().add("tutorial-overview-box");
        setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "tutorials/" + getModel().getId());
        setMarkdown(DataRepository2.getInstance().getTutorialReadMe(tutorial));
        imageProperty().bind(ImageManager.getInstance().tutorialImageLargeProperty(tutorial));
    }
}
