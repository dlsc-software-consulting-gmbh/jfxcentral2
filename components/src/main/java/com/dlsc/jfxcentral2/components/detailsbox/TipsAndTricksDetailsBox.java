package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.TipsAndTricksDetailsObject;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;

public class TipsAndTricksDetailsBox extends SimpleDetailsBox<TipsAndTricksDetailsObject> {

    public TipsAndTricksDetailsBox() {
        getStyleClass().add("tips-details-box");
        setTitle("TIPS & TRICKS");
        setIkon(MaterialDesignL.LIGHTBULB_ON_OUTLINE);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });
    }

}
