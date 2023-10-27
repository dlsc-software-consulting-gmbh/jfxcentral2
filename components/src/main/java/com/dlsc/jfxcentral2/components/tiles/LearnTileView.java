package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;

public class LearnTileView extends SimpleTileView<Learn> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LearnTileView(Learn learn) {
        super(learn);
        getStyleClass().addAll("learn-tile-view", "tool-tile-view");
        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(learn));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(learn));
        setTitle(learn.getName());
        setDescription(DATE_FORMATTER.format(learn.getCreatedOn()));
        updateLinkedObjectBadges();

    }

    protected void updateLinkedObjectBadges() {
        HBox developmentStatusBox = getLinkedObjectBox();
        developmentStatusBox.getChildren().clear();
        Learn learn = getData();
        learn.getPersonIds().forEach(id -> DataRepository2.getInstance().getPersonById(id)
                .ifPresent(person -> {
                    AvatarView avatarView = new AvatarView();
                    avatarView.setTooltip(new Tooltip(person.getName()));
                    avatarView.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));
                    developmentStatusBox.getChildren().add(avatarView);
                }));
    }

    //protected void setLinkForItem(Node target, ModelObject item) {
    //    LOGGER.error("set link for item,URL = {}",ModelObjectTool.getModelLink(item));
    //    LinkUtil.setLink(target, ModelObjectTool.getModelLink(item));
    //
    //}

}
