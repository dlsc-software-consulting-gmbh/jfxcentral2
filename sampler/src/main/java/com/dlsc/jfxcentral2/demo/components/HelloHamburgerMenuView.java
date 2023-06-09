package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.hamburger.HamburgerMenuView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloHamburgerMenuView extends JFXCentralSampleBase {

    private HamburgerMenuView menuView;

    @Override
    protected Region createControl() {
        menuView = new HamburgerMenuView(false);
        return new ScrollPane(menuView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.MEDIUM);
        menuView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "HamburgerMenuView";
    }
}
