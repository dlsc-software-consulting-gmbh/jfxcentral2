package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.WelcomeView;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class StartPage extends DefaultPage {

    @Override
    public String title() {
        return "title";
    }

    @Override
    public String description() {
        return "description";
    }

    public StartPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public Node content() {
        var content = new Label("Hello JPro!");

        // welcome
        WelcomeView welcomeView = new WelcomeView();
        welcomeView.sizeProperty().bind(sizeProperty());

        return wrapContent(welcomeView);
    }
}
