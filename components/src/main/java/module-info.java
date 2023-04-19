open module com.dlsc.jfxcentral2.components {
    requires javafx.controls;
    requires javafx.fxml;

    requires fr.brouillard.oss.cssfx;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.material2;
    requires com.dlsc.gemsfx;

    exports com.dlsc.jfxcentral2;
    exports com.dlsc.jfxcentral2.components;
    exports com.dlsc.jfxcentral2.model;
    exports com.dlsc.jfxcentral2.components.skins;
}