package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;

public class ToolTileView extends SimpleTileView<Tool> {

    public ToolTileView(Tool tool) {
        super(tool);

        getStyleClass().add("tool-tile-view");

        setAvatarType(AvatarView.Type.PLAIN);

        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(tool));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(tool));

        imageProperty().bind(ImageManager.getInstance().toolImageProperty(tool));

        titleProperty().set(tool.getName());
        setDescription(tool.getDescription());
    }
}
