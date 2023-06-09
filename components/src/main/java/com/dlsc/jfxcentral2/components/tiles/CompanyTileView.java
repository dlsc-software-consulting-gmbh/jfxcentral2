package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class CompanyTileView extends PreviewTileView<Company> {

    public CompanyTileView(Company company) {
        super(company);
        getStyleClass().add("company-tile-view");
        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        LinkUtil.setLink(getButton1(), "/companies/" + company.getId());
    }

    @Override
    protected int getSizeReduction() {
        return 40;
    }
}
