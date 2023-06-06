package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.filters.ToolsFilterView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.ToolTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class ToolsCategoryPage extends CategoryPageBase<Tool> {

    public ToolsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Tools";
    }

    @Override
    public String description() {
        return "A curated list of tools for developing JavaFX applications.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Tools";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Tool.class);
    }

    @Override
    protected int getNumberOfGridViewRows() {
        return 5;
    }

    @Override
    protected Callback<Tool, TileViewBase<Tool>> getTileViewProvider() {
        return ToolTileView::new;
    }

    @Override
    protected SearchFilterView createSearchFilterView() {
        return new ToolsFilterView();
    }

    @Override
    protected ObservableList<Tool> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getTools());
    }
}
