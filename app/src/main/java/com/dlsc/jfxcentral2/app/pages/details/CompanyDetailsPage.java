package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;

public class CompanyDetailsPage extends DetailsPageBase<Company> {

    public CompanyDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Company.class, itemId);
    }
}
