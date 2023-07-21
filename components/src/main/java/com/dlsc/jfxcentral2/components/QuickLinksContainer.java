package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.QuickLink;

import java.util.List;

public class QuickLinksContainer extends LinksContainerBase {

    public QuickLinksContainer() {
        getStyleClass().add("quick-links-container");
        updateUI();
    }

    @Override
    protected void layoutBySize() {
        if (!isLgToMdOrMdToLg()) {
            updateUI();
        }
    }

    private void updateUI() {
        gridPane.getChildren().clear();
        List<QuickLink> links = getQuickLinks();
        if (links == null || links.isEmpty()) {
            return;
        }
        int col = getSize().isSmall() ? 2 : 3;
        for (int i = 0; i < links.size(); i++) {
            QuickLink quickLink = links.get(i);
            QuickLinkView quickLinkView = new QuickLinkView(quickLink);
            quickLinkView.sizeProperty().bind(sizeProperty());
            gridPane.add(quickLinkView, i % col, i / col);
        }
    }

}
