package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class TutorialTileView extends TileView<Tutorial> {

    public TutorialTileView(Tutorial tutorial) {
        super(tutorial);
        getStyleClass().add("tutorial-tile-view");
        imageProperty().bind(ImageManager.getInstance().tutorialImageLargeProperty(tutorial));
        descriptionProperty().bind(DataRepository.getInstance().tutorialTextProperty(tutorial));
        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));
        LinkUtil.setLink(getButton1(), "/tutorials/" + tutorial.getId());
    }
}
