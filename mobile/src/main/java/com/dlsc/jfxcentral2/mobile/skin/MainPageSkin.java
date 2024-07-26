package com.dlsc.jfxcentral2.mobile.skin;

import com.dlsc.jfxcentral2.components.MobilePageBase;
import com.dlsc.jfxcentral2.events.MobileResponseEvent;
import com.dlsc.jfxcentral2.events.RepositoryUpdatedEvent;
import com.dlsc.jfxcentral2.mobile.components.BottomMenuBar;
import com.dlsc.jfxcentral2.mobile.pages.MainPage;
import com.dlsc.jfxcentral2.mobile.utils.PreferredFocusedNodeProvider;
import com.dlsc.jfxcentral2.utils.EventBusUtil;
import com.dlsc.jfxcentral2.utils.Subscribe;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;

import java.util.Optional;
import java.util.function.Function;

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
    public void onMobileResponseEvent(MobileResponseEvent event) {
        // Old view will disappear
        Node oldView = borderPane.getCenter();
        invokeLifecycleMethod(oldView, MobilePageBase::getViewWillDisappear);

        // New view will appear
        Node newView = event.mobileResponse().getView();
        invokeLifecycleMethod(newView, MobilePageBase::getViewWillAppear);

        borderPane.setCenter(newView);

        // Old view did disappear
        invokeLifecycleMethod(oldView, MobilePageBase::getViewDidDisappear);

        // New view did appear
        invokeLifecycleMethod(newView, MobilePageBase::getViewDidAppear);

        // focus the preferred node
        if (newView instanceof PreferredFocusedNodeProvider preferredFocusedNodeProvider) {
            Node preferredFocusedNode = preferredFocusedNodeProvider.getPreferredFocusedNode();
            if (preferredFocusedNode != null) {
                preferredFocusedNode.requestFocus();
            }
        }
    }
    
    private void invokeLifecycleMethod(Node view, Function<MobilePageBase, Runnable> eventFunction) {
        if (view instanceof MobilePageBase mobilePage) {
            Optional.ofNullable(eventFunction.apply(mobilePage)).ifPresent(Runnable::run);
        }
    }

    @Subscribe
    public void onHideMenuBarEvent(RepositoryUpdatedEvent event) {
        bottomMenuBar.setVisible(event.isUpdated());
    }

}
