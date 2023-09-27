package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.ErrorView;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import one.jpro.platform.routing.Request;

public class ErrorPage extends PageBase {

    private final Request request;

    public ErrorPage(ObjectProperty<Size> size, Request request) {
        super(size, Mode.LIGHT);

        this.request = request;
    }

    @Override
    public String title() {
        return "JFXCentral - Error";
    }

    @Override
    public String description() {
        return "Error page.";
    }

    @Override
    public Node content() {
        // error view
        ErrorView errorView = new ErrorView();
        errorView.setMessage("Page not found: " + request.path());
        errorView.sizeProperty().bind(sizeProperty());

        // strip view for pull requests and features
        StripView stripView = new StripView(errorView);

        return wrapContent(stripView);
    }
}
