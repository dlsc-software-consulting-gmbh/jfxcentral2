package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.components.IconPreviewPane;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class IkonliPackTileView extends TileView<IkonliPack> {

    public IkonliPackTileView(IkonliPack pack) {
        super(pack);

        getStyleClass().addAll("icon-tile-view");

        // button 1
        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        LinkUtil.setLink(getButton1(), "icons/" + pack.getId());

        // button 2
        setButton2Text("GitHub");
        setButton2Graphic(new FontIcon(IkonUtil.github));
        LinkUtil.setExternalLink(getButton2(), pack.getUrl());
    }

    @Override
    protected Node createFrontTop() {
        IconPreviewPane previewPane = new IconPreviewPane();
        previewPane.sizeProperty().bind(sizeProperty());
        previewPane.setModel(getData());
        return previewPane;
    }
}
