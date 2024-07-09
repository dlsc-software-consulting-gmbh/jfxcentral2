package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.LearnOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileLearnDetailsPage extends MobileDetailsPageBase<Learn> {

    public MobileLearnDetailsPage(ObjectProperty<Size> size, Class<? extends Learn> clazz, String itemId) {
        super(size, clazz, itemId);
    }

    @Override
    public List<Node> content() {
        Learn learn = getItem();

        // header
        MobileCategoryHeader header = new MobileCategoryHeader() {
            @Override
            protected String goBackLink() {
                Class<? extends Learn> modelClazz = getModelClazz();
                if (modelClazz == LearnJavaFX.class) {
                    return PagePath.LEARN_JAVAFX;
                } else if (modelClazz == LearnRaspberryPi.class) {
                    return PagePath.LEARN_RASPBERRYPI;
                } else if (modelClazz == LearnMobile.class) {
                    return PagePath.LEARN_MOBILE;
                }
                return PagePath.HOME;
            }
        };
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(learn.getName());

        // overview box
        LearnOverviewBox learnOverviewBox = new LearnOverviewBox(learn);
        learnOverviewBox.sizeProperty().bind(sizeProperty());

        PrettyScrollPane detailsContentPane = new PrettyScrollPane(new StackPane(learnOverviewBox));
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);

        return List.of(header, detailsContentPane);
    }

}
