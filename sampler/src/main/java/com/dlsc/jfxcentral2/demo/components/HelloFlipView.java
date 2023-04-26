package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.FlipView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HelloFlipView extends JFXCentralSampleBase {

    private FlipView flipView;

    @Override
    protected Region createControl() {
        flipView = new FlipView();
        flipView.getStyleClass().add("hello-flip-view");
        // create front node
        ImageView frontImage = new ImageView(new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/quick-link-lg1.png").toExternalForm()));
        frontImage.setPreserveRatio(true);
        frontImage.setFitWidth(420);

        Button flipToBackBtn = new Button("Click Flip to Back");
        flipToBackBtn.setOnAction(evt -> flipView.flipToBack());
        StackPane frontPane = new StackPane(frontImage, flipToBackBtn);
        StackPane.setAlignment(flipToBackBtn, Pos.TOP_LEFT);
        StackPane.setMargin(flipToBackBtn, new Insets(10));

        flipView.setFrontNode(frontPane);

        // create back node
        ImageView backImage = new ImageView(new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/quick-link-lg2.png").toExternalForm()));
        backImage.setPreserveRatio(true);
        backImage.setFitWidth(420);

        Button flipToFrontBtn = new Button("Click Flip to Front");
        flipToFrontBtn.setOnAction(evt -> flipView.flipToFront());
        StackPane backPane = new StackPane(backImage, flipToFrontBtn);
        StackPane.setAlignment(flipToFrontBtn, Pos.TOP_LEFT);
        StackPane.setMargin(flipToFrontBtn, new Insets(10));

        flipView.setBackNode(backPane);
        flipView.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        return new StackPane(flipView);
    }

    @Override
    public Node getControlPanel() {
        CheckBox animateOnFlipBox = new CheckBox("Animate on Flip");
        animateOnFlipBox.setSelected(true);
        flipView.animateOnFlipProperty().bind(animateOnFlipBox.selectedProperty());

        Spinner<Double> flipTimeSpinner = new Spinner<>(0.1, 5, 1, 0.5);
        flipView.flipTimeProperty().bind(Bindings.createObjectBinding(() -> Duration.seconds(flipTimeSpinner.getValue()), flipTimeSpinner.valueProperty()));

        return new VBox(15,new Label("Animate on Flip"),animateOnFlipBox,new Separator(),new Label("Flip Time (Second)"), flipTimeSpinner);
    }


    @Override
    public String getSampleName() {
        return "FlipView";
    }
}
