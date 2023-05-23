package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.CompaniesFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.CompanyTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class CompaniesCategoryPage extends CategoryPageBase<Company> {

    public CompaniesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Companies";
    }

    @Override
    public String description() {
        return "A curated list of companies connected to JavaFX. They develop libraries, applications, tools.";
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
    protected SearchFilterView createSearchFilterView() {
        return new CompaniesFilterView();
    }

    @Override
    protected ObservableList<Company> getCategoryItems() {
        return DataRepository.getInstance().getCompanies();
    }
}
