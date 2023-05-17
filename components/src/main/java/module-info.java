open module com.dlsc.jfxcentral2.components {
    requires javafx.controls;
    requires javafx.fxml;

    requires fr.brouillard.oss.cssfx;

    requires jfxcentral.data;
    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.material2;
    requires com.dlsc.gemsfx;
    requires com.sandec.mdfx;
    requires org.apache.commons.lang3;

    requires jpro.webapi;
    requires one.jpro.routing.core;

    exports com.dlsc.jfxcentral2.components;
    exports com.dlsc.jfxcentral2.model;
    exports com.dlsc.jfxcentral2.components.skins;
    exports com.dlsc.jfxcentral2.utils;
    exports com.dlsc.jfxcentral2.components.detailsbox;
    exports com.dlsc.jfxcentral2.model.filter;
    exports com.dlsc.jfxcentral2.components.tiles;
    exports com.dlsc.jfxcentral2.components.filters;
    exports com.dlsc.jfxcentral2.components.headers;
    exports com.dlsc.jfxcentral2.components.gridview;
}