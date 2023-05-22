package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import one.jpro.routing.LinkUtil;

public class BlogTileView extends SimpleTileView<Blog> {

    public BlogTileView() {
        getStyleClass().add("blog-tile-view");

        dataProperty().addListener((ob, ov, data) -> {
            LinkUtil.setLink(this, "/blogs/" + getData().getId());

            saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(data));
            saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(data));

            //add image for testing
            imageProperty().bind(ImageManager.getInstance().blogIconImageProperty(getData()));

            setTitle(data.getName());
            setDescription(data.getSummary());
            setAvatarType(AvatarView.Type.PLAIN);
            updateLinkedObjectBadges();
        });
    }

}
