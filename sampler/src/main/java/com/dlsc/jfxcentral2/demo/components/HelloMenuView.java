package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.MenuView;
import com.dlsc.jfxcentral2.components.OrientationComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloMenuView extends JFXCentralSampleBase {

    private MenuView menuView;

    @Override
    protected Region createControl() {
        menuView = new MenuView(FXCollections.observableArrayList(
                new MenuView.Item("TOOLS", null, null),
                new MenuView.Item("BLOGS", null, null),
                new MenuView.Item("DOWNLOADS", null, null),
                new MenuView.Item("LIBRARIES", null, null),
                new MenuView.Item("VIDEOS", null, null),
                new MenuView.Item("APP", null, null),
                new MenuView.Item("BOOK", null, null),
                new MenuView.Item("TIPS & TRICKS", null, null)
        ));
        return new ScrollPane(menuView);
    }

    @Override
    public Node getControlPanel() {
        OrientationComboBox comboBox = new OrientationComboBox(Orientation.VERTICAL);
        menuView.orientationProperty().bind(comboBox.orientationProperty());
        return comboBox;
    }

    @Override
    public String getSampleName() {
        return "Hello MenuView";
    }

    @Override
    public double getControlPanelDividerPosition() {
        return 0.7;
    }
}
