package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.components.overviewbox.VideoOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class VideoDetailsPage extends DetailsPageBase<Video> {

    public VideoDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Video.class, itemId);
    }

    @Override
    public Node content() {
        // header
        CategoryHeader<Video> header = new CategoryHeader<>();
        header.setTitle(getItem().getName());
        header.setIkon(IkonUtil.getModelIkon(getItem()));
        header.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(new VideoOverviewBox(getItem()));
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }

}
