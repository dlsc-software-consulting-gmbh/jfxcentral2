package com.dlsc.jfxcentral2.demo.devtools;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.utilities.effectdesigner.EffectDesignerView;
import javafx.scene.layout.Region;

public class HelloEffectDesignerView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        return new EffectDesignerView();
    }

    @Override
    public String getSampleName() {
        return "Effect Designer View";
    }
}
