package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class BookOverviewBox extends OverviewBox<Book> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private Label writtenByLabel;
    private Label publisherLabel;
    private Label publishDateLabel;
    private Label isbnLabel;
    private CustomImageView previewImageView;

    public BookOverviewBox(Book book) {
        super(book);
        getStyleClass().add("book-overview-box");
        setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "books/" + getModel().getId());
        writtenByLabel.setText(book.getAuthors());
        publisherLabel.setText(book.getPublisher());
        publishDateLabel.setText(book.getPublishedDate().format(DATE_FORMATTER));
        isbnLabel.setText(book.getIsbn());
        markdownProperty().bind(DataRepository.getInstance().bookTextProperty(book));
        previewImageView.imageProperty().bind(ImageManager.getInstance().bookCoverImageProperty(book));
    }

    @Override
    protected Node createTopNode() {
        Label writtenBy = new Label("WRITTEN BY");
        writtenBy.getStyleClass().add("field-title");

        writtenByLabel = new Label();
        writtenByLabel.getStyleClass().add("field-value");
        writtenByLabel.setWrapText(true);

        Label publisher = new Label("PUBLISHER");
        publisher.getStyleClass().add("field-title");

        publisherLabel = new Label();
        publisherLabel.getStyleClass().add("field-value");
        publisherLabel.setWrapText(true);

        Label publishDate = new Label("PUBLISH DATE");
        publishDate.getStyleClass().add("field-title");

        publishDateLabel = new Label();
        publishDateLabel.getStyleClass().add("field-value");
        publishDateLabel.setWrapText(true);

        Label isbn = new Label("ISBN");
        isbn.getStyleClass().add("field-title");

        isbnLabel = new Label();
        isbnLabel.getStyleClass().add("field-value");

        VBox topBox = new VBox(
                writtenBy,
                writtenByLabel,
                publisher,
                publisherLabel,
                publishDate,
                publishDateLabel,
                isbn,
                isbnLabel
        );
        isbnLabel.getStyleClass().add("last");
        topBox.getStyleClass().add("top-box");
        HBox topBoxWrapper = new HBox();
        topBoxWrapper.getStyleClass().add("top-box-wrapper");
        if (!isSmall()) {
            previewImageView = new CustomImageView();
            previewImageView.getStyleClass().add("preview-image");

            StackPane imageWrapper = new StackPane(previewImageView);
            imageWrapper.getStyleClass().add("image-wrapper");
            topBoxWrapper.getChildren().add(imageWrapper);
        }
        topBoxWrapper.getChildren().add(topBox);

        return topBoxWrapper;
    }
}
