module com.dlsc.jfxcentral2.components {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
                
    opens com.dlsc.jfxcentral2.components to javafx.fxml;
    exports com.dlsc.jfxcentral2.components;
}