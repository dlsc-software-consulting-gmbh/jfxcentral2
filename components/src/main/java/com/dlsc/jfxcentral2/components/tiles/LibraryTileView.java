package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;

public class LibraryTileView extends SimpleTileView<Library> {

    public LibraryTileView(Library library) {
        super(library);

        getStyleClass().add("library-tile-view");

        setAvatarType(AvatarView.Type.PLAIN);

        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(library));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(library));

        setTitle(library.getName());
        setDescription(library.getDescription());

        imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library));
    }
}
