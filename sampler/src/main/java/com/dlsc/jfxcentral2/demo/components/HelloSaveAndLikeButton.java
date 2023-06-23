package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloSaveAndLikeButton extends JFXCentralSampleBase {

    private SaveAndLikeButton saveAndLikeButton;

    @Override
    protected Region createControl() {
        saveAndLikeButton = new SaveAndLikeButton();
        saveAndLikeButton.setSaveCount(56);
        saveAndLikeButton.setLikeCount(123);
        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("hello-save-and-like-button");
        stackPane.getChildren().add(saveAndLikeButton);
        return stackPane;
    }

    @Override
    public Node getControlPanel() {
        CheckBox saveButtonVisibleCheckBox = new CheckBox("Save Button Visible");
        saveButtonVisibleCheckBox.setSelected(true);
        saveAndLikeButton.saveButtonVisibleProperty().bind(saveButtonVisibleCheckBox.selectedProperty());

        CheckBox likeButtonVisibleCheckBox = new CheckBox("Like Button Visible");
        likeButtonVisibleCheckBox.setSelected(true);
        saveAndLikeButton.likeButtonVisibleProperty().bind(likeButtonVisibleCheckBox.selectedProperty());

        TextField saveTextField = new TextField("Save");
        saveAndLikeButton.saveButtonTextProperty().bind(saveTextField.textProperty());

        TextField likeTextField = new TextField("Like");
        saveAndLikeButton.likeButtonTextProperty().bind(likeTextField.textProperty());

        SizeComboBox sizeComboBox = new SizeComboBox();
        saveAndLikeButton.sizeProperty().bind(sizeComboBox.valueProperty());

        CheckBox showCountButton = new CheckBox("Show Count");
        saveAndLikeButton.showCountProperty().bind(showCountButton.selectedProperty());

        VBox vBox = new VBox(10,
                saveButtonVisibleCheckBox,
                likeButtonVisibleCheckBox,
                new Separator(),
                new Label("Save Text"),
                saveTextField,
                new Separator(),
                new Label("Like Text"),
                likeTextField,
                new Label("Change Size"),
                new Label("Show Count"),
                showCountButton,
                sizeComboBox
        );

        saveAndLikeButton.saveButtonSelectedProperty().addListener((ob, ov, nv) -> {
            System.out.println(nv ? "Save selected" : "Save unselected");
        });

        saveAndLikeButton.likeButtonSelectedProperty().addListener((ob, ov, nv) -> {
            System.out.println(nv ? "Like selected" : "Like unselected");
        });

        return vBox;
    }

    @Override
    public String getSampleName() {
        return "SaveAndLikeButton";
    }

    @Override
    public double getControlPanelDividerPosition() {
        return 0.5;
    }
}
