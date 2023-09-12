package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CustomMarkdownTabPane;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.MarkdownTab;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class HelloTermsAndConditionsView extends JFXCentralSampleBase {

    private CustomMarkdownTabPane largeTabPane;
    private CustomMarkdownTabPane mediumTabPane;
    private CustomMarkdownTabPane smallTabPane;
    private final String termsText = readStrFromFile("/com/dlsc/jfxcentral2/demo/components/text/TermsAndConditions.txt");
    private final String privacyText = readStrFromFile("/com/dlsc/jfxcentral2/demo/components/text/PrivacyPolicy.txt");
    private final String cookieText = readStrFromFile("/com/dlsc/jfxcentral2/demo/components/text/CookiePolicy.txt");

    @Override
    protected Region createControl() {
        largeTabPane = createTabPane(Size.LARGE,2);
        mediumTabPane = createTabPane(Size.MEDIUM,0);
        smallTabPane = createTabPane(Size.SMALL,1);
        return new TabPane(
                new Tab("Large", largeTabPane),
                new Tab("Medium", mediumTabPane),
                new Tab("Small", smallTabPane));
    }

    private CustomMarkdownTabPane createTabPane(Size size, int selectedIndex) {
        CustomMarkdownTabPane  tabPane = new CustomMarkdownTabPane();
        tabPane.sizeProperty().set(size);
        tabPane.getTabs().setAll(
                new MarkdownTab("TERMS & CONDITIONS", termsText),
                new MarkdownTab("PRIVACY POLICY", privacyText),
                new MarkdownTab("COOKIE POLICY", cookieText)
        );
        //select
        tabPane.setSelectedIndex(selectedIndex);
        return tabPane;
    }

    private String readStrFromFile(String filePath) {
        String text = "";
        try {
            URI uri = Objects.requireNonNull(getClass().getResource(filePath)).toURI();
            text = Files.readString(Path.of(uri));
        } catch (Exception e) {
            System.out.println("Error reading file: " + filePath);
        }
        return text;
    }

    @Override
    public Node getControlPanel() {
        Spinner<Integer> spinnerIndex = new Spinner<>(0, 2, 0);
        spinnerIndex.valueProperty().addListener((observable, oldValue, newValue) -> {
            largeTabPane.setSelectedIndex(newValue);
            mediumTabPane.setSelectedIndex(newValue);
            smallTabPane.setSelectedIndex(newValue);
        });
        return new VBox(10, new VBox(3, new Label("Set selected Index"), spinnerIndex));
    }

    @Override
    public String getSampleName() {
        return "TermsAndConditionsView";
    }
}
