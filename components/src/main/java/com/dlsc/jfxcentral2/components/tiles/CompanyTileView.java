package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class CompanyTileView extends TileView<Company> {

    public CompanyTileView(Company company) {
        this();
        setData(company);
    }

    public CompanyTileView() {
        getStyleClass().add("company-tile-view");

        setButton1Text("HOMEPAGE");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        setButton1Action(() -> System.out.println("HomePage: " + (getData() != null ? getData().getHomepage() : "..")));
    }
}
