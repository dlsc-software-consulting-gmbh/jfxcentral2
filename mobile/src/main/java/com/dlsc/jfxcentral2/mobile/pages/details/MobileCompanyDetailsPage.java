package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.CompanyDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.CompanyOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class MobileCompanyDetailsPage extends MobileDetailsPageBase<Company> {

    public MobileCompanyDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Company.class, itemId);
    }

    @Override
    public List<Node> content() {
        Company company = getItem();

        // header
        CompanyDetailHeader header = new CompanyDetailHeader(company);
        header.sizeProperty().bind(sizeProperty());

        // overview
        CompanyOverviewBox companyOverviewBox = new CompanyOverviewBox(company);
        companyOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(companyOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return List.of(header, detailsContentPane);
    }
}
