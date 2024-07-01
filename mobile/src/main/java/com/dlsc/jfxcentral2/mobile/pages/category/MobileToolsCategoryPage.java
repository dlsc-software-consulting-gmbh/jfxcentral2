package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.ToolTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileToolsCategoryPage extends MobileCategoryPageBase<Tool> {

    public MobileToolsCategoryPage(ObjectProperty<Size> size) {
        super(size);
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
    protected Callback<Tool, TileViewBase<Tool>> getTileViewProvider() {
        return ToolTileView::new;
    }

    @Override
    protected String getSearchPrompText() {
        return "Search for a tool";
    }

    @Override
    protected ObservableList<Tool> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getTools());
    }
}
