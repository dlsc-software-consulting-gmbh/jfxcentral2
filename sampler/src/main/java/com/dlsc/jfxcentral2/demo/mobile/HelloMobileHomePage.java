package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.pages.MobileHomePage;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloMobileHomePage extends JFXCentralSampleBase {

    private MobileHomePage homePage;

    @Override
    protected Region createControl() {
        homePage = MobileHomePage.getInstance();

        ScrollPane scrollPane = new ScrollPane(homePage);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }


    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        homePage.sizeProperty().bind(sizeComboBox.sizeProperty());
        Platform.runLater(sizeComboBox::requestFocus);
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "MobileHomePage";
    }
}
