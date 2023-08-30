package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;

public class DocumentationTileView extends SimpleTileView<Documentation> {

    public DocumentationTileView(Documentation doc) {
        super(doc);

        getStyleClass().addAll("tool-tile-view", "doc-tile-view");
        if ("cssref".equalsIgnoreCase(doc.getId())) {
            getStyleClass().add("cssref-tile-view");
        }

        setAvatarType(AvatarView.Type.PLAIN);

        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(doc));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(doc));

        imageProperty().bind(ImageManager.getInstance().documentationImageProperty(doc));

        titleProperty().set(doc.getName());
        setDescription(doc.getDescription());
    }
}
