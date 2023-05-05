package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class HelloPaginationControl2 extends JFXCentralSampleBase {

    private PaginationControl2 paginationControl2;

    @Override
    protected Region createControl() {
        paginationControl2 = new PaginationControl2();
        paginationControl2.setPageCount(12);
        paginationControl2.setCurrentPageIndex(0);
        paginationControl2.setMaxPageIndicatorCount(3);

        paginationControl2.setPageFactory(index -> new Label("Page " + (index + 1)));

        return paginationControl2;
    }

    @Override
    public String getSampleName() {
        return "PaginationControl2";
    }
}
