package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral2.events.MobileLinkEvent;
import com.dlsc.jfxcentral2.events.MobileResponseEvent;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * The MobileRouter class is responsible for managing navigation in the mobile application.
 * It uses an event-driven approach to navigate between different views based on URLs.
 */
public class MobileRouter {

    private static MobileRouter instance;
    private final List<MobileRoute> mobileRoutes = new ArrayList<>();

    private MobileRouter() {
        EventBusUtil.register(this);
    }

    public static MobileRouter getInstance() {
        if (instance == null) {
            instance = new MobileRouter();
        }
        return instance;
    }

    public MobileRouter and(MobileRoute mobileRoute) {
        mobileRoutes.add(mobileRoute);
        return this;
    }

    @Subscribe
    public void handleMobileLink(MobileLinkEvent linkEvent) {
        long start = System.currentTimeMillis();
        String link = linkEvent.link();
        System.out.println(">>> Navigating to: " + link);

        currentPath.set(linkEvent.link());
        for (MobileRoute mobileRoute : mobileRoutes) {
            if (mobileRoute.matches(link)) {
                if (mobileRoute.getRedirectPath() != null) {
                    handleMobileLink(new MobileLinkEvent(mobileRoute.getRedirectPath()));
                    loadTime.set(System.currentTimeMillis() - start);
                    return;
                }
                Node view = mobileRoute.createView(link);
                if (view != null) {
                    EventBusUtil.post(new MobileResponseEvent(link, view));
                    loadTime.set(System.currentTimeMillis() - start);
                    return;
                }
            }
        }
        EventBusUtil.post(new MobileResponseEvent(link, new Label("Error: 404 " + link)));
        loadTime.set(System.currentTimeMillis() - start);
    }

    public void start() {
        handleMobileLink(new MobileLinkEvent(PagePath.HOME));
    }

    // current path

    private final ReadOnlyStringWrapper currentPath = new ReadOnlyStringWrapper(this, "currentPath");

    public ReadOnlyStringProperty currentPathProperty() {
        return currentPath.getReadOnlyProperty();
    }

    public String getCurrentPath() {
        return currentPath.get();
    }

    // loadTime

    private final ReadOnlyLongWrapper loadTime = new ReadOnlyLongWrapper(this, "loadTime", 0);

    public ReadOnlyLongProperty loadTimeProperty() {
        return loadTime.getReadOnlyProperty();
    }

    public long getLoadTime() {
        return loadTime.get();
    }

}