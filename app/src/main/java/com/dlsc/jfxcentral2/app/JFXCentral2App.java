package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral2.app.pages.OpenJFXPage;
import com.dlsc.jfxcentral2.app.pages.RefreshPage;
import com.dlsc.jfxcentral2.app.pages.StartPage;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import one.jpro.routing.Route;
import one.jpro.routing.RouteApp;
import one.jpro.routing.RouteUtils;
import one.jpro.routing.dev.DevFilter;

public class JFXCentral2App extends RouteApp {

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    static {
        DataRepository.getInstance().loadData();
    }

    @Override
    public Route createRoute() {

        getScene().setFill(Color.web("070B32"));
        getScene().widthProperty().addListener((observable, oldWidth, newWidth) -> {
            if (newWidth.intValue() < 780) {
                size.set(Size.SMALL);
            } else if (newWidth.intValue() < 1160) {
                size.set(Size.MEDIUM);
            } else {
                size.set(Size.LARGE);
            }
        });

        getScene().getStylesheets().add(NodeUtil.class.getResource("/com/dlsc/jfxcentral2/theme.css").toExternalForm());

        return Route.empty()
                .and(RouteUtils.get("/", r -> new StartPage(size)))
                .and(RouteUtils.get("/openjfx", r -> new OpenJFXPage(size)))
                .and(RouteUtils.get("/refresh", r -> new RefreshPage(size)))
                .filter(DevFilter.create());
    }
}
