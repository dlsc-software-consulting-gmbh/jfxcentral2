package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;

public class CompaniesDetailsBox extends SimpleDetailsBox<Company> {

    public CompaniesDetailsBox() {
        getStyleClass().add("companies-details-box");
        setTitle("COMPANIES");
        setIkon(IkonUtil.company);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getName());
        });

    }

    @Override
    protected CustomImageView createImageView(Company model) {
        CustomImageView imageView = new CustomImageView();
        imageView.imageProperty().bind(ImageManager.getInstance().companyImageProperty(model));
        return imageView;
    }

}
