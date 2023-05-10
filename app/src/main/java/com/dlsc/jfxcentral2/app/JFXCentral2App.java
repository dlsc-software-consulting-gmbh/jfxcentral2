package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral2.app.pages.OpenJFXPage;
import com.dlsc.jfxcentral2.app.pages.RefreshPage;
import com.dlsc.jfxcentral2.app.pages.StartPage;
import com.dlsc.jfxcentral2.app.pages.details.BookDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.PersonDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.RealWorldAppDetailsPage;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import one.jpro.routing.Request;
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

        Scene scene = getScene();

        scene.setFill(Color.web("070B32"));
        scene.widthProperty().addListener((it -> updateSize(scene)));
        scene.getStylesheets().add(NodeUtil.class.getResource("/com/dlsc/jfxcentral2/theme.css").toExternalForm());

        updateSize(scene);

        return Route.empty()
                .and(RouteUtils.get("/", r -> new StartPage(size)))
                .and(RouteUtils.get("/person", r -> new PersonDetailsPage(size, id(r))))
                .and(RouteUtils.get("/book", r -> new BookDetailsPage(size, id(r))))
                .and(RouteUtils.get("/real_world/*", r -> new RealWorldAppDetailsPage(size, id(r))))
                .and(RouteUtils.get("/openjfx", r -> new OpenJFXPage(size)))
                .and(RouteUtils.get("/refresh", r -> new RefreshPage(size)))
                .filter(DevFilter.create());
    }

    private String id(Request r) {
        String path = r.path();
        System.out.println("path: " + path);
        int index = path.lastIndexOf("/");
        if (index > -1) {
            return path.substring(index + 1);
        }

        System.out.println("no id found in path");
        return "";
    }

    private void updateSize(Scene scene) {
        double sceneWidth = scene.getWidth();
        if (sceneWidth < 768) {
            size.set(Size.SMALL);
        } else if (sceneWidth < 1160) {
            size.set(Size.MEDIUM);
        } else {
            size.set(Size.LARGE);
        }
    }
}
