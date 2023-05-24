package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Tip;

public class TipDetailHeader extends SimpleDetailHeader<Tip>  {

    public TipDetailHeader(Tip app) {
        this();
        setModel(app);
    }

    public TipDetailHeader() {
        getStyleClass().add("tip-detail-header");
    }
}
