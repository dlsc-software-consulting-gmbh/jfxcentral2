package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral2.events.MobileLinkEvent;
import com.dlsc.jfxcentral2.events.MobileResponseEvent;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The MobileRouter class is responsible for managing navigation in the mobile application.
 * It uses an event-driven approach to navigate between different views based on URLs.
 */
public class MobileRouter {

    private static MobileRouter instance;
    private final List<MobileRoute> mobileRoutes = new ArrayList<>();
    private final Navigator<String> navigator = new Navigator<>();

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
        navigateTo(linkEvent.link());
    }

    public void navigateTo(String link) {
        long start = System.currentTimeMillis();
        System.out.println(">>> Navigating to: " + link);

        if (!Objects.equals(navigator.getCurrent(), link) && !StringUtils.equalsIgnoreCase(link, PagePath.REFRESH)) {
            // Record the navigation in the history manager
            navigator.visit(link);
        }
        updateView(link, start);
    }

    private void updateView(String link, long start) {
        currentPath.set(link);
        for (MobileRoute mobileRoute : mobileRoutes) {
            if (mobileRoute.matches(link)) {
                MobileResponse response = mobileRoute.createResponse(link);

                String redirectPath = response.getRedirectPath();
                if (redirectPath != null) {
                    navigateTo(redirectPath);
                    loadTime.set(System.currentTimeMillis() - start);
                    return;
                }

                Node view = response.getView();
                if (view != null) {
                    response.setFinalUrl(link);
                    EventBusUtil.post(new MobileResponseEvent(response));
                    loadTime.set(System.currentTimeMillis() - start);
                    return;
                }
            }
        }
        EventBusUtil.post(new MobileResponseEvent(MobileResponse.view(link, new Label("404 - Not Found"))));
        loadTime.set(System.currentTimeMillis() - start);
    }

    public void goToBack() {
        if (navigator.isCanGoBack()) {
            String previousLink = navigator.goBack();
            EventBusUtil.post(new MobileLinkEvent(previousLink));
        }
    }

    public void goToForward() {
        if (navigator.isCanGoForward()) {
            String nextLink = navigator.goForward();
            EventBusUtil.post(new MobileLinkEvent(nextLink));
        }
    }

    public final ReadOnlyBooleanProperty canGoBackProperty() {
        return navigator.canGoBackProperty();
    }

    public final ReadOnlyBooleanProperty canGoForwardProperty() {
        return navigator.canGoForwardProperty();
    }

    public void start() {
        navigateTo(PagePath.HOME);
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