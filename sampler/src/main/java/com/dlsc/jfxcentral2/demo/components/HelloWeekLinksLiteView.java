package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.WeekLinksLiteView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloWeekLinksLiteView extends JFXCentralSampleBase {

    private WeekLinksLiteView weekLinksLiteView;

    @Override
    protected Region createControl() {
        weekLinksLiteView = new WeekLinksLiteView();
        weekLinksLiteView.setMdString("""
                #### Hello World
                Foojay.io, the website for Friends Of OpenJDK, published the podcast ‘The State of JavaFX Framework, Libraries, and Projects’. These guests spoke about the JavaFX framework itself, but also about the libraries and applications that are built with it:
                > Pedro Duque Vieira
                
                Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n
                Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n""");

        return new ScrollPane(weekLinksLiteView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        weekLinksLiteView.sizeProperty().bind(sizeComboBox.sizeProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "WeekLinksLiteView";
    }
}
