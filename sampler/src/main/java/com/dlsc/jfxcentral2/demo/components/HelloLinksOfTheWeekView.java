package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.LinksOfTheWeekView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.Comparator;
import java.util.List;

public class HelloLinksOfTheWeekView extends JFXCentralSampleBase {

    private LinksOfTheWeekView view;

    @Override
    protected Region createControl() {
        List<LinksOfTheWeek> links = DataRepository.getInstance().getLinksOfTheWeek();
        links.sort(Comparator.comparing(LinksOfTheWeek::getCreatedOn).reversed());
        LinksOfTheWeek latestLotw = links.get(0);

        view = new LinksOfTheWeekView(latestLotw);
        view.getLinksOfTheWeeks().setAll(links);
        return new ScrollPane(new StackPane(view));
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        view.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "LinksOfTheWeekView";
    }
}
