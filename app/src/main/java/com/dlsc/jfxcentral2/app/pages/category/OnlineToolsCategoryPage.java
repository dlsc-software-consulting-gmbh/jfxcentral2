package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.OnlineTool;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.OnlineToolsFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.OnlineToolTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class OnlineToolsCategoryPage extends CategoryPageBase<OnlineTool> {

    public OnlineToolsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Online Develop Tools";
    }

    @Override
    public String description() {
        return "Online tools for developing JavaFX applications.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Online Tools";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(OnlineTool.class);
    }

    @Override
    protected int getNumberOfGridViewRows() {
        return 5;
    }

    @Override
    protected Callback<OnlineTool, TileViewBase<OnlineTool>> getTileViewProvider() {
        return OnlineToolTileView::new;
    }

    @Override
    protected SearchFilterView<OnlineTool> createSearchFilterView() {
        return new OnlineToolsFilterView();
    }

    @Override
    protected ObservableList<OnlineTool> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getOnlineTools());
    }

}
