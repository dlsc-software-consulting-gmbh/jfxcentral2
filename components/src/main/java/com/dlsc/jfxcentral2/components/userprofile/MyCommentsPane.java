package com.dlsc.jfxcentral2.components.userprofile;

import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.model.Comment;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.List;

public class MyCommentsPane extends PaneBase {
    public MyCommentsPane() {
        getStyleClass().add("my-comments-pane");

        maxCommentsProperty().bind(sizeProperty().map(it -> it.isLarge() ? 5 : 4));

        PaginationControl2 pagination = new PaginationControl2();
        pagination.pageCountProperty().bind(Bindings.createIntegerBinding(() -> {
            int size = getComments().size();
            int maxComments = getMaxComments();
            if (size == 0) {
                return 1;
            }
            return (size - 1) / maxComments + 1;
        }, commentsProperty(), maxCommentsProperty()));

        pagination.pageFactoryProperty().bind(Bindings.createObjectBinding(() -> pageIndex -> {
            List<Comment> comments = getComments().subList(pageIndex * getMaxComments(), Math.min((pageIndex + 1) * getMaxComments(), getComments().size()));
            VBox commentList = new VBox();
            commentList.getStyleClass().add("comment-list");
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);
                MyCommentCell cell = new MyCommentCell();
                cell.sizeProperty().bind(sizeProperty());
                if (i == 0) {
                    cell.getStyleClass().add("first");
                }
                if (i == comments.size() - 1) {
                    cell.getStyleClass().add("last");
                }
                cell.setComment(comment);
                commentList.getChildren().add(cell);

            }
            return commentList;
        }, commentsProperty(), maxCommentsProperty()));

        getChildren().setAll(pagination);

    }

    private final ListProperty<Comment> comments = new SimpleListProperty<>(this, "comments", FXCollections.observableArrayList());

    public ObservableList<Comment> getComments() {
        return comments.get();
    }

    public ListProperty<Comment> commentsProperty() {
        return comments;
    }

    public void setComments(ObservableList<Comment> comments) {
        this.comments.set(comments);
    }

    private final IntegerProperty maxComments = new SimpleIntegerProperty(this, "maxComments", 10);

    public int getMaxComments() {
        return maxComments.get();
    }

    public IntegerProperty maxCommentsProperty() {
        return maxComments;
    }

    public void setMaxComments(int maxComments) {
        this.maxComments.set(maxComments);
    }
}
