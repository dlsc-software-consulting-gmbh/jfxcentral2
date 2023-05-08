package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloPaginationControl2 extends JFXCentralSampleBase {

    @Override
    protected Region createControl() {
        PaginationControl2 paginationControl2 = new PaginationControl2();
        paginationControl2.setPageCount(12);
        paginationControl2.setCurrentPageIndex(0);
        paginationControl2.setMaxPageIndicatorCount(3);

        paginationControl2.setPageFactory(index -> {
            Label label = new Label("Page " + (index + 1));
            label.setAlignment(Pos.CENTER);
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            label.setStyle("-fx-border-color: black;");
            return label;
        });

        StackPane wrapper = new StackPane(paginationControl2);
        wrapper.setPadding(new Insets(20));

        return wrapper;
    }

    @Override
    public String getSampleName() {
        return "PaginationControl2";
    }
}
