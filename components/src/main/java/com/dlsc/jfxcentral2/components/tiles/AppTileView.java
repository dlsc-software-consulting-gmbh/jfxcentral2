package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PlatformLinkUtil;
import com.dlsc.jfxcentral2.utils.images.CentralImageManager;
import org.kordamp.ikonli.javafx.FontIcon;

public class AppTileView extends TileView<RealWorldApp> {

    public AppTileView(RealWorldApp app) {
        super(app);

        getStyleClass().add("app-tile-view");

        imageProperty().set(CentralImageManager.getRealWorldAppBannerImage2(app));

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        setButton2Visible(false);
        PlatformLinkUtil.setLink(getButton1(), "/showcases/" + app.getId());
    }
}
