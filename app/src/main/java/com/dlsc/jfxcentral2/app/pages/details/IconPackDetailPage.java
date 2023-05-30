package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.components.headers.IconDetailHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.model.filter.IconPack;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

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

        // grid view
        IkonGridView gridView = new IkonGridView();
        gridView.sizeProperty().bind(sizeProperty());
        gridView.getItems().setAll(IkonliPackUtil.getInstance().getIkonList(IconPack.valueOf(ikonliPack.getId().toUpperCase())));

        return wrapContent(iconDetailHeader, gridView);
    }
}
