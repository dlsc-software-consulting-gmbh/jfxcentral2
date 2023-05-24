package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Company;

public class CompanyDetailHeader extends SimpleDetailHeader<Company>  {

    public CompanyDetailHeader(Company company) {
        super(company);
        getStyleClass().add("company-detail-header");
        setWebsite(getModel().getHomepage());
    }
}
