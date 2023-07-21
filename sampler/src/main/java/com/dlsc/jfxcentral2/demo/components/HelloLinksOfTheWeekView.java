package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.LinksOfTheWeekView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class HelloLinksOfTheWeekView extends JFXCentralSampleBase {

    private LinksOfTheWeekView view;

    @Override
    protected Region createControl() {
        view = new LinksOfTheWeekView();
        view.getLinksOfTheWeeks().setAll(createLinksOfTheWeeks());
        return new ScrollPane(new StackPane(view));
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        view.sizeProperty().bind(sizeComboBox.valueProperty());
        //weekCount bind size
        view.weekCountProperty().bind(sizeComboBox.valueProperty().map(size -> size.isLarge() ? 3 : 1));
        return sizeComboBox;
    }

    private List<LinksOfTheWeek> createLinksOfTheWeeks() {
        List<LinksOfTheWeek> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            LinksOfTheWeek week = new LinksOfTheWeek();
            week.setName("[" + (i + 1) + "] 16 jan - 22 jan 2023");
            week.setDescription("""
                    #### Hello World
                    Foojay.io, the website for Friends Of OpenJDK, published the podcast ‘The State of JavaFX Framework, Libraries, and Projects’. These guests spoke about the JavaFX framework itself, but also about the libraries and applications that are built with it:
                    > Pedro Duque Vieira
                     
                    Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n
                    Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n
                    """);
            list.add(week);
        }

        return list;
    }

    @Override
    public String getSampleName() {
        return "LinksOfTheWeekView";
    }
}
