package com.dlsc.jfxcentral2.mobile.skin;

import com.dlsc.jfxcentral2.events.MobileResponseEvent;
import com.dlsc.jfxcentral2.events.RepositoryUpdatedEvent;
import com.dlsc.jfxcentral2.mobile.components.BottomMenuBar;
import com.dlsc.jfxcentral2.mobile.utils.PreferredFocusedNodeProvider;
import com.dlsc.jfxcentral2.mobile.pages.MainPage;
import com.dlsc.jfxcentral2.utils.EventBusUtil;
import com.dlsc.jfxcentral2.utils.Subscribe;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;

public class MainPageSkin extends SkinBase<MainPage> {

    private final BorderPane borderPane = new BorderPane();
    private final BottomMenuBar bottomMenuBar = new BottomMenuBar();

    public MainPageSkin(MainPage control) {
        super(control);
        EventBusUtil.register(this);

        bottomMenuBar.managedProperty().bind(bottomMenuBar.visibleProperty());
        bottomMenuBar.setVisible(false);

        borderPane.setBottom(bottomMenuBar);
        getChildren().add(borderPane);
    }

    @Subscribe
    public void onMobileResponseEvent(MobileResponseEvent responseView) {
        System.out.println("responseView = " + responseView);
        Node view = responseView.view();
        borderPane.setCenter(view);

        // focus the preferred node
        if (view instanceof PreferredFocusedNodeProvider preferredFocusedNodeProvider) {
            Node preferredFocusedNode = preferredFocusedNodeProvider.getPreferredFocusedNode();
            if (preferredFocusedNode != null) {
                preferredFocusedNode.requestFocus();
            }
        }
    }

    @Subscribe
    public void onHideMenuBarEvent(RepositoryUpdatedEvent event) {
        bottomMenuBar.setVisible(event.isUpdated());
    }

}
