package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Company;
import javafx.beans.binding.Bindings;

public class CompanyOverviewBox extends SimpleOverviewBox<Company> {

    public CompanyOverviewBox() {
        super();
    }

    public CompanyOverviewBox(Company data) {
        super(data);
        getStyleClass().add("company-overview-box");
        baseURLProperty().bind(Bindings.createStringBinding(() -> DataRepository.getInstance().getRepositoryDirectoryURL() + "companies/" + getData().getId(), dataProperty()));
        markdownProperty().bind(DataRepository.getInstance().companyDescriptionProperty(data));
    }
}
