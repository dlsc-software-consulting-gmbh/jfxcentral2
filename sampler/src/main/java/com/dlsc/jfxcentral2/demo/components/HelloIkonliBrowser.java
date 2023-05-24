package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.IkonliBrowser;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.layout.Region;

public class HelloIkonliBrowser extends JFXCentralSampleBase {

    @Override
    protected Region createControl() {
        return new IkonliBrowser();
    }

    @Override
    public String getSampleName() {
        return "Ikonli Browser";
    }
}
