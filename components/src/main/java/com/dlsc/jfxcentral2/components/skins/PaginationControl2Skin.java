package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.components.PaginationControl2;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class PaginationControl2Skin extends SkinBase<PaginationControl2> {

    private PaginationControl2 control;
    private final HBox controlBox;
    private final Button leftArrowButton;
    private final Button rightArrowButton;
    private final Label ellipsisLeftLabel;
    private final Button toFirstButton;
    private final Label ellipsisRightLabel;
    private final Button toLastButton;
    private final ToggleGroup buttonToggleGroup;

    public PaginationControl2Skin(PaginationControl2 control) {
        super(control);
        this.control = control;

        BorderPane contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");

        controlBox = new HBox();
        controlBox.getStyleClass().add("control-box");

        leftArrowButton = new Button();
        leftArrowButton.getStyleClass().add("left-arrow-button");
        leftArrowButton.setGraphic(new FontIcon(MaterialDesign.MDI_ARROW_LEFT));
        leftArrowButton.setOnAction(e -> {
            int newIndex = control.getCurrentPageIndex() - 1;
            if (newIndex >= 0) {
                control.setCurrentPageIndex(newIndex);
            }
        });

        rightArrowButton = new Button();
        rightArrowButton.getStyleClass().add("left-arrow-button");
        rightArrowButton.setGraphic(new FontIcon(MaterialDesign.MDI_ARROW_RIGHT));
        rightArrowButton.setOnAction(e -> {
            int newIndex = control.getCurrentPageIndex() + 1;
            if (newIndex < control.getPageCount()) {
                control.setCurrentPageIndex(newIndex);
            }
        });

        toFirstButton = new Button("1");
        toFirstButton.getStyleClass().addAll("first", "number-button");
        toFirstButton.setOnAction(e -> control.setCurrentPageIndex(0));

        ellipsisLeftLabel = new Label("...");
        ellipsisLeftLabel.getStyleClass().addAll("ellipsis-label", "ellipsis-left");

        ellipsisRightLabel = new Label("...");
        ellipsisRightLabel.getStyleClass().addAll("ellipsis-label", "ellipsis-right");

        toLastButton = new Button();
        toLastButton.getStyleClass().addAll("last", "number-button");
        toLastButton.setOnAction(e -> control.setCurrentPageIndex(control.getPageCount() - 1));

        buttonToggleGroup = new ToggleGroup();

        contentPane.setBottom(controlBox);
        BorderPane.setMargin(controlBox, new Insets(50, 0, 0, 0));

        BorderPane.setAlignment(controlBox, Pos.CENTER);
        contentPane.centerProperty().bind(Bindings.createObjectBinding(() -> control.getPageFactory().call(control.getCurrentPageIndex()),
                control.currentPageIndexProperty(),
                control.pageFactoryProperty()
        ));
        getChildren().add(contentPane);

        updateControlBox();
        control.pageCountProperty().addListener(it -> updateControlBox());
        control.currentPageIndexProperty().addListener(it -> updateControlBox());
        control.maxPageIndicatorCountProperty().addListener(it -> updateControlBox());
    }

    private int computeToggleButtonNumber(int index, int pageCount, int currentPageIndex, int maxPageIndicatorCount) {
        int middle = maxPageIndicatorCount / 2;
        if (pageCount <= maxPageIndicatorCount) {
            return index;
        }
        if (currentPageIndex < middle) {
            return index;
        }
        if (currentPageIndex > pageCount - middle - 1) {
            return pageCount - maxPageIndicatorCount + index;
        }
        return currentPageIndex - middle + index;
    }

    private void updateControlBox() {
        buttonToggleGroup.getToggles().clear();
        controlBox.getChildren().clear();

        leftArrowButton.setDisable(control.getCurrentPageIndex() == 0);
        controlBox.getChildren().add(leftArrowButton);

        int maxPageIndicatorCount = control.getMaxPageIndicatorCount();
        int pageCount = control.getPageCount();
        int currentPage = control.getCurrentPageIndex();

        if (currentPage >= maxPageIndicatorCount) {
            controlBox.getChildren().addAll(toFirstButton, ellipsisLeftLabel);
        }

        for (int i = 0; i < pageCount && i < maxPageIndicatorCount; i++) {
            int num = computeToggleButtonNumber(i, pageCount, currentPage, maxPageIndicatorCount);
            CustomToggleButton numberToggleButton = new CustomToggleButton(String.valueOf(num + 1));
            numberToggleButton.getStyleClass().add("number-button");
            numberToggleButton.setSelected(num == control.getCurrentPageIndex());
            numberToggleButton.setOnAction(e -> control.setCurrentPageIndex(num));
            controlBox.getChildren().add(numberToggleButton);
            buttonToggleGroup.getToggles().add(numberToggleButton);
        }

        if (pageCount - currentPage > maxPageIndicatorCount) {
            toLastButton.setText(String.valueOf(pageCount));
            controlBox.getChildren().addAll(ellipsisRightLabel, toLastButton);
        }

        rightArrowButton.setDisable(control.getCurrentPageIndex() == control.getPageCount() - 1);
        controlBox.getChildren().add(rightArrowButton);
    }
}
