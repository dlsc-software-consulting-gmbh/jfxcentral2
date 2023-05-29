package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral2.components.IconPreviewPane;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.model.ikon.IkonPackModel;
import com.dlsc.jfxcentral2.utils.IkonModelUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;
import org.kordamp.ikonli.javafx.FontIcon;

public class IkonPackModelTileView extends TileView<IkonPackModel> {
    public IkonPackModelTileView(IkonPackModel item) {
        super(item);
        getStyleClass().addAll("icon-tile-view");
        setDescription(item.getDescription());

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        getButton1().setOnAction(event->{
            IkonGridView ikonGridView = new IkonGridView();
            ikonGridView.getItems().addAll(IkonModelUtil.getInstance().getIkonList(item.getName()));
            //Todo show ikonGridView (New Page)

        });

        setButton2Text("GitHub");
        setButton2Graphic(new FontIcon(IkonUtil.github));

    }

    @Override
    protected Node createFrontTop() {
        IconPreviewPane previewPane = new IconPreviewPane();
        previewPane.sizeProperty().bind(sizeProperty());
        previewPane.setIconModel(getData());
        return previewPane;
    }

}
