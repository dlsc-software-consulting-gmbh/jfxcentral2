package com.dlsc.jfxcentral2.mobile.skin;

import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.events.MobileResponseEvent;
import com.dlsc.jfxcentral2.mobile.componenets.BottomMenuBar;
import com.dlsc.jfxcentral2.mobile.pages.MainPage;
import com.dlsc.jfxcentral2.mobile.pages.MobileHomePage;
import com.dlsc.jfxcentral2.utils.EventBusUtil;
import com.dlsc.jfxcentral2.utils.Subscribe;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;

public class MainPageSkin extends SkinBase<MainPage> {

    private final PrettyScrollPane prettyScrollPane = new PrettyScrollPane();

    public MainPageSkin(MainPage control) {
        super(control);
        EventBusUtil.register(this);

        BorderPane  borderPane = new BorderPane();
        // default view
        prettyScrollPane.setContent(new MobileHomePage());
        prettyScrollPane.setShowScrollToTopButton(false);
        borderPane.setCenter(prettyScrollPane);
        borderPane.setBottom(new BottomMenuBar());

        getChildren().add(borderPane);
    }

    @Subscribe
    public void onMobileResponseEvent(MobileResponseEvent responseView) {
        prettyScrollPane.setContent(responseView.view());

        // scroll to top, easy to read from top to bottom
        // if (PagePath.LINKS.equals(responseView.url())) {
            prettyScrollPane.setVvalue(0.0);
        // }
    }
}
