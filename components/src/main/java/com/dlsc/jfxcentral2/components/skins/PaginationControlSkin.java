package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.PaginationControl;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Skin;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class PaginationControlSkin extends ControlBaseSkin<PaginationControl> {

    public PaginationControlSkin(PaginationControl control) {
        super(control);

        Button btnPrev = new Button();
        btnPrev.getStyleClass().add("prev-button");
        btnPrev.setGraphic(new FontIcon(IkonUtil.arrowLeft));
        btnPrev.setFocusTraversable(false);
        BorderPane.setAlignment(btnPrev, Pos.CENTER);

        Label pageInformationLabel = new Label();
        pageInformationLabel.getStyleClass().add("page-information-label");

        pageInformationLabel.textProperty().bind(
                control.currentPageIndexProperty().add(Bindings.when(control.pageCountProperty().greaterThan(0)).then(1).otherwise(0))
                        .asString()
                        .concat(control.separatorTextProperty())
                        .concat(control.pageCountProperty())
        );

        BorderPane.setAlignment(pageInformationLabel, Pos.CENTER);

        Button btnNext = new Button();
        btnNext.getStyleClass().add("next-button");
        btnNext.setGraphic(new FontIcon(IkonUtil.arrowRight));
        btnNext.setFocusTraversable(false);
        BorderPane.setAlignment(btnNext, Pos.CENTER);

        BorderPane controlBox = new BorderPane();
        controlBox.setLeft(btnPrev);
        controlBox.setRight(btnNext);
        controlBox.setCenter(pageInformationLabel);
        controlBox.getStyleClass().add("control-box");
        getChildren().setAll(controlBox);

        btnPrev.setOnAction(e -> {
            int currentPageIndex = control.getCurrentPageIndex();
            if (currentPageIndex > 0) {
                control.setCurrentPageIndex(currentPageIndex - 1);
            }
        });

        btnNext.setOnAction(e -> {
            int currentPageIndex = control.getCurrentPageIndex();
            if (currentPageIndex < control.getPageCount() - 1) {
                control.setCurrentPageIndex(currentPageIndex + 1);
            }
        });

        Pagination pagination = new Pagination();
        pagination.pageFactoryProperty().bind(control.pageFactoryProperty());
        pagination.pageCountProperty().bind(control.pageCountProperty());
        pagination.currentPageIndexProperty().bindBidirectional(control.currentPageIndexProperty());
        pagination.skinProperty().addListener(it -> {
            Skin<?> skin = pagination.getSkin();
            if (skin != null) {
                Node paginationControl = pagination.lookup(".pagination-control");
                paginationControl.setVisible(false);
                paginationControl.setManaged(false);
            }
        });

        control.currentPageIndexProperty().addListener((ob, ov, nv) -> {
            if (nv.intValue() < 0 || nv.intValue() > control.getPageCount() - 1) {
                return;
            }
            btnPrev.setDisable(nv.intValue() == 0);
            btnNext.setDisable(nv.intValue() == control.getPageCount() - 1);
        });

        control.pageCountProperty().addListener((ob, ov, nv) -> {
            if (nv.intValue() < 0) {
                return;
            }
            btnPrev.setDisable(control.getCurrentPageIndex() == 0);
            btnNext.setDisable(control.getCurrentPageIndex() == control.getPageCount() - 1);
        });

        int pageIndex = control.getCurrentPageIndex();
        btnPrev.setDisable(pageIndex == 0);

        VBox.setVgrow(pagination, Priority.ALWAYS);

        BorderPane centerPane = new BorderPane();
        centerPane.getStyleClass().add("center-pane");

        centerPane.centerProperty().bind(Bindings.createObjectBinding(() -> {
                    if ((control.getCurrentPageIndex() <= 0 && control.getPageCount() <= 0) || control.pageFactoryProperty() == null) {
                        Node placeholder = control.getPlaceholder();
                        if (placeholder == null) {
                            return null;
                        }
                        StackPane placeholderWrapper = new StackPane(placeholder);
                        placeholderWrapper.getStyleClass().add("placeholder-wrapper");
                        return placeholderWrapper;
                    }
                    return pagination;
                },
                control.currentPageIndexProperty(),
                control.pageFactoryProperty()));

        VBox contentPane = new VBox(centerPane, controlBox);
        contentPane.setAlignment(Pos.TOP_CENTER);
        contentPane.getStyleClass().add("content-pane");

        getChildren().setAll(contentPane);
    }
}
