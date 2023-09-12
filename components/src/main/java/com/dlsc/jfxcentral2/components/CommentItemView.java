package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Badge;
import com.dlsc.jfxcentral2.model.Comment;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

public class CommentItemView extends PaneBase {
    private static final Logger LOGGER = LogManager.getLogger(CommentItemView.class);

    private static final String DELETED_PLACEHOLDER = "Comment Deleted";
    private final Label commentLabel;
    private final HBox badgeBox;
    private final Button deleteButton;

    public CommentItemView() {
        getStyleClass().add("comment-item-view");
        BorderPane contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");

        commentLabel = new Label();
        commentLabel.getStyleClass().add("comment");
        commentLabel.setWrapText(true);
        commentLabel.setEllipsisString("...read more");
        commentLabel.setMinHeight(Region.USE_PREF_SIZE);

        BooleanBinding booleanBinding = Bindings.createBooleanBinding(() -> {
            Comment temp = getComment();
            if (temp == null) {
                return false;
            }
            return isHover() && !temp.isDeleted();
        }, hoverProperty(), commentProperty());

        deleteButton = new Button("DELETE");
        deleteButton.setFocusTraversable(false);
        deleteButton.getStyleClass().addAll("delete-button", "blue-button");
        deleteButton.setGraphic(new FontIcon(IkonUtil.delete));
        deleteButton.managedProperty().bind(deleteButton.visibleProperty());
        deleteButton.visibleProperty().bind(booleanBinding);
        deleteButton.setOnAction(event -> {
            Comment comment = getComment();
            if (comment != null && !comment.isDeleted()) {
                comment.setDeleted(true);
                commentLabel.setText(DELETED_PLACEHOLDER);
                LOGGER.info("Delete comment: {}", comment.getId());
            }
            event.consume();
        });

        Button editButton = new Button("EDIT");
        editButton.setFocusTraversable(false);
        editButton.getStyleClass().addAll("edit-button");
        editButton.setGraphic(new FontIcon(JFXCentralIcon.EDIT));
        editButton.managedProperty().bind(editButton.visibleProperty());
        editButton.visibleProperty().bind(booleanBinding);
        editButton.setOnAction(event -> {
            Comment comment = getComment();
            if (comment != null && !comment.isDeleted()) {
                LOGGER.info("Edit comment: {}", comment.getId());
            }
            event.consume();
        });

        AvatarView avatar = new AvatarView();
        avatar.imageProperty().bind(Bindings.createObjectBinding(() -> {
            Comment comment = getComment();
            if (comment != null) {
                return comment.getUser().avatar();
            }
            return null;
        }, commentProperty()));

        BorderPane.setMargin(avatar, new Insets(0, 20, 0, 0));
        BorderPane.setAlignment(avatar, Pos.TOP_CENTER);

        Label nameLabel = new Label();
        nameLabel.getStyleClass().add("name");
        nameLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Comment comment = getComment();
            if (comment != null) {
                return comment.getUser().name();
            }
            return null;
        }, commentProperty()));

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("date");
        dateLabel.setMinWidth(Region.USE_PREF_SIZE);
        dateLabel.textProperty().bind(Bindings.createStringBinding(() -> getComment() == null ? null : getTimeDifference(comment.get().getDate(), ZonedDateTime.now()), commentProperty()));
        dateLabel.managedProperty().bind(dateLabel.visibleProperty());

        dateLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> !isSmall() || !booleanBinding.get(), booleanBinding, sizeProperty()));

        Spacer topSpacer = new Spacer();

        badgeBox = new HBox();
        badgeBox.getStyleClass().add("badge-box");
        badgeBox.managedProperty().bind(badgeBox.visibleProperty());
        badgeBox.visibleProperty().bind(Bindings.createBooleanBinding(() -> !isSmall() || !deleteButton.isVisible(), deleteButton.visibleProperty(), sizeProperty()));

        HBox topBox = new HBox(nameLabel, badgeBox, topSpacer, dateLabel, deleteButton, editButton);

        topBox.getStyleClass().add("top-box");

        HBox bottomBox = new HBox();
        bottomBox.getStyleClass().add("bottom-box");

        SaveAndLikeButton likeButton = new SaveAndLikeButton();
        likeButton.getStyleClass().add("like-button");
        likeButton.setSaveButtonVisible(false);
        likeButton.setLikeButtonText(null);

        Label likeCountLabel = new Label();
        likeCountLabel.getStyleClass().add("like-count-label");
        likeCountLabel.setGraphic(new FontIcon(JFXCentralIcon.THUMB_UP));
        likeCountLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Comment comment = getComment();
            if (comment != null) {
                return "Liked by " + comment.getLikes();
            }
            return null;
        }, commentProperty()));

        bottomBox.getChildren().setAll(likeButton, new Spacer(), likeCountLabel);

        VBox centerBox = new VBox(topBox, commentLabel, bottomBox);
        centerBox.getStyleClass().add("center-box");

        contentPane.setLeft(avatar);
        contentPane.setCenter(centerBox);

        managedProperty().bind(visibleProperty());
        visibleProperty().bind(commentProperty().isNotNull());
        commentProperty().addListener((ob, ov, nv) -> layoutBySize());

        getChildren().setAll(contentPane);

        commentProperty().addListener((ob, ov, nv) -> {
            badgeBox.getChildren().clear();
            if (nv != null) {
                commentLabel.setText(nv.isDeleted() ? DELETED_PLACEHOLDER : nv.getContent());
                if (nv.getUser().badges() != null && !nv.getUser().badges().isEmpty()) {
                    List<Badge> badges = nv.getUser().badges();
                    for (Badge badge : badges) {
                        Label label = new Label(badge.name(), new FontIcon(badge.icon()));
                        label.setTooltip(new Tooltip(badge.name()));
                        label.getStyleClass().add("badge");
                        badgeBox.getChildren().add(label);
                    }
                }
            }
        });

    }

    private String getTimeDifference(ZonedDateTime start, ZonedDateTime end) {
        boolean small = isSmall();
        Duration duration = Duration.between(start, end);
        if (duration.toMinutes() < 1) {
            return "Just now";
        } else if (duration.toMinutes() < 2) {
            return small ? "1min ago" : "1 minute ago";
        } else if (duration.toMinutes() < 60) {
            return small ? duration.toMinutes() + "min ago" : duration.toMinutes() + " minutes ago";
        } else if (duration.toHours() < 2) {
            return small ? "1h ago" : "1 hour ago";
        } else if (duration.toHours() < 24) {
            return small ? duration.toHours() + "h ago" : duration.toHours() + " hours ago";
        } else if (duration.toDays() < 2) {
            return small ? "1d ago" : "1 day ago";
        } else if (duration.toDays() < 30) {
            return small ? duration.toDays() + "d ago" : duration.toDays() + " days ago";
        } else if (duration.toDays() < 60) {
            return small ? "1mo ago" : "1 month ago";
        } else if (duration.toDays() < 365) {
            return small ? duration.toDays() / 30 + "mo ago" : duration.toDays() / 30 + " months ago";
        } else if (duration.toDays() < 730) {
            return small ? "1yr ago" : "1 year ago";
        } else {
            return small ? duration.toDays() / 365 + "yr ago" : duration.toDays() / 365 + " years ago";
        }
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
