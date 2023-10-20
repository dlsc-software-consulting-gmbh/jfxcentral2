package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.TextCopyView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class HelloTextCopyView extends JFXCentralSampleBase {

    private TextCopyView textCopyView;

    @Override
    protected Region createControl() {
        textCopyView = new TextCopyView();
        textCopyView.getOptions().addAll("JavaFX Code","Css Code");
        textCopyView.setSelectedIndex(0);
        textCopyView.textProperty().bind(Bindings.createStringBinding(() -> {
            String selectedItem = textCopyView.getSelectedItem();
            if (Objects.equals(selectedItem, "JavaFX Code")) {
                return "public static void main(String[] args)";
            } else {
                return "-fx-background-color: -bright-blue;";
            }
        }, textCopyView.selectedItemProperty()));

        return textCopyView;
    }

    @Override
    public Node getControlPanel() {
        ComboBox<TextCopyView.ViewMode> comboBox = new ComboBox<>(FXCollections.observableArrayList(TextCopyView.ViewMode.values()));
        comboBox.getSelectionModel().select(textCopyView.getViewMode());
        textCopyView.viewModeProperty().bind(comboBox.getSelectionModel().selectedItemProperty());
        Label test = new Label("View Mode");
        return new VBox(test, comboBox);
    }

    @Override
    public String getSampleName() {
        return "TextCopyView";
    }
}
