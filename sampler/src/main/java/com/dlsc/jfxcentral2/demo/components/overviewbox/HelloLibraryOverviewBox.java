package com.dlsc.jfxcentral2.demo.components.overviewbox;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.overviewbox.LibraryOverviewBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloLibraryOverviewBox extends JFXCentralSampleBase {

    private LibraryOverviewBox box;

    @Override
    protected Region createControl() {
        Library library = new Library();
        //library.setId("flexganttfx");
        library.setId("charts");
        library.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae.");

        box = new LibraryOverviewBox(library);
        return new ScrollPane(box);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        box.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "LibraryOverviewBox";
    }
}
