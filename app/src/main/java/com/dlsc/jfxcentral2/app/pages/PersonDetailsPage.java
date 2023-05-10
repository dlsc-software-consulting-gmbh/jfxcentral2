package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class PersonDetailsPage extends DetailsPageBase {

    public PersonDetailsPage(ObjectProperty<Size> size, TopMenuBar.Mode menuMode) {
        super(size, menuMode);
    }

    @Override
    public String title() {
        return "JFXCentral - Person";
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public Node content() {
        return null;
    }
}
