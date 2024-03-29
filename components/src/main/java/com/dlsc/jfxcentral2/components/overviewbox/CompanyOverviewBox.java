package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Company;

public class CompanyOverviewBox extends SimpleOverviewBox<Company> {

    public CompanyOverviewBox(Company company) {
        super(company);
        getStyleClass().add("company-overview-box");
        setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "companies/" + getModel().getId());
        setMarkdown(DataRepository2.getInstance().getCompanyReadMe(company));
    }
}
