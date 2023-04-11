package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class HelloSponsors extends JFXCentralSampleBase {

    private SponsorsView sponsorsView;

    @Override
    public String getSampleName() {
        return "Sponsors";
    }

    @Override
    public Node getPanel(Stage stage) {
        sponsorsView = new SponsorsView();
        sponsorsView.setShowLogoCount(5);
        sponsorsView.itemsProperty().addAll(
                new SponsorsView.Sponsor("JPRO",
                        getClass().getResource("logos/jpro.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new SponsorsView.Sponsor("SANDEC",
                        getClass().getResource("logos/sandec.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new SponsorsView.Sponsor("DLSC",
                        getClass().getResource("logos/dlsc.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new SponsorsView.Sponsor("NAVIELEKTRO",
                        getClass().getResource("logos/navielektro.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new SponsorsView.Sponsor(
                        "Hydraulic",
                        getClass().getResource("logos/hydraulic.png").toExternalForm(),
                        "https://www.jpro.one/")
        );

        return new ScrollPane(sponsorsView);
    }

    @Override
    public Node getControlPanel() {
        Spinner<Integer> spinner = new Spinner<>(0, 5, 5);
        sponsorsView.showLogoCountProperty().bind(spinner.valueProperty());
        ComboBox<Size> targetDeviceComboBox = new ComboBox<>();
        targetDeviceComboBox.getItems().addAll(Size.values());
        targetDeviceComboBox.getSelectionModel().select(Size.LARGE);
        sponsorsView.sizeProperty().bind(targetDeviceComboBox.getSelectionModel().selectedItemProperty());
        targetDeviceComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Size object) {
                return object.toString();
            }

            @Override
            public Size fromString(String string) {
                return null;
            }
        });

        CheckBox showDividerCheckBox = new CheckBox("Show Divider");
        showDividerCheckBox.setSelected(true);
        sponsorsView.dividerVisibleProperty().bind(showDividerCheckBox.selectedProperty());

        VBox controlBox = new VBox(10, new Label("Change size:"), targetDeviceComboBox, new Separator(),
                new Label("Show Logo Count:"), spinner, showDividerCheckBox);
        controlBox.setSpacing(10);
        controlBox.setAlignment(Pos.TOP_LEFT);
        return controlBox;
    }

}
