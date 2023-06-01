package com.dlsc.jfxcentral2.demo.others;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.layout.Region;

public class BackgroundImageTestApp extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        Region region = new Region();
        region.getStyleClass().add("bg-test");
        return region;
    }

    @Override
    public String getSampleName() {
        return "Test Background Image";
    }
}
