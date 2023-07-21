package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;

import java.util.List;

public class TipsAndTricksDetailsBox extends DetailsBoxBase<Tip> {

    public TipsAndTricksDetailsBox() {
        getStyleClass().add("tips-details-box");
        setTitle("TIPS & TRICKS");
        setIkon(IkonUtil.getModelIkon(Tip.class));
    }

    @Override
    protected List<Node> createActionButtons(Tip tip) {
        return List.of(createDetailsButton(tip));
    }
}
