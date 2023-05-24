import com.dlsc.jfxcentral2.iconfont.JFXCentralIkonliHandler;

module com.dlsc.jfxcentral2.iconfont {
    requires javafx.controls;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;

    exports com.dlsc.jfxcentral2.iconfont;

    opens com.dlsc.jfxcentral2.iconfont;

    provides org.kordamp.ikonli.IkonHandler with JFXCentralIkonliHandler;
}