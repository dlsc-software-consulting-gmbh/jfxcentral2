package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Company;

public class CompanyDetailHeader extends SimpleDetailHeader<Company>  {

    public CompanyDetailHeader(Company company) {
        super(company);

        getStyleClass().add("company-detail-header");

        setDescription(company.getSummary());
        setWebsite(getModel().getHomepage());
        setShareUrl("companies/" + company.getId());
        setShareText("Found this company on @JFXCentral: " + company.getName());
        setShareTitle("Company: " + company.getName());
        setBackText("ALL COMPANIES");
        setBackUrl("/companies");
    }
}
