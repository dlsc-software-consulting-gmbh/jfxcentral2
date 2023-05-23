package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import one.jpro.routing.LinkUtil;

public class BlogTileView extends SimpleTileView<Blog> {

    public BlogTileView(Blog blog) {
        super(blog);

        getStyleClass().add("blog-tile-view");

        LinkUtil.setLink(this, "/blogs/" + getData().getId());

        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(blog));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(blog));

        //add image for testing
        imageProperty().bind(ImageManager.getInstance().blogIconImageProperty(getData()));

        setTitle(blog.getName());
        setDescription(blog.getSummary());
        setAvatarType(AvatarView.Type.PLAIN);
        updateLinkedObjectBadges();
    }
}
