package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;

public class RealWorldAppDetailsPage extends DetailsPageBase<RealWorldApp> {

    public RealWorldAppDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, RealWorldApp.class, itemId);
    }
}
