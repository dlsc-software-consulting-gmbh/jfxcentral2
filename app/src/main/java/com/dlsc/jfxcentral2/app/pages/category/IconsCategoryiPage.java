package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.IkonliPacksFilter;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.IkonliPackTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class IconsCategoryiPage extends CategoryPageBase<IkonliPack> {


    public IconsCategoryiPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Icons";
    }

    @Override
    public String description() {
        return "A browser for all ikonli icon fonts that supports searching based on icon name.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Icons";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(IkonliPack.class);
    }

    @Override
    protected ObservableList<IkonliPack> getCategoryItems() {
        return DataRepository.getInstance().getIkonliPacks();
    }

    @Override
    protected Callback<IkonliPack, TileViewBase<IkonliPack>> getTileViewProvider() {
        return IkonliPackTileView::new;
    }

    @Override
    protected SearchFilterView createSearchFilterView() {
        return new IkonliPacksFilter();
    }
}
