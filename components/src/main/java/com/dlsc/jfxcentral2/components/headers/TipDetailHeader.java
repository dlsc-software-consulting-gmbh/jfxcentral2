package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.RealWorldApp;

public class TipDetailHeader extends SimpleDetailHeader<RealWorldApp>  {

    public TipDetailHeader(RealWorldApp app) {
        this();
        setModel(app);
    }

    public TipDetailHeader() {
        getStyleClass().add("tip-detail-header");
        setOnShare(() -> System.out.println(getClass().getSimpleName() + " share"));
    }
}
