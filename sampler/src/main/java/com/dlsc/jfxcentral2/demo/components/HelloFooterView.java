package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.FooterView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloFooterView extends JFXCentralSampleBase {

    private FooterView footer;

    @Override
    public String getSampleName() {
        return "Footer";
    }

    @Override
    protected Region createControl() {
        footer = new FooterView();
        footer.setOnSendMail(() -> System.out.println("Send mail to dlemmermann@gmail.com"));
        footer.setOnTwitter(() -> System.out.println("https://twitter.com/dlemmermann"));
        footer.setOnGithub(() -> System.out.println("https://github.com/dlsc-software-consulting-gmbh"));
        footer.setOnLinkedin(() -> System.out.println("https://www.linkedin.com/in/dlemmermann/"));
        footer.setOnYoutube(() -> System.out.println("youtube channel"));
        footer.setOnTC(() -> System.out.println("onT&C"));
        footer.setOnPrivacyPolicy(() -> System.out.println("onPrivacyPolicy"));
        footer.setOnCookies(() -> System.out.println("onCookies"));
        return new ScrollPane(footer);
    }

    @Override
    public Node getControlPanel() {
        VBox box = new VBox(10);
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        footer.sizeProperty().bind(sizeComboBox.sizeProperty());
        box.getChildren().addAll(new Label("Change Size:"),sizeComboBox);
        return box;
    }
}
