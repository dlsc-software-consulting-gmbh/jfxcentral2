package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.BookTileView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.tiles.BookTileData;
import javafx.scene.Node;
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
        SizeComboBox sizeComboBox = new SizeComboBox();
        bookTileView.sizeProperty().bind(sizeComboBox.valueProperty());
        return new VBox(10, sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "BookTileView";
    }
}
