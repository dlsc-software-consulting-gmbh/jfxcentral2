package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class LearnDetailBox extends DetailsBoxBase<Learn> {

    public LearnDetailBox() {
        getStyleClass().add("learn-details-box");
        setMaxItemsPerPage(3);
        setTitle("LEARN");
        setIkon(IkonUtil.getModelIkon(Learn.class));
    }

    @Override
    protected List<Node> createActionButtons(Learn learn) {
        return List.of(createDetailsButton(learn));
    }

    @Override
    protected String getDescription(Learn model) {
        if (model instanceof LearnJavaFX) {
            return "Learn JavaFX";
        } else if (model instanceof LearnMobile) {
            return "Learn Mobile";
        } else if (model instanceof LearnRaspberryPi) {
            return "Learn Raspberry Pi";
        }
        return "Learn";
    }

    @Override
    protected Node createMainPreView(Learn model) {
        FontIcon fontIcon = new FontIcon(IkonUtil.getModelIkon(model));
        fontIcon.getStyleClass().addAll("model-icon");
        StackPane iconWrapper = new StackPane(fontIcon);
        iconWrapper.getStyleClass().addAll("icon-wrapper");
        iconWrapper.managedProperty().bind(iconWrapper.visibleProperty());
        return iconWrapper;
    }
}
