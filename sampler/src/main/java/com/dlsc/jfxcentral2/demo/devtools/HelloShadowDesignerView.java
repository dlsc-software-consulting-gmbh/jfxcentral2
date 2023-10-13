package com.dlsc.jfxcentral2.demo.devtools;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.utilities.shadowdesigner.ShadowDesignerView;
import javafx.scene.layout.Region;

public class HelloShadowDesignerView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        return new ShadowDesignerView();
    }

    @Override
    public String getSampleName() {
        return "Shadow Designer View";
    }
}
