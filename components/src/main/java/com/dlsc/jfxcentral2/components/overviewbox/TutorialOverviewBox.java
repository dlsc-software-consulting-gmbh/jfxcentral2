package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Tutorial;
import javafx.beans.binding.Bindings;

public class TutorialOverviewBox extends SimpleOverviewBox<Tutorial> {

    public TutorialOverviewBox() {
        super();
    }

    public TutorialOverviewBox(Tutorial data) {
        super(data);
        getStyleClass().add("tutorial-overview-box");
        baseURLProperty().bind(Bindings.createStringBinding(() -> DataRepository.getInstance().getRepositoryDirectoryURL() + "tutorials/" + getData().getId(), dataProperty()));
        markdownProperty().bind(DataRepository.getInstance().tutorialTextProperty(data));
    }
}
