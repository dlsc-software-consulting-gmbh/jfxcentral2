package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloAvatarView extends JFXCentralSampleBase {

    private AvatarView avatarView;

    @Override
    protected Region createControl() {
        avatarView = new AvatarView();
        avatarView.getStyleClass().add("test-avatar-view");
        avatarView.setImage(new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/quick-link-lg2.png").toExternalForm()));
        ScrollPane scrollPane = new ScrollPane(new StackPane(avatarView));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    @Override
    public Node getControlPanel() {
        ComboBox<AvatarView.Type> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(AvatarView.Type.values());
        typeBox.getSelectionModel().select(AvatarView.Type.SQUARE);
        avatarView.typeProperty().bind(typeBox.valueProperty());

        Spinner<Integer> roundSpinner = new Spinner<>(0, 300, 10, 10);
        avatarView.roundSizeProperty().bind(roundSpinner.valueProperty());

        Node roundControl = createControlNode("Round Number", roundSpinner);
        roundControl.managedProperty().bind(roundControl.visibleProperty());
        roundControl.visibleProperty().bind(typeBox.valueProperty().isEqualTo(AvatarView.Type.SQUARE));

        Spinner<Integer> avatarSize = new Spinner<>(0, 300, 100, 10);
        avatarView.avatarSizeProperty().bind(avatarSize.valueProperty());

        CheckBox smoothCheckBox = new CheckBox("Smooth");
        smoothCheckBox.setSelected(true);
        avatarView.smoothProperty().bind(smoothCheckBox.selectedProperty());

        TextField textField = new TextField();
        textField.setPromptText("Enter Image Url");

        Button button = new Button("Set Image");
        button.setOnAction(evt -> avatarView.setImage(new Image(textField.getText(), true)));

        return new VBox(15,
                createControlNode("Avatar type", typeBox),
                roundControl,
                createControlNode("Avatar Size", avatarSize),
                createControlNode("Smooth", smoothCheckBox),
                createControlNode("Avatar ImageUrl", textField, button));
    }

    private Node createControlNode(String desc, Node... controls) {
        VBox box = new VBox(5);
        box.getChildren().add(new Label(desc));
        box.getChildren().addAll(controls);
        box.getChildren().add(new Separator());
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(2));
        return box;
    }

    @Override
    public double getControlPanelDividerPosition() {
        return 0.75;
    }

    @Override
    public String getSampleName() {
        return "AvatarView";
    }
}
