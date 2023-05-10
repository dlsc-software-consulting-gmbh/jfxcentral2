package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral2.model.Company;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class CompanyTileView extends TileView<Company> {

    public CompanyTileView(Company company) {
        this();
        setData(company);
    }

    public CompanyTileView() {
        getStyleClass().add("company-tile-view");

        setButton1Text("HOMEPAGE");
        setButton1Graphic(new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        setButton1Action(() -> System.out.println("HomePage: " + (getData() != null ? getData().getUrl() : "..")));
    }
}
