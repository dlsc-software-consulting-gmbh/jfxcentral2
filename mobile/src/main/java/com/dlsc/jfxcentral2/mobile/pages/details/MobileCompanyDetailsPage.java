package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Company;

import com.dlsc.jfxcentral2.components.overviewbox.CompanyOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.LinkedObjectsBox;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileCompanyDetailsPage extends MobileDetailsPageBase<Company> {

    public MobileCompanyDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Company.class, itemId);
    }

    @Override
    public List<Node> content() {
        Company company = getItem();

        // header
        MobilePageHeader header = new MobilePageHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setIcon(IkonUtil.getModelIkon(Company.class));
        header.setTitle(company.getName());

        // overview
        CompanyOverviewBox companyOverviewBox = new CompanyOverviewBox(company);
        companyOverviewBox.sizeProperty().bind(sizeProperty());
        companyOverviewBox.imageProperty().bind(ImageManager.getInstance().companyImageProperty(company));
        companyOverviewBox.setIcon(null);
        companyOverviewBox.setTitle(null);

        // linked objects
        LinkedObjectsBox<Company> linkedObjectsBox = new LinkedObjectsBox<>(company);
        linkedObjectsBox.sizeProperty().bind(sizeProperty());

        VBox detailsPageContentWrapper = new VBox(companyOverviewBox, linkedObjectsBox);
        detailsPageContentWrapper.getStyleClass().add("details-page-content-wrapper");

        ScrollPane detailsContentPane = new ScrollPane(detailsPageContentWrapper);
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);

        return List.of(header, detailsContentPane);
    }
}
