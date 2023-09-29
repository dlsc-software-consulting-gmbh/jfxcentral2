package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.DevelopmentStatus;
import com.dlsc.jfxcentral.data.model.OnlineTool;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.apache.commons.lang3.StringUtils;

public class OnlineToolTileView extends SimpleTileView<OnlineTool> {

    public OnlineToolTileView(OnlineTool onlineTool) {
        super(onlineTool);
        DevelopmentStatus status = onlineTool.getStatus();
        getStyleClass().addAll("online-tool-tile-view", status.toString().toLowerCase(), "tool-tile-view");

        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(onlineTool));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(onlineTool));
        setAvatarType(AvatarView.Type.PLAIN);
        imageProperty().bind(ImageManager.getInstance().onlineToolImageProperty(onlineTool));
        titleProperty().set(onlineTool.getName());
        setDescription(onlineTool.getDescription());

        setDisable(!ModelObjectTool.isOnlineToolCanBeUsed(onlineTool));

        updateLinkedObjectBadges();
    }

    protected void updateLinkedObjectBadges() {
        HBox developmentStatusBox = getLinkedObjectBox();
        developmentStatusBox.getChildren().clear();

        String strStatus = getData().getStatus().toString().toLowerCase();
        Label label = new Label(StringUtils.capitalize(strStatus));
        label.getStyleClass().addAll("linked-badge");
        developmentStatusBox.getChildren().setAll(label);
    }

}
