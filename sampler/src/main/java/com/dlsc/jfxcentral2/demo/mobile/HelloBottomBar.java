package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.BottomMenuBar;
import javafx.scene.layout.Region;

public class HelloBottomBar extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        return new BottomMenuBar();
    }

    @Override
    public String getSampleName() {
        return "";
    }
}
