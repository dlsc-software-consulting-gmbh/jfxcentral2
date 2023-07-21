package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.CustomMarkdownTabPane;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.model.MarkdownTab;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class LegalPage extends PageBase {

    private static final String termsText = readText("terms-and-conditions.md");
    private static final String privacyText = readText("privacy-policy.md");
    private static final String cookieText = readText("cookie-policy.md");

    private final Section section;

    public enum Section {
        TERMS,
        COOKIES,
        PRIVACY
    }

    public LegalPage(ObjectProperty<Size> size, Section section) {
        super(size, Mode.LIGHT);
        this.section = section;
    }

    @Override
    public String title() {
        return "Terms & Conditions";
    }

    @Override
    public String description() {
        return "Lists the terms and conditions, the privacy policy, and the cookie policy of JFXCentral.";
    }

    @Override
    public Node content() {
        // tab pane
        CustomMarkdownTabPane customMarkdownTabPane = new CustomMarkdownTabPane();
        customMarkdownTabPane.sizeProperty().bind(sizeProperty());

        customMarkdownTabPane.getTabs().setAll(
                new MarkdownTab("TERMS & CONDITIONS", termsText),
                new MarkdownTab("PRIVACY POLICY", privacyText),
                new MarkdownTab("COOKIE POLICY", cookieText)
        );

        switch (section) {
            case TERMS -> customMarkdownTabPane.setSelectedIndex(0);
            case PRIVACY -> customMarkdownTabPane.setSelectedIndex(1);
            case COOKIES -> customMarkdownTabPane.setSelectedIndex(2);
        }

        // strip view
        StripView stripView = new StripView(customMarkdownTabPane);
        stripView.sizeProperty().bind(sizeProperty());

        return wrapContent(stripView);
    }

    private static String readText(String filePath) {
        String text = "";
        try {
            URI uri = Objects.requireNonNull(LegalPage.class.getResource(filePath)).toURI();
            text = Files.readString(Path.of(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
