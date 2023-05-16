package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.kordamp.ikonli.javafx.FontIcon;

public class BookTileView extends TileView<Book> {

    public BookTileView(Book book) {
        this();
        setData(book);
    }

    public BookTileView() {
        getStyleClass().add("book-tile-view");

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        setButton1Action(() -> System.out.println("Discover book: " + (getData() != null ? getData().getUrl() : "..")));

        setButton2Text("amazon");
        Image AMAZON_IMAGE = new Image(BookTileView.class.getResource("/com/dlsc/jfxcentral2/images/amazon.png").toExternalForm());
        setButton2Graphic(new ImageView(AMAZON_IMAGE));
        setButton2Action(() -> System.out.println("Open amazon: " + (getData() != null ? getData().getUrl() : "..")));

    }
}
