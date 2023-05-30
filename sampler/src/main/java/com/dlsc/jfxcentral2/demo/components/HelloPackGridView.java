package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.tiles.IkonliPackTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.filter.IconPack;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.Optional;

public class HelloPackGridView extends JFXCentralSampleBase {

    private ModelGridView<IkonliPack> gridView;

    @Override
    protected Region createControl() {

        gridView = new ModelGridView<>();
        gridView.setTileViewProvider(IkonliPackTileView::new);
        IconPack[] values = IconPack.values();
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                continue;
            }
            Optional<IkonliPack> ikonPackModel = DataRepository.getInstance().getIkonliPackById(values[i].name().toLowerCase());
            if (ikonPackModel.isPresent()) {
                gridView.getItems().add(ikonPackModel.get());
            }
        }

        return new ScrollPane(gridView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        gridView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "ModelGridView<IkonPackModel>";
    }
}
