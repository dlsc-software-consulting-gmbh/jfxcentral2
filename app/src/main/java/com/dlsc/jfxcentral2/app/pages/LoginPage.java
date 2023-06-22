package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.LoginView;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LoginPage extends PageBase {

    public LoginPage(ObjectProperty<Size> size) {
        super(size, Mode.DARK);
    }

    @Override
    public String title() {
        return "JFXCentral - Login";
    }

    @Override
    public String description() {
        return "Social login for JFXCentral";
    }

    @Override
    public Node content() {

        // login view
        LoginView loginView = new LoginView();
        loginView.sizeProperty().bind(sizeProperty());
        VBox.setVgrow(loginView, Priority.ALWAYS);

        return wrapContent(loginView);
    }
}
