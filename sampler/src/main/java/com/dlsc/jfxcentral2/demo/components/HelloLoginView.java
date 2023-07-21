package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.LoginView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloLoginView extends JFXCentralSampleBase {

    private LoginView loginView;

    @Override
    protected Region createControl() {
        loginView = new LoginView();
        return new ScrollPane(loginView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.LARGE);
        loginView.sizeProperty().bind(sizeComboBox.sizeProperty());
        return new VBox(10, new Label("Change size:"), sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "LoginView";
    }
}
