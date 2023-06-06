package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.CompanyTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloCompanyTileView extends JFXCentralSampleBase {

    private CompanyTileView companyTileView;

    @Override
    protected Region createControl() {
        Company company = new Company();
        company.setName("DLSC Software & Consulting GmbH");
        company.setDescription("DLSC is a software company located in Zurich, Switzerland. They are specialised in developing JavaFX and Swing frameworks and providing consulting services. DLSC is best known for its professional Gantt chart, timeline, and calendar controls for planning and scheduling applications. DLSC is the founder and organizer of the annual");
        companyTileView = new CompanyTileView(company);
        return new StackPane(companyTileView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        companyTileView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "CompanyTileView";
    }
}
