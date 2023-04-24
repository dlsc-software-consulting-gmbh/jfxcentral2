package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloSponsorsView extends JFXCentralSampleBase {

    private SponsorsView sponsorsView;

    @Override
    public String getSampleName() {
        return "Sponsors";
    }

    @Override
    protected Region createControl() {
        sponsorsView = new SponsorsView();
        sponsorsView.getStylesheets().add(getClass().getResource("test.css").toExternalForm());
        return new ScrollPane(sponsorsView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        sponsorsView.sizeProperty().bind(sizeComboBox.sizeProperty());
        sponsorsView.sizeProperty().addListener((ob, ov, nv) ->{
            System.out.println("sponsorsView.size: " + nv);
        });
        CheckBox showDividerCheckBox = new CheckBox("Show Divider");
        showDividerCheckBox.setSelected(true);
        sponsorsView.dividerVisibleProperty().bind(showDividerCheckBox.selectedProperty());

        VBox controlBox = new VBox(10, new Label("Change size:"), sizeComboBox, new Separator(), showDividerCheckBox);
        controlBox.setSpacing(10);
        controlBox.setAlignment(Pos.TOP_LEFT);
        return controlBox;
    }

}
