package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral2.utils.PageUtil;

public class LearnOverviewBox extends OverviewBox<Learn> {

    public LearnOverviewBox(Learn learn) {
        super(learn);
        getStyleClass().add("learn-overview-box");

        setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + PageUtil.getLink(learn));

        if (learn instanceof LearnJavaFX temp) {
            setMarkdown(DataRepository2.getInstance().getLearnJavaFXReadMe(temp));
        } else if (learn instanceof LearnMobile temp) {
            setMarkdown(DataRepository2.getInstance().getLearnMobileReadMe(temp));
        } else {
            setMarkdown(DataRepository2.getInstance().getLearnRaspberryPiReadMe((LearnRaspberryPi) learn));
        }
    }
}
