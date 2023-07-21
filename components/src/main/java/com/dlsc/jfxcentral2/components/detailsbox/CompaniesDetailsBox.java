package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;

import java.util.List;

public class CompaniesDetailsBox extends DetailsBoxBase<Company> {

    public CompaniesDetailsBox() {
        getStyleClass().add("companies-details-box");
        setTitle("COMPANIES");
        setIkon(IkonUtil.getModelIkon(Company.class));
    }

    @Override
    protected List<Node> createActionButtons(Company company) {
        return List.of(createDetailsButton(company));
    }
}
