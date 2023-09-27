package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class TutorialTileView extends PreviewTileView<Tutorial> {

    public TutorialTileView(Tutorial tutorial) {
        super(tutorial);
        getStyleClass().add("tutorial-tile-view");
        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        imageProperty().bind(ImageManager.getInstance().tutorialImageLargeProperty(tutorial));
        LinkUtil.setLink(getButton1(), "/tutorials/" + tutorial.getId());
    }
}
