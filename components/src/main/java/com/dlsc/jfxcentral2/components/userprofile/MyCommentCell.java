package com.dlsc.jfxcentral2.components.userprofile;

import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.model.Comment;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MyCommentCell extends PaneBase {
    private static final Logger LOGGER = LogManager.getLogger(MyCommentCell.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final Image PERSON_AVATAR_IMAGE = new Image(Objects.requireNonNull(MyCommentCell.class.getResource("/com/dlsc/jfxcentral2/demoimages/person-avatar.png")).toExternalForm());

    public MyCommentCell() {
        getStyleClass().add("my-comment-cell");
        BooleanProperty editState = new SimpleBooleanProperty(this, "editState", false);

        AvatarView avatarView = new AvatarView();

        Label commentLabel = new Label();
        commentLabel.getStyleClass().add("comment-label");
        commentLabel.setWrapText(true);
        VBox.setVgrow(commentLabel, Priority.ALWAYS);
        commentLabel.setMaxHeight(Double.MAX_VALUE);

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("date-label");

        TextArea commentArea = new TextArea();
        commentArea.getStyleClass().add("comment-area");
        commentArea.setWrapText(true);
        commentArea.managedProperty().bind(commentArea.visibleProperty());
        commentArea.visibleProperty().bind(editState);

        VBox commentBox = new VBox(commentLabel, dateLabel);
        commentBox.getStyleClass().add("comment-box");
        commentBox.managedProperty().bind(commentBox.visibleProperty());
        commentBox.visibleProperty().bind(editState.not());

        StackPane centerPane = new StackPane(commentBox, commentArea);
        centerPane.getStyleClass().add("center-pane");
        HBox.setHgrow(centerPane, Priority.ALWAYS);

        Button deleteButton = new Button("Delete", new FontIcon(IkonUtil.delete));
        deleteButton.setFocusTraversable(false);
        deleteButton.getStyleClass().addAll("fill-button", "delete-button");
        deleteButton.managedProperty().bind(deleteButton.visibleProperty());
        deleteButton.visibleProperty().bind(editState.not());

        Button editButton = new Button("Edit", new FontIcon(IkonUtil.edit));
        editButton.setFocusTraversable(false);
        editButton.getStyleClass().addAll("fill-button", "edit-button");
        editButton.managedProperty().bind(editButton.visibleProperty());
        editButton.visibleProperty().bind(editState.not());
        editButton.setOnAction(evt -> {
            editState.set(true);
            commentArea.setText(commentLabel.getText());
            commentArea.requestFocus();
        });

        Button saveButton = new Button();
        saveButton.setFocusTraversable(false);
        saveButton.getStyleClass().addAll("fill-button", "save-button");
        saveButton.textProperty().bind(sizeProperty().map(it -> it.isSmall() ? "SAVE" : "SAVE CHANGES"));
        saveButton.managedProperty().bind(saveButton.visibleProperty());
        saveButton.visibleProperty().bind(editState);
        saveButton.setOnAction(evt -> {
            Comment comment = getComment();
            if (comment != null) {
                comment.setContent(commentArea.getText());
                commentLabel.setText(comment.getContent());
                editState.set(false);
                LOGGER.info("Save comment: {}", comment.getContent());
            }
        });

        HBox buttonsBox = new HBox(deleteButton, editButton, saveButton);
        buttonsBox.getStyleClass().add("buttons-box");

        HBox contentBox = new HBox(avatarView, centerPane,new Spacer(), buttonsBox);
        contentBox.getStyleClass().add("content-box");

        getChildren().setAll(contentBox);

        deleteButton.setOnAction(evt -> {
            Comment comment = getComment();
            if (comment != null) {
                comment.setDeleted(true);
                commentLabel.setText("Comment Deleted");
                buttonsBox.setVisible(false);
                LOGGER.info("Delete comment: {}", comment.getId());
            }
        });

        commentProperty().addListener((ob, ov, comment) -> {
            if (comment != null) {
                // TODO: avatarView.setImage(ImageManager.getInstance().xxx);
                avatarView.setImage(PERSON_AVATAR_IMAGE);
                if (comment.isDeleted()) {
                    commentLabel.setText("Comment Deleted");
                    buttonsBox.setVisible(false);
                } else {
                    commentLabel.setText(comment.getContent());
                    commentArea.setText(comment.getContent());
                    buttonsBox.setVisible(true);
                }
                dateLabel.setText("Written on " + comment.getDate().format(DATE_FORMATTER));

            } else {
                avatarView.setImage(null);
                commentLabel.setText("");
                commentArea.setText("");
                dateLabel.setText("");
            }
        });
    }

    private final ObjectProperty<Comment> comment = new SimpleObjectProperty<>(this, "comment");

    public Comment getComment() {
        return comment.get();
    }

    public ObjectProperty<Comment> commentProperty() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment.set(comment);
    }
}
