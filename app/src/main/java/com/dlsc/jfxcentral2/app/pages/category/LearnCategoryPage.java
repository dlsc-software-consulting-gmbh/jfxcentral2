package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.LearnFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.LearnTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public abstract class LearnCategoryPage extends CategoryPageBase<Learn> {

    public LearnCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Learn.class);
    }

    @Override
    protected Callback<Learn, TileViewBase<Learn>> getTileViewProvider() {
        return LearnTileView::new;
    }

    @Override
    protected SearchFilterView<Learn> createSearchFilterView() {
        return new LearnFilterView();
    }

}
