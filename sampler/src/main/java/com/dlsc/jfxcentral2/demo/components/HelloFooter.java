package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.Footer;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.stage.Stage;

public class HelloFooter extends JFXCentralSampleBase {


    @Override
    public String getSampleName() {
        return "Footer";
    }

    @Override
    public Node getPanel(Stage stage) {
        return new Footer();
    }
}
