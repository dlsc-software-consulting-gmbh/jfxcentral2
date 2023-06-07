package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class CompanyTileView extends TileView<Company> {

    public CompanyTileView(Company company) {
        super(company);
        getStyleClass().add("company-tile-view");
        setButton1Text("HOMEPAGE");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        LinkUtil.setLink(getButton1(), "/companies/" + company.getId());
    }

    protected Node createFrontTop() {
        //company logo
        CustomImageView logoView = new CustomImageView();
        logoView.imageProperty().bind(imageProperty());

        StackPane imageContainer = new StackPane();
        imageContainer.getStyleClass().add("image-container");
        imageContainer.getChildren().setAll(logoView);
        logoView.fitWidthProperty().bind(imageContainer.widthProperty().subtract(20));
        logoView.fitHeightProperty().bind(imageContainer.heightProperty().subtract(20));

        return imageContainer;
    }
}
