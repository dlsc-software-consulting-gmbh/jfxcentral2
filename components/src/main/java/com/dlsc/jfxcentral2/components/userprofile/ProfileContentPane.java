package com.dlsc.jfxcentral2.components.userprofile;

import com.dlsc.gemsfx.PhotoView;
import com.dlsc.jfxcentral2.components.EditTextField;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.RegisteredUser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class ProfileContentPane extends PaneBase {
    private static final Logger LOGGER = LogManager.getLogger(ProfileContentPane.class);
    private static final Image PERSON_AVATAR_IMAGE = new Image(Objects.requireNonNull(ProfileContentPane.class.getResource("/com/dlsc/jfxcentral2/demoimages/person-avatar.png")).toExternalForm());
    private PhotoView photoView;
    private EditTextField userNameField;
    private EditTextField fullNameField;
    private EditTextField emailField;

    public ProfileContentPane() {
        getStyleClass().add("profile-content-pane");
        initComponents();

        userProperty().addListener(it -> fillData());
        updateUI();
    }

    private void initComponents() {
        photoView = new PhotoView();

        userNameField = new EditTextField();
        userNameField.getStyleClass().add("user-name-field");
        userNameField.setTitle("User name");
        userNameField.setEditButtonText("");
        userNameField.setSaveButtonText(isLarge() ? "SAVE CHANGES" : "SAVE");
        userNameField.setPromptText("Enter User name here");
        userNameField.setFailedMessage("Username too short. Min 3 chars");
        userNameField.setValidator(s -> s != null && s.length() >= 3);
        userNameField.setOnSave(s -> {
            RegisteredUser user = getUser();
            if (user != null) {
                user.setUserName(s);
                LOGGER.info("saved user name: {}" , s);
            }
        });

        fullNameField = new EditTextField();
        fullNameField.getStyleClass().add("full-name-field");
        fullNameField.setTitle("Full name");
        fullNameField.setEditButtonText("");
        fullNameField.setSaveButtonText(isLarge() ? "SAVE CHANGES" : "SAVE");
        fullNameField.setPromptText("Enter Full name here");
        fullNameField.setFailedMessage("Full name too short.");
        fullNameField.setValidator(s -> s != null && s.length() >= 3);
        fullNameField.setOnSave(s -> {
            RegisteredUser user = getUser();
            if (user != null) {
                user.setFullName(s);
                LOGGER.info("saved full name: {}" , s);
            }
        });

        emailField = new EditTextField();
        emailField.getStyleClass().add("email-field");
        emailField.setTitle("E-mail");
        emailField.setEditButtonText("");
        emailField.setSaveButtonText(isLarge() ? "SAVE CHANGES" : "SAVE");
        emailField.setPromptText("Enter Email here");
        emailField.setFailedMessage("Invalid email address.");
        emailField.setValidator(s -> EmailValidator.getInstance().isValid(s));
        emailField.setOnSave(s -> {
            RegisteredUser user = getUser();
            if (user != null) {
                user.setEmail(s);
                LOGGER.info("saved email: {}" , s);
            }
        });
    }

    @Override
    protected void layoutBySize() {
        if (isSmToMdOrMdToSm()) {
            return;
        }
        updateUI();
    }

    private void updateUI() {
        Button uploadButton = new Button("UPLOAD", new FontIcon(JFXCentralIcon.CAMERA));
        uploadButton.setFocusTraversable(false);
        uploadButton.getStyleClass().addAll("upload-button", "fill-button");
        uploadButton.setOnAction(it -> photoView.setPhoto(photoView.getPhotoSupplier().get()));

        Button removeButton = new Button("REMOVE", new FontIcon(JFXCentralIcon.CAMERA));
        removeButton.setFocusTraversable(false);
        removeButton.getStyleClass().addAll("remove-button", "fill-button");
        removeButton.setOnAction(it -> photoView.setPhoto(null));
        removeButton.setFocusTraversable(false);

        Label tipLabel = new Label("Do you want to delete your account?");
        tipLabel.getStyleClass().add("tip-label");
        Label clickHereLabel = new Label(" Click here.");
        clickHereLabel.getStyleClass().add("click-here-label");
        clickHereLabel.setOnMousePressed(event -> {
            RegisteredUser user = getUser();
            if (user != null) {
                LOGGER.info("delete account: {}" , user.getUserName());
            }
        });

        HBox bottomBox = new HBox(tipLabel, clickHereLabel);
        bottomBox.getStyleClass().add("bottom-box");

        if (isLarge()) {
            VBox buttonsBox = new VBox(uploadButton, removeButton);
            buttonsBox.getStyleClass().add("buttons-box");

            VBox photoBox = new VBox(photoView, buttonsBox);
            photoBox.getStyleClass().add("photo-box");

            VBox centerBox = new VBox(userNameField, fullNameField, emailField, new Spacer(Orientation.VERTICAL), bottomBox);
            centerBox.getStyleClass().add("center-box");
            HBox.setHgrow(centerBox, Priority.ALWAYS);

            HBox contentBox = new HBox(photoBox, centerBox);
            contentBox.getStyleClass().add("content-box");

            getChildren().setAll(contentBox);
        } else {
            HBox buttonsBox = new HBox(uploadButton, removeButton);
            buttonsBox.getStyleClass().add("buttons-box");

            VBox photoBox = new VBox(photoView, buttonsBox);
            photoBox.getStyleClass().add("photo-box");

            VBox contentBox = new VBox(photoBox, userNameField, fullNameField, emailField, new Spacer(Orientation.VERTICAL), bottomBox);
            contentBox.getStyleClass().add("content-box");

            getChildren().setAll(contentBox);
        }
        fillData();
    }

    private void fillData() {
        RegisteredUser user = getUser();
        if (user == null) {
            photoView.setPhoto(null);
            userNameField.setText("");
            fullNameField.setText("");
            emailField.setText("");
        } else {
            // TODO: photoView.setPhoto(ImageManager.getInstance().xxx);
            photoView.setPhoto(PERSON_AVATAR_IMAGE);
            userNameField.setText(user.getUserName());
            fullNameField.setText(user.getFullName());
            emailField.setText(user.getEmail());
        }
    }

    private final ObjectProperty<RegisteredUser> user = new SimpleObjectProperty<>(this, "user", new RegisteredUser());

    public RegisteredUser getUser() {
        return user.get();
    }

    public ObjectProperty<RegisteredUser> userProperty() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user.set(user);
    }
}
