package com.dlsc.jfxcentral2.demo.components.overviewbox;

import com.dlsc.jfxcentral.data.model.Coordinates;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.overviewbox.LibraryCoordinatesBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloLibraryCoordinatesBox extends JFXCentralSampleBase {

    private LibraryCoordinatesBox libraryCoordinatesBox;

    @Override
    protected Region createControl() {
        Coordinates coordinates = new Coordinates() {
            @Override
            public String getGroupId() {
                return "com.calendarfx";
            }

            @Override
            public String getArtifactId() {
                return "calendar";
            }

        };
        libraryCoordinatesBox = new LibraryCoordinatesBox();
        libraryCoordinatesBox.setCoordinates(coordinates);
        libraryCoordinatesBox.setDescription("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem ");

        return new ScrollPane(libraryCoordinatesBox);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        libraryCoordinatesBox.sizeProperty().bind(sizeComboBox.sizeProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "LibraryCoordinatesBox";
    }
}
