package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;

public class VideoDetailsPage extends DetailsPageBase<Video> {

    public VideoDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Video.class, itemId);
    }
}
