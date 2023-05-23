package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.components.overviewbox.CompanyOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class CompanyDetailsPage extends DetailsPageBase<Company> {

    public CompanyDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Company.class, itemId);
    }

    @Override
    public Node content() {
        // header
        CategoryHeader<Company> header = new CategoryHeader<>();
        header.setTitle(getItem().getName());
        header.setIkon(IkonUtil.getModelIkon(getItem()));
        header.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(new CompanyOverviewBox(getItem()));
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }
}
