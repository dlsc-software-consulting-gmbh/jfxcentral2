package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;

public class TipsAndTricksDetailsBox extends SimpleDetailsBox<Tip> {

    public TipsAndTricksDetailsBox() {
        getStyleClass().add("tips-details-box");
        setTitle("TIPS & TRICKS");
        setIkon(IkonUtil.tip);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getName());
        });
    }

    @Override
    protected CustomImageView createImageView(Tip model) {
        CustomImageView imageView = new CustomImageView();
        imageView.imageProperty().bind(Bindings.createObjectBinding(() -> new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/tips-tricks-thumbnail-01.png").toExternalForm())));
        return imageView;
    }

}
