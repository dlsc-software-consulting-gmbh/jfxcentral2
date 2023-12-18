package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.PacksIconsView;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class IconsCategoryPage extends CategoryPageBase<IkonliPack> {

    public IconsCategoryPage(ObjectProperty<Size> size) {
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
        return null;
    }

    @Override
    protected Callback<IkonliPack, TileViewBase<IkonliPack>> getTileViewProvider() {
        return null;
    }

    @Override
    protected SearchFilterView<IkonliPack> createSearchFilterView() {
        return null;
    }

    @Override
    public Node content() {
        // header
        CategoryHeader header = new CategoryHeader();
        header.setTitle("Icons");
        header.setIkon(IkonUtil.icons);
        header.setDescription("Explore thousands of icons from the Ikonli project, searchable by icon name or library.");
        header.sizeProperty().bind(sizeProperty());

        PacksIconsView packsIconsView = new PacksIconsView();
        packsIconsView.sizeProperty().bind(sizeProperty());

        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        StripView wrapperBox = new StripView(packsIconsView, featuresContainer);
        wrapperBox.getStyleClass().add("packs-icons-wrapper-box");

        return wrapContent(header, wrapperBox);
    }
}
