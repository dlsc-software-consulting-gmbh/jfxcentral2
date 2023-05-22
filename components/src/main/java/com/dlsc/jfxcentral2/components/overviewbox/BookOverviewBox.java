package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
        this();
        setData(book);
    }

    public BookOverviewBox() {
        getStyleClass().add("book-overview-box");
        baseURLProperty().bind(Bindings.createStringBinding(() -> DataRepository.getInstance().getRepositoryDirectoryURL() + "books/" + getData().getId(), dataProperty()));
        dataProperty().addListener(it -> fillData());
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

        fillData();
        return topBoxWrapper;
    }

    private void fillData() {
        Book book = getData();
        if (!isSmall()) {
            if (previewImageView.imageProperty().isBound()) {
                previewImageView.imageProperty().unbind();
            }
            previewImageView.setImage(null);
        }

        if (book != null) {
            writtenByLabel.setText(book.getAuthors());
            publisherLabel.setText(book.getPublisher());
            publishDateLabel.setText(book.getPublishedDate().format(DATE_FORMATTER));
            isbnLabel.setText(book.getIsbn());
            setMarkdown(book.getDescription());
            if (!isSmall()) {
                //previewImageView.imageProperty().bind(ImageManager.getInstance().bookCoverImageProperty(book));
                //add test image
                previewImageView.setImage(new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/book-thumbnail-01.png").toExternalForm()));
            }
        } else {
            writtenByLabel.setText("");
            publisherLabel.setText("");
            publishDateLabel.setText("");
            isbnLabel.setText("");
            setMarkdown("");
        }
    }

}
