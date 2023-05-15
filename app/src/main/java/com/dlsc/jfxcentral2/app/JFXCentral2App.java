package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral2.app.pages.LegalPage;
import com.dlsc.jfxcentral2.app.pages.LinksOfTheWeekPage;
import com.dlsc.jfxcentral2.app.pages.OpenJFXPage;
import com.dlsc.jfxcentral2.app.pages.RefreshPage;
import com.dlsc.jfxcentral2.app.pages.StartPage;
import com.dlsc.jfxcentral2.app.pages.category.BooksCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.PeopleCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.ShowcasesCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.VideosCategoryPage;
import com.dlsc.jfxcentral2.app.pages.details.BookDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.PersonDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.ShowcaseDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.VideoDetailsPage;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import one.jpro.routing.Request;
import one.jpro.routing.Response;
import one.jpro.routing.Route;
import one.jpro.routing.RouteApp;
import one.jpro.routing.RouteUtils;
import one.jpro.routing.dev.DevFilter;
import simplefx.experimental.parts.FXFuture;

import java.util.function.Supplier;

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
                .and(createCategoryOrDetailRoute("/people", () -> new PeopleCategoryPage(size), id -> new PersonDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute("/books", () -> new BooksCategoryPage(size), id -> new BookDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute("/videos", () -> new VideosCategoryPage(size), id -> new VideoDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute("/real_world", () -> new ShowcasesCategoryPage(size), id -> new ShowcaseDetailsPage(size, id))) // legacy routing for real world apps / showcases
                .and(createCategoryOrDetailRoute("/showcases", () -> new ShowcasesCategoryPage(size), id -> new ShowcaseDetailsPage(size, id))) // new routing for showcases
                .and(RouteUtils.get("/legal", r -> new LegalPage(size, LegalPage.Section.TERMS)))
                .and(RouteUtils.get("/legal/terms", r -> new LegalPage(size, LegalPage.Section.TERMS)))
                .and(RouteUtils.get("/legal/cookies", r -> new LegalPage(size, LegalPage.Section.COOKIES)))
                .and(RouteUtils.get("/legal/privacy", r -> new LegalPage(size, LegalPage.Section.PRIVACY)))
                .and(RouteUtils.get("/links", r -> new LinksOfTheWeekPage(size)))
                .and(RouteUtils.get("/openjfx", r -> new OpenJFXPage(size)))
                .and(RouteUtils.get("/refresh", r -> new RefreshPage(size)))
                .filter(DevFilter.create());
    }

    private Route createCategoryOrDetailRoute(String path, Supplier<Response> masterResponse, Callback<String, Response> detailedResponse) {
        return r -> {
            if (r.path().startsWith(path)) {
                return FXFuture.apply(() -> createResponse(r, masterResponse, detailedResponse));
            }

            return null;
        };
    }

    private Response createResponse(Request request, Supplier<Response> categoryResponse, Callback<String, Response> detailedResponse) {
        int index = request.path().lastIndexOf("/");
        if (index > 0) {
            String id = request.path().substring(index + 1).trim();
            return detailedResponse.call(id);
        }

        return categoryResponse.get();
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
