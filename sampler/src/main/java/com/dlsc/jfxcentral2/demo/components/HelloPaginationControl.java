package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.PaginationControl;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloPaginationControl extends JFXCentralSampleBase {

    @Override
    protected Region createControl() {
        PaginationControl pagination = new PaginationControl();
        pagination.setPageCount(10);
        pagination.setPageFactory(index -> {
            Label label = new Label("Page " + index);
            label.setPrefSize(300, 150);
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-background-color: red;");
            return label;
        });
        pagination.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        StackPane.setAlignment(pagination, Pos.CENTER);
        StackPane stackPane = new StackPane(pagination);
        stackPane.setStyle("-fx-background-color: orange; -fx-padding: 20px;");
        return stackPane;
    }

    @Override
    public String getSampleName() {
        return "CustomPagination";
    }

}
