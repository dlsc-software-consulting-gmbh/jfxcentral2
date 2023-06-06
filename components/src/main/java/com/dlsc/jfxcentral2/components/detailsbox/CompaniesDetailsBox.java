package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;

public class CompaniesDetailsBox extends SimpleDetailsBox<Company> {

    public CompaniesDetailsBox() {
        getStyleClass().add("companies-details-box");
        setTitle("COMPANIES");
        setIkon(IkonUtil.company);
    }

    @Override
    protected CustomImageView createImageView(Company model) {
        return new CustomImageView();
    }

}
