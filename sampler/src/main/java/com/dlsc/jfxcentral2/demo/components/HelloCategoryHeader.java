package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CategoryHeader;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignV;

public class HelloCategoryHeader extends JFXCentralSampleBase {

    private CategoryHeader categoryHeader;

    @Override
    protected Region createControl() {
        categoryHeader = new CategoryHeader();
        return new ScrollPane(categoryHeader);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        categoryHeader.sizeProperty().bind(sizeComboBox.valueProperty());

        ComboBox<String> titleComboBox = new ComboBox<>(FXCollections.observableArrayList("Videos", "People"));
        titleComboBox.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) ->{
            if (nv.equals("Videos")) {
                categoryHeader.setTitle("Videos");
                categoryHeader.setIkon(MaterialDesignV.VIDEO_OUTLINE);
            } else {
                categoryHeader.setTitle("People");
                categoryHeader.setIkon(Material2OutlinedAL.ACCOUNT_BOX);
            }
        });
        titleComboBox.getSelectionModel().selectFirst();
        return new VBox(10,sizeComboBox,new Separator(),new Label("Header Title"), titleComboBox);
    }

    @Override
    public String getSampleName() {
        return "CategoryHeaderVideos";
    }
}
