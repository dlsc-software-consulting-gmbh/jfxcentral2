package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.BottomBar;
import javafx.scene.layout.Region;

public class HelloBottomBar extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        return new BottomBar();
    }

    @Override
    public String getSampleName() {
        return "";
    }
}
