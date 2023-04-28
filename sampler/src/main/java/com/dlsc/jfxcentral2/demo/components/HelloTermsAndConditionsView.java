package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CustomMarkdownTabPane;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.MarkdownTab;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class HelloTermsAndConditionsView extends JFXCentralSampleBase {
    private CustomMarkdownTabPane customMarkdownTabPane;

    @Override
    protected Region createControl() {
        customMarkdownTabPane = new CustomMarkdownTabPane();

        String termsText = readStrFromFile("/com/dlsc/jfxcentral2/demo/components/text/TermsAndConditions.txt");
        String privacyText = readStrFromFile("/com/dlsc/jfxcentral2/demo/components/text/PrivacyPolicy.txt");
        String cookieText = readStrFromFile("/com/dlsc/jfxcentral2/demo/components/text/CookiePolicy.txt");

        customMarkdownTabPane.getTabs().setAll(
                new MarkdownTab("TERMS & CONDITIONS", termsText),
                new MarkdownTab("PRIVACY POLICY", privacyText),
                new MarkdownTab("COOKIE POLICY", cookieText)
        );

        return new ScrollPane(customMarkdownTabPane);
    }

    private String readStrFromFile(String filePath) {
        String text = "";
        try {
            URI uri = getClass().getResource(filePath).toURI();
            text = Files.readString(Path.of(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        customMarkdownTabPane.sizeProperty().bind(sizeComboBox.sizeProperty());
        return new VBox(sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "TermsAndConditionsView";
    }
}
