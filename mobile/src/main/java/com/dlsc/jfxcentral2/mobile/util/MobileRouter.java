package com.dlsc.jfxcentral2.mobile.util;

import com.dlsc.jfxcentral2.mobile.events.MobileLinkEvent;
import com.dlsc.jfxcentral2.mobile.events.MobileResponseEvent;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MobileRouter {

    private static MobileRouter instance;
    private final List<Route> routes = new ArrayList<>();

    private MobileRouter() {
        EventBusUtil.register(this);
    }

    public static MobileRouter getInstance() {
        if (instance == null) {
            instance = new MobileRouter();
        }
        return instance;
    }

    public MobileRouter add(String path, Supplier<Node> viewSupplier) {
        routes.add(new Route(path, viewSupplier));
        return this;
    }

    public MobileRouter add(String path, Supplier<Node> categoryViewSupplier, Callback<String, Node> detailViewCallback) {
        routes.add(new Route(path, categoryViewSupplier, detailViewCallback));
        return this;
    }

    @Subscribe
    public void handleMobileLink(MobileLinkEvent linkEvent) {
        String link = linkEvent.link();
        System.out.println(">>> Navigating to: " + link);

        currentPath.set(linkEvent.link());
        for (Route route : routes) {
            if (route.matches(link)) {
                Node view = route.createView(link);
                if (view != null) {
                    EventBusUtil.post(new MobileResponseEvent(link, view));
                    return;
                }
            }
        }
        EventBusUtil.post(new MobileResponseEvent(link, new Label("Error: 404" + link)));
    }

    private final ReadOnlyStringWrapper currentPath = new ReadOnlyStringWrapper(this, "currentPath", "/");

    public ReadOnlyStringProperty currentPathProperty() {
        return currentPath.getReadOnlyProperty();
    }

    public String getCurrentPath() {
        return currentPath.get();
    }

    private static class Route {
        private final String path;
        private final Supplier<Node> viewSupplier;
        private final Callback<String, Node> detailViewCallback;

        Route(String path, Supplier<Node> viewSupplier) {
            this.path = path;
            this.viewSupplier = viewSupplier;
            this.detailViewCallback = null;
        }

        Route(String path, Supplier<Node> categoryViewSupplier, Callback<String, Node> detailViewCallback) {
            this.path = path;
            this.viewSupplier = categoryViewSupplier;
            this.detailViewCallback = detailViewCallback;
        }

        boolean matches(String url) {
            if (detailViewCallback == null) {
                return url.equals(path);
            } else {
                return url.startsWith(path);
            }
        }

        Node createView(String url) {
            if (detailViewCallback == null) {
                return viewSupplier.get();
            } else {
                int index = url.lastIndexOf('/');
                if (index > path.length()) {
                    String id = url.substring(index + 1).trim();
                    return detailViewCallback.call(id);
                } else {
                    return viewSupplier.get();
                }
            }
        }
    }
}
