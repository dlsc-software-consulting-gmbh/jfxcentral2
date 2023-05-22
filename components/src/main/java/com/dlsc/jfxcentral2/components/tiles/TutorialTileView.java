package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class TutorialTileView extends TileView<Tutorial> {

    public TutorialTileView() {
        getStyleClass().add("tutorial-tile-view");

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));

        dataProperty().addListener(it -> {
            Tutorial tutorial = getData();
            if (tutorial != null) {
                LinkUtil.setLink(getButton1(), "/tutorials/" + tutorial.getId());
            }
        });
    }
}
