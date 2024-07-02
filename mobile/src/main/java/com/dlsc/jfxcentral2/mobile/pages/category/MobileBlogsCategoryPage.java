package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.components.tiles.BlogTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileBlogsCategoryPage extends MobileCategoryPageBase<Blog> {

    public MobileBlogsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected String getCategoryTitle() {
        return "Blogs";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Blog.class);
    }

    @Override
    protected Callback<Blog, TileViewBase<Blog>> getTileViewProvider() {
        return BlogTileView::new;
    }

    @Override
    protected String getSearchPrompText() {
        return "Search for a blog";
    }

    @Override
    protected ObservableList<Blog> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getBlogs());
    }
}
