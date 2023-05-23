package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class CompanyTileView extends TileView<Company> {

    public CompanyTileView(Company company) {
        super(company);
        getStyleClass().add("company-tile-view");
        setButton1Text("HOMEPAGE");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        descriptionProperty().bind(DataRepository.getInstance().companyDescriptionProperty(company));
        LinkUtil.setLink(getButton1(), "/companies/" + company.getId());
    }
}
