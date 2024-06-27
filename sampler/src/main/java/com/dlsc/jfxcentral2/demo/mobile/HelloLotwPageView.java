package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.pages.MobileLinksOfTheWeekPage;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloLotwPageView extends JFXCentralSampleBase {

    private MobileLinksOfTheWeekPage mobileLinksOfTheWeekPage;

    @Override
    protected Region createControl() {
        mobileLinksOfTheWeekPage = new MobileLinksOfTheWeekPage();
        ScrollPane scrollPane = new ScrollPane(mobileLinksOfTheWeekPage);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.MEDIUM);
        mobileLinksOfTheWeekPage.sizeProperty().bind(sizeComboBox.sizeProperty());

        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "LotwPageView";
    }
}
