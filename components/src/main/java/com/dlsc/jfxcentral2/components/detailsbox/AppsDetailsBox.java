package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;

import java.util.List;

public class AppsDetailsBox extends DetailsBoxBase<RealWorldApp> {

    public AppsDetailsBox() {
        getStyleClass().add("apps-details-box");
        setTitle("APPS");
        setIkon(IkonUtil.getModelIkon(RealWorldApp.class));
        setMaxItemsPerPage(5);
        setHomepageUrlProvider(RealWorldApp::getUrl);
    }

    @Override
    protected List<Node> createActionButtons(RealWorldApp model) {
        return List.of(createDetailsButton(model), createHomepageButton(model));
    }
}
