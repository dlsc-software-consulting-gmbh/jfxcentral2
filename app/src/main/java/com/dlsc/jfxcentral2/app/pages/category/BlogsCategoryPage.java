package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.BlogsFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.BlogTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class BlogsCategoryPage extends CategoryPageBase<Blog> {

    public BlogsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Blogs";
    }

    @Override
    public String description() {
        return "A curated list of blogs covering JavaFX topics.";
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
    protected int getNumberOfGridViewRows() {
        return 5;
    }

    @Override
    protected SearchFilterView createSearchFilterView() {
        return new BlogsFilterView();
    }

    @Override
    protected ObservableList<Blog> getCategoryItems() {
        return DataRepository.getInstance().getBlogs();
    }
}
