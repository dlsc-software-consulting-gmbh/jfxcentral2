package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Tip;

public class TipOverviewBox extends OverviewBox<Tip> {

    public TipOverviewBox(Tip tip) {
        super(tip);
        getStyleClass().add("tip-overview-box");
        setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "tips/" + getModel().getId());
        setMarkdown(DataRepository2.getInstance().getTipReadMe(tip));
    }
}
