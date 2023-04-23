package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral2.app.pages.StartPage;
import com.dlsc.jfxcentral2.components.Size;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import one.jpro.routing.Route;
import one.jpro.routing.RouteApp;
import one.jpro.routing.RouteUtils;

public class JFXCentral2App extends RouteApp {

    ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.MEDIUM);

    @Override
    public Route createRoute() {

        getRouteNode().getStylesheets().add(com.dlsc.jfxcentral2.JFXCentral2App.class.getResource("theme.css").toExternalForm());

        return Route.empty()
                .and(RouteUtils.get("/", r -> new StartPage(size)));

    }
}
