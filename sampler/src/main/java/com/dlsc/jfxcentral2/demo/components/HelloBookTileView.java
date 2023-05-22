package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.BookTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloBookTileView extends JFXCentralSampleBase {

    private BookTileView bookTileView;

    @Override
    protected Region createControl() {
        Book book = new Book();
        book.setName("The Definitive Guide to Modern Java Clients with JavaFX");
        book.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.");
        bookTileView = new BookTileView();
        bookTileView.setData(book);
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
