package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.home.CategoryAdvancedView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloCategoryAdvancedView extends JFXCentralSampleBase {

    private CategoryAdvancedView categoryAdvancedView;
    private SizeComboBox sizeComboBox;

    @Override
    protected Region createControl() {
        StackPane wrapper = new StackPane();
        wrapper.getStyleClass().add("white-bg");

        categoryAdvancedView = new CategoryAdvancedView();
        wrapper.getChildren().add(categoryAdvancedView);

        return wrapper;
    }

    @Override
    public Node getControlPanel() {
        sizeComboBox = new SizeComboBox();
        sizeComboBox.sizeProperty().addListener(it -> updateWidth());
        categoryAdvancedView.sizeProperty().bind(sizeComboBox.sizeProperty());

        updateWidth();
        return new VBox(10, sizeComboBox);
    }

    private void updateWidth() {
        Size newVal = sizeComboBox.getSize();

        if (newVal == Size.SMALL) {
            categoryAdvancedView.setMaxWidth(375);
        } else if (newVal == Size.MEDIUM) {
            categoryAdvancedView.setMaxWidth(768);
        } else if (newVal == Size.LARGE) {
            categoryAdvancedView.setMaxWidth(1440);
        } else {
            categoryAdvancedView.setMaxWidth(Double.MAX_VALUE);
        }
    }

    @Override
    public String getSampleName() {
        return "CategoryAdvancedView";
    }

    @Override
    public double getControlPanelDividerPosition() {
        return 0.75;
    }
}
