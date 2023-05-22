package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import one.jpro.routing.LinkUtil;

public class LibraryTileView extends SimpleTileView<Library> {

    public LibraryTileView() {
        getStyleClass().add("library-tile-view");

        setAvatarType(AvatarView.Type.PLAIN);

        setPrefWidth(1);

        dataProperty().addListener((ob, ov, library) -> {
            LinkUtil.setLink(this, "/libraries/" + getData().getId());

            saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(library));
            saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(library));

            setTitle(library.getName());
            setDescription(library.getSummary());
        });
    }
}
