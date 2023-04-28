package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.PersonDetailHeader;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Badge;
import com.dlsc.jfxcentral2.model.Person;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;

import java.util.List;

public class HelloPersonDetailHeader extends JFXCentralSampleBase {

    private PersonDetailHeader personDetailHeader;

    @Override
    protected Region createControl() {
        personDetailHeader = new PersonDetailHeader();

        personDetailHeader.setPerson(new Person(
                "Dirk Lemmermann",
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/person-avatar.png").toExternalForm()),
                "JavaFX Fan Boy. Java Champion. Java One Rockstar. Open Source Contributor. Freelancer. Consultant. Speaker. Entrepreneur.",
                "https://twitter.com/dlemmermann",
                "https://www.linkedin.com/in/dlemmermann/",
                "https://www.dlsc.com",
                "mailto:dlemmermann@gmail.com",
                "https://github.com/dlsc-software-consulting-gmbh",
                List.of(new Badge("Champion", Material2OutlinedMZ.VERIFIED),
                        new Badge("Rockstar", MaterialDesignS.STAR_FOUR_POINTS_OUTLINE))));

        return new ScrollPane(new StackPane(personDetailHeader));
    }

    @Override
    public Node getControlPanel() {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton largeBox = new RadioButton("Large");
        RadioButton mediumBox = new RadioButton("Medium");
        toggleGroup.getToggles().addAll(largeBox, mediumBox);
        toggleGroup.selectedToggleProperty().addListener((ob, ov, nv) ->{
            if (nv == largeBox) {
                personDetailHeader.setSize(Size.LARGE);
            } else if (nv == mediumBox) {
                personDetailHeader.setSize(Size.MEDIUM);
            }
        });
        mediumBox.setSelected(true);
        return new VBox(20, largeBox, mediumBox);
    }

    @Override
    public String getSampleName() {
        return "PersonDetailHeader";
    }
}
