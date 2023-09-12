package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.CustomMarkdownTabPane;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.model.MarkdownTab;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class LegalPage extends PageBase {
    private static final Logger LOGGER = LogManager.getLogger(LegalPage.class);
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

    private static String readText(String fileName) {
        String filePath = "/com/dlsc/jfxcentral2/app/pages/" + fileName;

        try (InputStream inputStream = LegalPage.class.getResourceAsStream(filePath);
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            return bufferedReader.lines().reduce("", (s1, s2) -> s1 + "\n" + s2);
        } catch (IOException e) {
            LOGGER.error("Failed to read the file: " + filePath, e);
            throw new RuntimeException(e);
        }
    }
}
