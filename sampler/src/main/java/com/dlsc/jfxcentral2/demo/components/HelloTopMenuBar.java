package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.routing.View;

public class HelloTopMenuBar extends JFXCentralSampleBase {

    private TopMenuBar topMenuBar;

    @Override
    protected Region createControl() {
        topMenuBar = new TopMenuBar(new View() {
            @Override
            public String title() {
                return "Test";
            }

            @Override
            public String description() {
                return "";
            }

            @Override
            public Node content() {
                return new Label();
            }
        });
        topMenuBar.getStylesheets().add(HelloTopMenuBar.class.getResource("test.css").toExternalForm());
        return new ScrollPane(topMenuBar);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        topMenuBar.sizeProperty().bind(sizeComboBox.sizeProperty());

        ComboBox<Mode> themeComboBox = new ComboBox<>();
        themeComboBox.getItems().addAll(Mode.values());
        themeComboBox.getSelectionModel().select(topMenuBar.getMode());
        topMenuBar.modeProperty().bind(themeComboBox.getSelectionModel().selectedItemProperty());
        return new VBox(10, new Label("Change Size:"), sizeComboBox, new Label("Change Theme:"), themeComboBox);
    }

    @Override
    public String getSampleName() {
        return "TopMenuBar";
    }
}
