package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.QuickLink;

import java.util.List;

public class QuickLinksContainer extends LinksContainerBase {

    public QuickLinksContainer() {
        getStyleClass().add("quick-links-container");
        layoutBySize();
        quickLinksProperty().addListener((ob, ov, nv) -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        gridPane.getChildren().clear();
        List<QuickLink> links = getQuickLinks();
        if (links == null || links.isEmpty()) {
            return;
        }
        int col = getSize().isSmall() ? 2 : 3;
        for (int i = 0; i < links.size(); i++) {
            QuickLink quickLink = links.get(i);
            QuickLinkView quickLinkView = new QuickLinkView(quickLink);
            gridPane.add(quickLinkView, i % col, i / col);
        }
    }

}
