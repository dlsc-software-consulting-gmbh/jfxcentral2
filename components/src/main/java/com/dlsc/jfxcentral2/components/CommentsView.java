package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Badge;
import com.dlsc.jfxcentral2.model.Comment;
import com.dlsc.jfxcentral2.model.NameProvider;
import com.dlsc.jfxcentral2.model.User;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class CommentsView extends PaneBase implements NameProvider {
    private static final Logger LOGGER = LogManager.getLogger(CommentsView.class);
    private final VBox commentsBox;

    public CommentsView() {
        getStyleClass().add("comments-view");
        setMaxHeight(Region.USE_PREF_SIZE);

        commentsBox = new VBox();
        commentsBox.getStyleClass().add("comments-box");

        //commentsProperty().addListener((observable, oldValue, newValue) -> {
        //    commentsBox.getChildren().clear();
        //    if (newValue != null) {
        //        for (Comment comment : newValue) {
        //            CommentItemView commentItemView = new CommentItemView();
        //            commentItemView.setComment(comment);
        //            commentItemView.sizeProperty().bind(sizeProperty());
        //            commentsBox.getChildren().add(commentItemView);
        //        }
        //    }
        //});

        commentsProperty().addListener((ListChangeListener<Comment>) c -> {
            while (c.next()) {
                //add
                if (c.wasAdded()) {
                    for (Comment comment : c.getAddedSubList()) {
                        CommentItemView commentItemView = new CommentItemView();
                        commentItemView.setComment(comment);
                        commentItemView.sizeProperty().bind(sizeProperty());
                        commentsBox.getChildren().add(commentItemView);
                    }
                }
            }
        });

        updateUI();
    }

    @Override
    protected void layoutBySize() {
        if (!isLgToMdOrMdToLg()) {
            updateUI();
        }
    }

    private void updateUI() {
        getChildren().clear();

        Header header = new Header();
        header.setIcon(JFXCentralIcon.COMMENTS);
        header.titleProperty().bind(titleProperty());
        header.getStyleClass().add("comments-header");

        Region writeCommentPane = createWriteCommentPane();
        writeCommentPane.getStyleClass().add("write-comment-pane");

        Region bottomPane = createBottomPane();
        bottomPane.getStyleClass().add("bottom");

        VBox bodyBox = new VBox(writeCommentPane, commentsBox, bottomPane);
        bodyBox.getStyleClass().add("body-box");

        VBox contentBox = new VBox(header, bodyBox);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    private Region createBottomPane() {
        StackPane pane = new StackPane();

        Button loadMoreButton = new Button("LOAD MORE");
        loadMoreButton.setFocusTraversable(false);
        loadMoreButton.getStyleClass().addAll("load-more-button", "blue-button");
        loadMoreButton.managedProperty().bind(loadMoreButton.visibleProperty());
        loadMoreButton.visibleProperty().bind(comments.emptyProperty().not());
        loadMoreButton.setOnAction(evt -> {
            User writedUser = new User(
                    "0", "Dirk Lemmermann", new Image(Objects.requireNonNull(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/person-avatar.png")).toExternalForm()),
                    List.of(new Badge("Champion", IkonUtil.champion),
                            new Badge("Rockstar", IkonUtil.rockstar)));
            comments.addAll(
                    new Comment("001abc", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", writedUser, ZonedDateTime.now().minusMinutes(5), 125, false, false),
                    new Comment("001abc", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", writedUser, ZonedDateTime.now().minusHours(3), 125, false, false),
                    new Comment("001abc", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", writedUser, ZonedDateTime.now().minusDays(7), 125, false, false));
            LOGGER.info("load more comments");

        });

        Label label = new Label("No comments yet");
        label.getStyleClass().add("no-comments-label");
        label.managedProperty().bind(label.visibleProperty());
        label.visibleProperty().bind(comments.emptyProperty());
        pane.getChildren().addAll(loadMoreButton, label);

        return pane;
    }

    private Region createWriteCommentPane() {
        BorderPane pane = new BorderPane();
        AvatarView avatarView = new AvatarView();
        avatarView.imageProperty().bind(Bindings.createObjectBinding(() -> {
            if (user.get() != null) {
                return user.get().avatar();
            } else {
                return null;
            }
        }, userProperty()));
        BorderPane.setMargin(avatarView, new Insets(0, 20, 0, 0));
        BorderPane.setAlignment(avatarView, Pos.TOP_CENTER);

        pane.setLeft(avatarView);

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setPromptText("Add a comment");
        textArea.maxHeightProperty().bind(textArea.prefHeightProperty());
        textArea.prefHeightProperty().bind(Bindings.when(textArea.textProperty().isEmpty()).then(Region.USE_PREF_SIZE).otherwise(67D));

        HBox.setHgrow(textArea, Priority.ALWAYS);

        Button submitButton = new Button("SUBMIT");
        submitButton.setFocusTraversable(false);
        submitButton.getStyleClass().addAll("submit-button", "blue-button");
        submitButton.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        submitButton.managedProperty().bind(submitButton.visibleProperty());
        submitButton.visibleProperty().bind(textArea.textProperty().isNotEmpty());

        Pane writeBox = isSmall() ? new VBox() : new HBox();
        writeBox.getStyleClass().add("write-box");
        writeBox.setMinHeight(Region.USE_PREF_SIZE);
        writeBox.getChildren().addAll(textArea, submitButton);

        Label submittedNotifylabel = new Label("Comment successfully submitted!");
        submittedNotifylabel.getStyleClass().add("notify-label");

        Button closeButton = new Button();
        closeButton.setFocusTraversable(false);
        closeButton.getStyleClass().add("close-button");
        closeButton.setGraphic(new FontIcon(IkonUtil.close));
        closeButton.setOnAction(e -> pane.setCenter(writeBox));

        HBox notifyBox = new HBox(submittedNotifylabel, new Spacer(), closeButton);
        notifyBox.getStyleClass().add("notify-box");

        pane.setCenter(writeBox);

        submitButton.setOnAction(e -> {
            User writedUser = new User(
                    "0", "Dirk Lemmermann", new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/person-avatar.png").toExternalForm()),
                    List.of(new Badge("Champion", IkonUtil.champion),
                            new Badge("Rockstar", IkonUtil.rockstar))
            );
            //latest comments first
            getComments().add(0, new Comment("001abc", textArea.getText(), writedUser, ZonedDateTime.now(), 125, false, false));
            textArea.clear();
            pane.setCenter(notifyBox);
        });

        return pane;
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

    private final ObjectProperty<User> user = new SimpleObjectProperty<>(this, "user");

    public User getUser() {
        return user.get();
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", "Comments");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public String getName() {
        return "Comments";
    }
}
