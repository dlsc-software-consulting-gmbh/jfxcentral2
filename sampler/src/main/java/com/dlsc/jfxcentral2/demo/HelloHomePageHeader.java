package com.dlsc.jfxcentral2.demo;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.mobile.home.HomePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class HelloHomePageHeader extends JFXCentralSampleBase {

    private HomePageHeader header;

    @Override
    protected Region createControl() {
        header = new HomePageHeader();
        return header;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        header.sizeProperty().bind(sizeComboBox.valueProperty());
        return createSimpleControlPanel(new CellData("Size", sizeComboBox));
    }

    @Override
    public String getSampleName() {
        return "Home Page Header";
    }
}
