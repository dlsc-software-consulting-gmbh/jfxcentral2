package com.dlsc.jfxcentral2.demo.components.overviewbox;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.overviewbox.BookOverviewBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.time.LocalDate;

public class HelloBookOverviewBox extends JFXCentralSampleBase {

    private BookOverviewBox bookOverviewBox;

    @Override
    protected Region createControl() {
        Book book = new Book();
        book.setAuthors("Carl Dea, Mark Heckler, Gerrit Grunwald, José Pereda, Sean Phillips");
        book.setPublisher("Apress");
        book.setPublishedDate(LocalDate.now());
        book.setIsbn("978-1-4842-1960-7");
        book.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae.");
        bookOverviewBox = new BookOverviewBox(book);

        return new ScrollPane(bookOverviewBox);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        bookOverviewBox.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "BookOverviewBox";
    }
}
