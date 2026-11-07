package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral2.mobile.components.LearnPagination;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
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
        MobilePageHeader header = new MobilePageHeader();
        header.sizeProperty().bind(sizeProperty());

        // content
        LearnPagination<Learn> detailsView = new LearnPagination<>();
        if (learn instanceof LearnJavaFX) {
            List<LearnJavaFX> learnJavaFX = DataRepository.getInstance().getLearnJavaFX();
            detailsView.getItems().setAll(learnJavaFX);
        } else if (learn instanceof LearnMobile) {
            List<LearnMobile> learnMobile = DataRepository.getInstance().getLearnMobile();
            detailsView.getItems().setAll(learnMobile);
        } else if (learn instanceof LearnRaspberryPi) {
            List<LearnRaspberryPi> learnRaspberryPi = DataRepository.getInstance().getLearnRaspberryPi();
            detailsView.getItems().setAll(learnRaspberryPi);
        }
        // Images in a lesson resolve against that lesson's folder, so the base
        // URL follows the lesson shown, not the one the page opened on.
        detailsView.setBaseURL(baseURL(learn));
        detailsView.itemProperty().addListener((obs, o, n) -> detailsView.setBaseURL(baseURL(n)));
        detailsView.setSelectedItem(learn);

        // bind header title to item name
        header.titleProperty().bind(detailsView.itemProperty().map(item -> item == null ? "" : item.getName()));

        VBox.setVgrow(detailsView, Priority.ALWAYS);
        return List.of(header, detailsView);
    }

    private static String baseURL(Learn learn) {
        String folder;
        if (learn instanceof LearnJavaFX) {
            folder = "learn/javafx/";
        } else if (learn instanceof LearnMobile) {
            folder = "learn/mobile/";
        } else if (learn instanceof LearnRaspberryPi) {
            folder = "learn/raspberrypi/";
        } else {
            return null;
        }
        return DataRepository.getInstance().getRepositoryDirectoryURL() + folder + learn.getId();
    }
}
