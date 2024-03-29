package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral2.components.LearnPaginationBox;
import javafx.scene.Node;

public class LearnOverviewBox extends OverviewBox<Learn> {

    public LearnOverviewBox(Learn learn) {
        super(learn);
        getStyleClass().add("learn-overview-box");

        if (learn instanceof LearnJavaFX temp) {
            setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "learn/javafx/" + learn.getId());
            setMarkdown(DataRepository2.getInstance().getLearnJavaFXReadMe(temp));
        } else if (learn instanceof LearnMobile temp) {
            setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "learn/mobile/" + learn.getId());
            setMarkdown(DataRepository2.getInstance().getLearnMobileReadMe(temp));
        } else {
            setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "learn/raspberrypi/" + learn.getId());
            setMarkdown(DataRepository2.getInstance().getLearnRaspberryPiReadMe((LearnRaspberryPi) learn));
        }
    }

    @Override
    protected Node createTopNode() {
        return new LearnPaginationBox(getModel(), LearnPaginationBox.Position.TOP, sizeProperty());
    }

    @Override
    protected Node createBottomNode() {
        return new LearnPaginationBox(getModel(), LearnPaginationBox.Position.BOTTOM, sizeProperty());
    }

}
