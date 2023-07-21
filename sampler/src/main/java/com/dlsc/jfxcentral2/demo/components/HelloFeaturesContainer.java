package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloFeaturesContainer extends JFXCentralSampleBase {

    private FeaturesContainer smallFC;
    private FeaturesContainer mediumFC;
    private FeaturesContainer largerFC;

    @Override
    protected Region createControl() {

        smallFC = new FeaturesContainer();
        smallFC.setSize(Size.SMALL);

        mediumFC = new FeaturesContainer();
        mediumFC.setSize(Size.MEDIUM);

        largerFC = new FeaturesContainer();
        largerFC.setSize(Size.LARGE);

        return new TabPane(
                new Tab("FeaturesContainer sm", wrap(smallFC)),
                new Tab("FeaturesContainer md", wrap(mediumFC)),
                new Tab("FeaturesContainer ld", wrap(largerFC))
        );
    }

    private Node wrap(Region node) {
        node.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane wrapper = new StackPane(node);
        StackPane.setAlignment(node, Pos.CENTER);
        return wrapper;
    }

    @Override
    public Node getControlPanel() {
        ComboBox<Orientation> orientationBox = new ComboBox<>();
        orientationBox.getItems().addAll(Orientation.HORIZONTAL, Orientation.VERTICAL);
        orientationBox.getSelectionModel().select(Orientation.HORIZONTAL);
        orientationBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            smallFC.setOrientation(newValue);
            mediumFC.setOrientation(newValue);
            largerFC.setOrientation(newValue);
        });
        return orientationBox;
    }

    @Override
    public String getSampleName() {
        return "FeaturesContainer";
    }
}
