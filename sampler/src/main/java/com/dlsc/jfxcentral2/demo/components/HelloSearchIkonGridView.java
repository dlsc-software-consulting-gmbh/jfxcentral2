package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;

import java.util.Set;
import java.util.stream.Collectors;

public class HelloSearchIkonGridView extends JFXCentralSampleBase {
    private IkonGridView ikonGridView;

    @Override
    protected Region createControl() {
        ikonGridView = new IkonGridView();

        return new ScrollPane(ikonGridView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        ikonGridView.sizeProperty().bind(sizeComboBox.valueProperty());
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        Set<Ikon> ikons = IkonliPackUtil.getInstance().getDataMap().keySet();
        System.out.println(ikons.size());
        ikonGridView.itemsProperty().bind(Bindings.createObjectBinding(()->
                FXCollections.observableArrayList(ikons.stream().filter(it -> StringUtils.containsIgnoreCase( it.getDescription(),searchField.getText().trim()))
                      .collect(Collectors.toList())), searchField.textProperty()));
        return new VBox(sizeComboBox, searchField);
    }

    @Override
    public String getSampleName() {
        return "IkonGridView";
    }
}
