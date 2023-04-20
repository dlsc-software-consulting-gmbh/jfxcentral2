package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.PaginationControl;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class HelloCustomPagination extends JFXCentralSampleBase {

    private PaginationControl pagination;

    @Override
    protected Region createControl() {
        pagination = new PaginationControl();
        pagination.setPageCount(10);
        pagination.setPageFactory(index -> new Label("Page " + index));
        return pagination;
    }

    @Override
    public String getSampleName() {
        return "CustomPagination";
    }

}
