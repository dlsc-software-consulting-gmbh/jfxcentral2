package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloTopMenuBar extends JFXCentralSampleBase {

    private TopMenuBar topMenuBar;

    @Override
    protected Region createControl() {
        topMenuBar = new TopMenuBar();
        topMenuBar.getStylesheets().add(HelloTopMenuBar.class.getResource("test.css").toExternalForm());
        return new ScrollPane(topMenuBar);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        topMenuBar.sizeProperty().bind(sizeComboBox.sizeProperty());

        ComboBox<String> themeComboBox = new ComboBox<>();
        String[] themes = {"dark", "light", "transparent", "bg-image"};
        themeComboBox.getItems().addAll(themes);
        themeComboBox.getSelectionModel().select("dark");
        themeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            topMenuBar.getStyleClass().removeAll("dark", "light","transparent","bg-image");
            topMenuBar.getStyleClass().add(newValue);
        });
        return new VBox(10, new Label("Change Size:"), sizeComboBox, new Label("Change Theme:"), themeComboBox);
    }

    @Override
    public String getSampleName() {
        return "TopMenuBar";
    }
}
