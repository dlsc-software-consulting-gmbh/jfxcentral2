package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.components.CategorySelectionView;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloCategorySelectionView extends JFXCentralSampleBase {

    @Override
    protected Region createControl() {
        CategorySelectionView view = new CategorySelectionView(null);
        view.setPrefWidth(400);
        view.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(view, Pos.CENTER);
        return new StackPane(view);
    }

    @Override
    public String getSampleName() {
        return "CategoriesSelectionView";
    }
}
