package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral2.app.pages.StartPage;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import one.jpro.routing.Route;
import one.jpro.routing.RouteApp;
import one.jpro.routing.RouteUtils;

public class JFXCentral2App extends RouteApp {

    ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    @Override
    public Route createRoute() {

        getScene().getStylesheets().add(NodeUtil.class.getResource("/com/dlsc/jfxcentral2/theme.css").toExternalForm());

        return Route.empty()
                .and(RouteUtils.get("/", r -> new StartPage(size)));
    }
}
