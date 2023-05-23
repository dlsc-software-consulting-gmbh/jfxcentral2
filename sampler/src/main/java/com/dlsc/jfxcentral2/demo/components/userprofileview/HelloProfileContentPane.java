package com.dlsc.jfxcentral2.demo.components.userprofileview;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.userprofile.ProfileContentPane;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.RegisteredUser;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloProfileContentPane extends JFXCentralSampleBase {

    private ProfileContentPane profileContentPane;

    @Override
    protected Region createControl() {
        profileContentPane = new ProfileContentPane();
        RegisteredUser user = new RegisteredUser(null, "Dirk Lemmermann", "dlemmermann@gmail.com");
        profileContentPane.setUser(user);
        return new ScrollPane(profileContentPane);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        profileContentPane.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "ProfileContentPane";
    }
}
