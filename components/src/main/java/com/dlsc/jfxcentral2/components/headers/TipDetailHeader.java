package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Tip;

public class TipDetailHeader extends SimpleDetailHeader<Tip>  {

    public TipDetailHeader(Tip tip) {
        super(tip);
        getStyleClass().add("tip-detail-header");
    }
}
