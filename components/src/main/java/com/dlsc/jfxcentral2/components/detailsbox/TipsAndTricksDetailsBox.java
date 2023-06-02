package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;

public class TipsAndTricksDetailsBox extends SimpleDetailsBox<Tip> {

    public TipsAndTricksDetailsBox() {
        getStyleClass().add("tips-details-box");
        setTitle("TIPS & TRICKS");
        setIkon(IkonUtil.tip);
    }

    @Override
    protected CustomImageView createImageView(Tip tip) {
        CustomImageView imageView = new CustomImageView();
        imageView.imageProperty().bind(ImageManager.getInstance().tipBannerImageProperty(tip));
        return imageView;
    }

}
