package com.dlsc.jfxcentral2.app.filters;

import com.dlsc.jfxcentral2.components.CopyrightView;
import com.dlsc.jfxcentral2.components.FooterView;
import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.Filter;
import one.jpro.platform.routing.Request;
import one.jpro.platform.routing.filter.container.ContainerVBox;
import one.jpro.platform.routing.filter.container.ContainerFilter;

public class FooterFilter {

    public static Filter create(ObjectProperty<Size> sizeProperty) {
        return ContainerFilter.create(() -> new FooterFilterContainer(sizeProperty), FooterFilterContainer.class);
    }

    static class FooterFilterContainer extends ContainerVBox {
        public FooterFilterContainer(ObjectProperty<Size> sizeProperty) {
            // footer
            FooterView footerView = new FooterView();
            footerView.sizeProperty().bind(sizeProperty);

            // sponsors
            SponsorsView sponsorsView = new SponsorsView();
            sponsorsView.sizeProperty().bind(sizeProperty);

            // copyright
            CopyrightView copyrightView = new CopyrightView();
            copyrightView.sizeProperty().bind(sizeProperty);

            contentProperty().addListener((p,o,it) -> {
                getChildren().setAll(it, sponsorsView, footerView, copyrightView);
            });
        }
    }

}
