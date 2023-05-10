package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.CompaniesDetailsObject;
import org.kordamp.ikonli.material2.Material2AL;

public class CompaniesDetailsBox extends SimpleDetailsBox<CompaniesDetailsObject> {

    public CompaniesDetailsBox() {
        getStyleClass().add("companies-details-box");
        setTitle("COMPANIES");
        setIkon(Material2AL.BUSINESS);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

    }

}
