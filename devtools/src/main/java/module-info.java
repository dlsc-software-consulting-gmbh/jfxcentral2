open module com.dlsc.jfxcentral2.devtools {
    requires javafx.controls;
    requires javafx.swing;

    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j;
    requires com.github.weisj.jsvg;
    requires one.jpro.platform.routing.core;
    requires one.jpro.platform.routing.dev;
    requires com.dlsc.jfxcentral2.components;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.antdesignicons;
    requires org.kordamp.ikonli.boxicons;
    requires org.kordamp.ikonli.whhg;
    requires org.kordamp.ikonli.codicons;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.icomoon;

    requires com.dlsc.gemsfx;

    exports com.dlsc.jfxcentral2.devtools;
    exports com.dlsc.jfxcentral2.devtools.pathextractor;
}