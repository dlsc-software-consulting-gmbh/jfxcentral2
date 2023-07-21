package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class AppTileView extends TileView<RealWorldApp> {

    public AppTileView(RealWorldApp app) {
        super(app);

        getStyleClass().add("app-tile-view");

        imageProperty().bind(ImageManager.getInstance().realWorldAppBannerImageProperty(app));

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        setButton2Visible(false);
        LinkUtil.setLink(getButton1(), "/showcases/" + app.getId());
    }
}
