open module com.dlsc.jfxcentral2.app {
    requires javafx.controls;
    requires javafx.fxml;


    requires jfxcentral.data;
    requires org.eclipse.jgit;
    requires fr.brouillard.oss.cssfx;
    requires com.dlsc.jfxcentral2.components;
    requires one.jpro.routing.core;
    requires one.jpro.routing.dev;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.materialdesign;
    requires javafx.web;

    exports com.dlsc.jfxcentral2.app;
}