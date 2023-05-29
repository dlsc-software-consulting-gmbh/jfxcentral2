package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.IkonliPacksFilter;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.IkonPackModelTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.model.ikon.IkonPackModel;
import com.dlsc.jfxcentral2.utils.IkonModelUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class IconsCategoryiPage extends CategoryPageBase<IkonPackModel> {


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

//    @Override
//    public Node content() {
//
//        // category header
//        CategoryHeader header = new CategoryHeader();
//        header.sizeProperty().bind(sizeProperty());
//        header.setTitle("Icons");
//        header.setIkon(IkonUtil.champion);
//
//        // ikonli browser
//        ModelGridView<IkonPackModel> gridView = new ModelGridView();
//        gridView.sizeProperty().bind(sizeProperty());
//
//        // features container
//        FeaturesContainer featuresContainer = new FeaturesContainer();
//        featuresContainer.sizeProperty().bind(sizeProperty());
//
//        // strip view wrapper
//        StripView stripView = new StripView(browser, featuresContainer);
//        stripView.sizeProperty().bind(sizeProperty());
//
//        return wrapContent(header, stripView);
//    }

    @Override
    protected String getCategoryTitle() {
        return "Icons";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(IkonPackModel.class);
    }

    @Override
    protected ObservableList<IkonPackModel> getCategoryItems() {
        return FXCollections.observableArrayList(IkonModelUtil.getInstance().getIkonPackModelList());
    }

    @Override
    protected Callback<IkonPackModel, TileViewBase<IkonPackModel>> getTileViewProvider() {
        return IkonPackModelTileView::new;
    }

    @Override
    protected SearchFilterView createSearchFilterView() {
        return new IkonliPacksFilter();
    }
}
