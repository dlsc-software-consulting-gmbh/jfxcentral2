package com.dlsc.jfxcentral2.demo.components.overviewbox;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.overviewbox.ShowcaseOverviewBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.time.LocalDate;

public class HelloAppOverviewBox extends JFXCentralSampleBase {

    private ShowcaseOverviewBox appOverviewBox;

    @Override
    protected Region createControl() {
        RealWorldApp app = new RealWorldApp();
        app.setLocation("Brazil");
        app.setDomain("Healthcare");
        app.setCompany("John Doe");
        app.setCreatedOn(LocalDate.now());
        app.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae.");
        appOverviewBox = new ShowcaseOverviewBox(app);

        return new ScrollPane(appOverviewBox);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        appOverviewBox.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "AppOverviewBox";
    }
}
