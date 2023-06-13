package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.filters.IkonliIconsFilter;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.components.headers.IconDetailHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import org.kordamp.ikonli.Ikon;

public class IconPackDetailPage extends DetailsPageBase<IkonliPack> {

    public IconPackDetailPage(ObjectProperty<Size> size, String itemId) {
        super(size, IkonliPack.class, itemId);
    }

    @Override
    public Node content() {
        IkonliPack ikonliPack = getItem();

        // header
        IconDetailHeader iconDetailHeader = new IconDetailHeader(ikonliPack);
        iconDetailHeader.sizeProperty().bind(sizeProperty());

        // filter view
        IkonliIconsFilter filter = new IkonliIconsFilter();
        filter.sizeProperty().bind(sizeProperty());

        // data
        FilteredList<Ikon> filteredList = new FilteredList<>(IkonliPackUtil.getInstance().getIkonList(ikonliPack));
        filteredList.predicateProperty().bind(filter.predicateProperty());

        //sort
        SortedList<Ikon> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(filter.comparatorProperty());

        // grid view
        IkonGridView gridView = new IkonGridView();
        gridView.sizeProperty().bind(sizeProperty());
        gridView.setItems(sortedList);

        // strip view
        StripView stripView = new StripView(gridView);
        stripView.getStyleClass().add("light");

        return wrapContent(iconDetailHeader, filter, stripView);
    }
}
