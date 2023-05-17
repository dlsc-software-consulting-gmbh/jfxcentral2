package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;

public class BlogTileView extends SimpleTileView<Blog> {

    public BlogTileView(Blog blog) {
        this();
        setData(blog);
    }

    public BlogTileView() {
        getStyleClass().add("blog-tile-view");

        dataProperty().addListener((ob, ov, data) -> {
            saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(data));
            saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(data));

            //add image for testing
            imageProperty().bind(Bindings.createObjectBinding(() -> new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/person-avatar.png").toExternalForm())));

            //imageProperty().bind(ImageManager.getInstance().blogIconImageProperty(data));
            titleProperty().set(data.getName());
            descriptionProperty().set(data.getDescription());
            updateLinkedObjectBadges();
        });
    }

}
