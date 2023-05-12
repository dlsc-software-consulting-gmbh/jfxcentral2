package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral2.app.pages.PageBase;
import com.dlsc.jfxcentral2.components.CustomMarkdownTabPane;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.model.MarkdownTab;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class LegalPage extends PageBase {

    private static final String termsText = readText("TermsAndConditions.txt");
    private static final String privacyText = readText("PrivacyPolicy.txt");
    private static final String cookieText = readText("CookiePolicy.txt");

    private final Section section;

    public enum Section {
        TERMS,
        COOKIES,
        PRIVACY
    }

    public LegalPage(ObjectProperty<Size> size, Section section) {
        super(size, TopMenuBar.Mode.LIGHT);
        this.section = section;
    }

    @Override
    public String title() {
        return "Terms & Conditions";
    }

    @Override
    public String description() {
        return null;
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
            case TERMS -> {
                // TODO: add tab selection
            }
            case COOKIES -> {
            }
            case PRIVACY -> {
            }
        }

        // strip view
        StripView stripView = new StripView(customMarkdownTabPane);
        stripView.sizeProperty().bind(sizeProperty());

        return wrapContent(stripView);
    }

    private static String readText(String filePath) {
        String text = "";
        try {
            URI uri = LegalPage.class.getResource(filePath).toURI();
            text = Files.readString(Path.of(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
