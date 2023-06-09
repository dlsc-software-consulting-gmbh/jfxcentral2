package com.dlsc.jfxcentral2.components.gridview;

import org.kordamp.ikonli.Ikon;

public class IkonGridView extends SimpleGridView<Ikon> {

    public IkonGridView() {
        getStyleClass().add("ikon-grid-view");
        setCellViewProvider(IkonCellView::new);
        setDetailNodeProvider(IkonDetailView::new);
        //columnsProperty().bind(sizeProperty().map(s -> s.isLarge() ? 12 : s.isMedium() ? 8 : 3));
        widthProperty().addListener((ob, ov, nv) -> {
            if (nv.doubleValue() != 0) {
                int columns = (int) (nv.doubleValue() / 120);
                setColumns(columns);
            }
        });
        setRows(6);
    }
}
