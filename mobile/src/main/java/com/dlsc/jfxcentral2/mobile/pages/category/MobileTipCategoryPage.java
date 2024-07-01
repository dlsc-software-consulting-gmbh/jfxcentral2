package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.TipsAndTricksTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileTipCategoryPage extends MobileCategoryPageBase<Tip> {

    public MobileTipCategoryPage(ObjectProperty<Size> size) {
        super(size);
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
    protected String getSearchPrompText() {
        return "Search for tips and tricks";
    }

    @Override
    protected ObservableList<Tip> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getTips());
    }
}
