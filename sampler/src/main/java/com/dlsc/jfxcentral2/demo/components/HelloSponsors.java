package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.Sponsors;
import com.dlsc.jfxcentral2.components.Target;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.demo.utils.ResourceUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class HelloSponsors extends JFXCentralSampleBase {

    @Override
    public String getSampleName() {
        return "Sponsors";
    }

    @Override
    public Node getPanel(Stage stage) {
        Sponsors sponsors = new Sponsors();
        sponsors.setShowLogoCount(5);
        sponsors.itemsProperty().addAll(
                new Sponsors.Sponsor("JPRO",
                        ResourceUtil.load("/com/dlsc/jfxcentral2/demo/components/logos/jpro.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new Sponsors.Sponsor("SANDEC",
                        ResourceUtil.load("/com/dlsc/jfxcentral2/demo/components/logos/sandec.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new Sponsors.Sponsor("DLSC",
                        ResourceUtil.load("/com/dlsc/jfxcentral2/demo/components/logos/dlsc.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new Sponsors.Sponsor("NAVIELEKTRO",
                        ResourceUtil.load("/com/dlsc/jfxcentral2/demo/components/logos/navielektro.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new Sponsors.Sponsor(
                        "Hydraulic",
                        ResourceUtil.load("/com/dlsc/jfxcentral2/demo/components/logos/hydraulic.png").toExternalForm(),
                        "https://www.jpro.one/")
        );

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(sponsors);

        Spinner<Integer> spinner = new Spinner<>(0, 5, 5);
        sponsors.showLogoCountProperty().bind(spinner.valueProperty());
        ComboBox<Target> targetDeviceComboBox = new ComboBox<>();
        targetDeviceComboBox.getItems().addAll(Target.values());
        targetDeviceComboBox.getSelectionModel().select(Target.DESKTOP);
        sponsors.targetProperty().bind(targetDeviceComboBox.getSelectionModel().selectedItemProperty());
        targetDeviceComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Target object) {
                return object.toString();
            }

            @Override
            public Target fromString(String string) {
                return null;
            }
        });

        VBox box1 = new VBox(10,new Label("Change target device:"), targetDeviceComboBox);
        box1.setAlignment(Pos.CENTER_LEFT);
        VBox box2 = new VBox(10,new Label("Show Logo Count:"), spinner);
        box2.setAlignment(Pos.CENTER_LEFT);

        HBox topBox = new HBox(10, box1,box2 );
        topBox.setSpacing(10);
        topBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topBox);
        return borderPane;
    }


}
