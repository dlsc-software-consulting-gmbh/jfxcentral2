package com.dlsc.jfxcentral2.components.userprofile;

import com.dlsc.jfxcentral2.components.CustomTabPane;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.model.Comment;
import com.dlsc.jfxcentral2.model.CustomTab;
import com.dlsc.jfxcentral2.model.RegisteredUser;
import com.dlsc.jfxcentral2.model.SaveAndLikeModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserProfileView extends PaneBase {
    public UserProfileView() {
        getStyleClass().add("user-profile-view");

        CustomTabPane tabPane = new CustomTabPane();
        tabPane.sizeProperty().bind(sizeProperty());

        ProfileContentPane profileContentPane = new ProfileContentPane();
        profileContentPane.sizeProperty().bind(sizeProperty());
        profileContentPane.userProperty().bind(userProperty());

        SaveAndLikePane saveAndLikePane = new SaveAndLikePane();
        saveAndLikePane.sizeProperty().bind(sizeProperty());
        saveAndLikePane.itemsProperty().bind(saveAndLikeModelsProperty());

        MyCommentsPane commentsPane = new MyCommentsPane();
        commentsPane.sizeProperty().bind(sizeProperty());
        commentsPane.commentsProperty().bind(commentsProperty());

        tabPane.getTabs().addAll(
                new CustomTab("PROFILE", profileContentPane),
                new CustomTab("SAVE AND LIKED ITEMS", saveAndLikePane),
                new CustomTab("COMMENTS", commentsPane)
        );

        getChildren().setAll(tabPane);
    }

    private final ObjectProperty<RegisteredUser> user = new SimpleObjectProperty<>(this, "user");

    public RegisteredUser getUser() {
        return user.get();
    }

    public ObjectProperty<RegisteredUser> userProperty() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user.set(user);
    }

    private final ListProperty<SaveAndLikeModel> saveAndLikeModels = new SimpleListProperty<>(this, "saveAndLikeModels", FXCollections.observableArrayList());

    public ObservableList<SaveAndLikeModel> getSaveAndLikeModels() {
        return saveAndLikeModels.get();
    }

    public ListProperty<SaveAndLikeModel> saveAndLikeModelsProperty() {
        return saveAndLikeModels;
    }

    public void setSaveAndLikeModels(ObservableList<SaveAndLikeModel> saveAndLikeModels) {
        this.saveAndLikeModels.set(saveAndLikeModels);
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
}
