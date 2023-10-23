package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CustomSearchField;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloCustomSearchField extends JFXCentralSampleBase  {
    @Override
    protected Region createControl() {
        return new StackPane(new CustomSearchField(true));
    }

    @Override
    public String getSampleName() {
        return "CustomSearchField";
    }
}
