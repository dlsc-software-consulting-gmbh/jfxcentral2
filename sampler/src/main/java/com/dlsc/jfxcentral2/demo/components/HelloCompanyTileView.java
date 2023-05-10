package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.tiles.CompanyTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Company;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloCompanyTileView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        Company company = new Company(
                "App DLSC Software & Consulting GmbH",
                "DLSC is a software company located in Zurich, Switzerland. They are specialised in developing JavaFX and Swing frameworks and providing consulting services. DLSC is best known for its professional Gantt chart, timeline, and calendar controls for planning and scheduling applications. DLSC is the founder and organizer of the annual",
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/company-thumbnail-01.png").toExternalForm()),
                "https://www.dlsc.com",
                false,
                true);

        CompanyTileView companyTileView = new CompanyTileView(company);
        return new StackPane(companyTileView);
    }

    @Override
    public String getSampleName() {
        return "CompanyTileView";
    }
}
