package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.StringUtil;
import com.dlsc.jfxcentral2.utils.images.CentralImageManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import one.jpro.platform.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class BookTileView extends PreviewTileView<Book> {

    private static final Image AMAZON_IMAGE = new Image(Objects.requireNonNull(BookTileView.class.getResource("/com/dlsc/jfxcentral2/images/amazon.png")).toExternalForm());

    public BookTileView(Book book) {
        super(book);

        getStyleClass().add("book-tile-view");

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));

        imageProperty().set(CentralImageManager.getBookCoverImage1(book));

        LinkUtil.setLink(getButton1(), "/books/" + book.getId());

        if (StringUtils.isNotBlank(book.getAmazonASIN())) {
            setButton2Text("amazon");
            setButton2Graphic(new ImageView(AMAZON_IMAGE));
            setButton2Visible(true);
            LinkUtil.setExternalLink(getButton2(), "https://www.amazon.com/dp/" + book.getAmazonASIN(), book.getName());
        } else if (StringUtils.isNotBlank(book.getUrl())) {
            setButton2Text(StringUtil.getDomainName(book.getUrl()));
            setButton2Graphic(new FontIcon(IkonUtil.website));
            setButton2Visible(true);
            LinkUtil.setExternalLink(getButton2(), book.getUrl(), book.getName());
        } else {
            setButton2Visible(false);
        }
    }

    @Override
    protected void imageViewSizeBinding(CustomImageView imageView, StackPane imageContainer) {
        imageView.fitWidthProperty().bind(imageContainer.widthProperty().subtract(10));
        imageView.fitHeightProperty().bind(imageContainer.heightProperty().subtract(10));
    }
}

