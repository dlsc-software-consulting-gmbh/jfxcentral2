package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.components.tiles.CompanyTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileCompaniesCategoryPage extends MobileCategoryPageBase<Company> {

    public MobileCompaniesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected String getCategoryTitle() {
        return "Companies";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Company.class);
    }

    @Override
    protected Callback<Company, TileViewBase<Company>> getTileViewProvider() {
        return CompanyTileView::new;
    }

    @Override
    protected String getSearchPrompText() {
        return "Search for a company";
    }

    @Override
    protected ObservableList<Company> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getCompanies());
    }
}
