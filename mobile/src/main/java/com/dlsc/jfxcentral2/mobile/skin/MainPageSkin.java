package com.dlsc.jfxcentral2.mobile.skin;

import com.dlsc.jfxcentral2.events.MobileResponseEvent;
import com.dlsc.jfxcentral2.mobile.componenets.BottomMenuBar;
import com.dlsc.jfxcentral2.mobile.pages.MainPage;
import com.dlsc.jfxcentral2.utils.EventBusUtil;
import com.dlsc.jfxcentral2.utils.Subscribe;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;

public class MainPageSkin extends SkinBase<MainPage> {

    private final BorderPane borderPane;

    public MainPageSkin(MainPage control) {
        super(control);
        EventBusUtil.register(this);

        borderPane = new BorderPane();
        // default view
        borderPane.setBottom(new BottomMenuBar());

        getChildren().add(borderPane);
    }

    @Subscribe
    public void onMobileResponseEvent(MobileResponseEvent responseView) {
        borderPane.setCenter(responseView.view());
    }
}
