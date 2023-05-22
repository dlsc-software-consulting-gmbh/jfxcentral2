package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import one.jpro.routing.LinkUtil;

public class LibraryTileView extends SimpleTileView<Library> {

    public LibraryTileView() {
        getStyleClass().add("library-tile-view");

        dataProperty().addListener((ob, ov, tool) -> {
            LinkUtil.setLink(this, "/libraries/" + getData().getId());

            saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(tool));
            saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(tool));

            titleProperty().set(tool.getName());
            descriptionProperty().set(tool.getDescription());
        });
    }
}
