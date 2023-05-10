package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.headers.BookDetailHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Book;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloBookDetailHeader extends JFXCentralSampleBase {

    private BookDetailHeader bookDetailHeader;

    @Override
    protected Region createControl() {
        bookDetailHeader = new BookDetailHeader(new Book("The Definitive Guide to Modern Java Clients with JavaFX", "Leveraging the JavaFX APIs","https://www.jfx-central.com/home", true, false));
        return new ScrollPane(new StackPane(bookDetailHeader));
    }

    @Override
    public Node getControlPanel() {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton largeBox = new RadioButton("Large");
        RadioButton mediumBox = new RadioButton("Medium");
        toggleGroup.getToggles().addAll(largeBox, mediumBox);
        toggleGroup.selectedToggleProperty().addListener((ob, ov, nv) -> {
            if (nv == largeBox) {
                bookDetailHeader.setSize(Size.LARGE);
            } else if (nv == mediumBox) {
                bookDetailHeader.setSize(Size.MEDIUM);
            }
        });
        mediumBox.setSelected(true);
        return new VBox(20, largeBox, mediumBox);
    }

    @Override
    public String getSampleName() {
        return "AppDetailHeader";
    }
}
