package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CustomMarkdownTabPane;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.MarkdownTab;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloCustomMarkdownTabPane extends JFXCentralSampleBase {

    private CustomMarkdownTabPane customMarkdownTabPane;

    @Override
    protected Region createControl() {
        customMarkdownTabPane = new CustomMarkdownTabPane();
        customMarkdownTabPane.getTabs().setAll(
                new MarkdownTab("TERMS & CONDITIONS", """
                        #### TERMS & CONDITIONS
                        Foojay.io, the website for Friends Of OpenJDK, published the podcast ‘The State of JavaFX Framework, Libraries, and Projects’. These guests spoke about the JavaFX framework itself, but also about the libraries and applications that are built with it:
                        > Pedro Duque Vieira
                                        
                        Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n
                        Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n"""),
                new MarkdownTab("PRIVACY POLICY", """
                        #### PRIVACY POLICY
                        Foojay.io, the website for Friends Of OpenJDK, published the podcast ‘The State of JavaFX Framework, Libraries, and Projects’. These guests spoke about the JavaFX framework itself, but also about the libraries and applications that are built with it:
                        > Pedro Duque Vieira
                                        
                        Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n
                        Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n"""),
                new MarkdownTab("COOKIE POLICY", """
                        #### COOKIE POLICY
                        Foojay.io, the website for Friends Of OpenJDK, published the podcast ‘The State of JavaFX Framework, Libraries, and Projects’. These guests spoke about the JavaFX framework itself, but also about the libraries and applications that are built with it:
                        > Pedro Duque Vieira
                                        
                        Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n
                        Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n""")
        );

        return new ScrollPane(customMarkdownTabPane);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        customMarkdownTabPane.sizeProperty().bind(sizeComboBox.sizeProperty());
        return new VBox(sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "CustomMarkdownTabPane";
    }
}
