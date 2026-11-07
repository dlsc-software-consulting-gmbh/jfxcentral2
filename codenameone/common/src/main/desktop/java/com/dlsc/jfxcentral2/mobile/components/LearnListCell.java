package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.components.AvatarView;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class LearnListCell extends ListCell<Learn> {

    private static final String DEFAULT_STYLE_CLASS = "learn-list-cell";
    private final HBox authorBox;
    private final Label titleLabel;
    private final HBox container;

    public LearnListCell() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setPrefWidth(0);

        titleLabel = new Label();
        titleLabel.setWrapText(true);
        titleLabel.getStyleClass().add("title");

        authorBox = new HBox();
        authorBox.getStyleClass().add("author-box");

        // The title takes the room the avatars leave and wraps in it; without
        // a grow it was handed its unwrapped width and spilled over the row.
        titleLabel.setMinWidth(0);
        HBox.setHgrow(titleLabel, javafx.scene.layout.Priority.ALWAYS);
        authorBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        authorBox.setSpacing(4);

        container = new HBox(titleLabel, new Spacer(), authorBox);
        container.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        container.getStyleClass().add("container");
    }

    @Override
    protected void updateItem(Learn item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        titleLabel.setText(null);
        authorBox.getChildren().clear();

        if (empty || item == null) {
            setGraphic(null);
        } else {
            titleLabel.setText(item.getName());
            item.getPersonIds().forEach(id -> DataRepository.getInstance().getPersonById(id)
                    .ifPresent(person -> {
                        AvatarView avatarView = new AvatarView();
                        avatarView.setMouseTransparent(true);
                        // The stylesheet's -fx-avatar-size is a custom CSS
                        // property the Codename One layer does not read.
                        avatarView.setAvatarSize(30);
                        avatarView.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));
                        authorBox.getChildren().add(avatarView);
                    }));

            setGraphic(container);
        }
    }
}
