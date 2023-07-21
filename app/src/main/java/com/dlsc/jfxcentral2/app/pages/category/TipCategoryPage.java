package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.filters.TipsAndTricksFilterView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.TipsAndTricksTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class TipCategoryPage extends CategoryPageBase<Tip> {

    public TipCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Tips";
    }

    @Override
    public String description() {
        return "A collection of posts with interesting tips and tricks for developing JavaFX applications.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Tips & Tricks";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Tip.class);
    }

    @Override
    protected Callback<Tip, TileViewBase<Tip>> getTileViewProvider() {
        return TipsAndTricksTileView::new;
    }

    @Override
    protected SearchFilterView<Tip> createSearchFilterView() {
        return new TipsAndTricksFilterView();
    }

    @Override
    protected ObservableList<Tip> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getTips());
    }
}
