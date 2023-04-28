package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.BookTileView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.BookTileData;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloBookTileView extends JFXCentralSampleBase {

    private BookTileView bookTileView;

    @Override
    protected Region createControl() {
        BookTileData book = new BookTileData(
                "The Definitive Guide to Modern Java Clients with JavaFX",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.",
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/book-thumbnail-01.png").toExternalForm()),
                "https://www.dlsc.com",
                true,
                false);

        bookTileView = new BookTileView(book);

        return new StackPane(bookTileView);
    }

    @Override
    public Node getControlPanel() {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton largerButton = new RadioButton("Large");
        RadioButton smallerButton = new RadioButton("Small");
        toggleGroup.getToggles().addAll(largerButton, smallerButton);
        largerButton.setSelected(true);
        largerButton.selectedProperty().addListener((ob, ov, nv) ->{
            if (nv) {
                bookTileView.setSize(Size.LARGE);
            }
        });
        smallerButton.selectedProperty().addListener((ob, ov, nv) ->{
            if (nv) {
                bookTileView.setSize(Size.SMALL);
            }
        });
        return new VBox(10, largerButton, smallerButton);
    }

    @Override
    public String getSampleName() {
        return "BookTileView";
    }
}
