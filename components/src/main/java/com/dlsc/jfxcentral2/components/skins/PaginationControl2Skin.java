package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;

public class PaginationControl2Skin extends SkinBase<PaginationControl2> {

    private final ToggleGroup toggleGroup;
    private PaginationControl2 control;
    private final HBox controlBox;
    private final Button leftArrowButton;
    private final Button rightArrowButton;
    private final Button toFirstButton;
    private final Button toLastButton;
    private final List<CustomToggleButton> numButtons = new ArrayList<>();

    public PaginationControl2Skin(PaginationControl2 control) {
        super(control);
        this.control = control;
        control.setFocusTraversable(false);
        BorderPane contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");

        controlBox = new HBox();
        controlBox.getStyleClass().add("control-box");

        leftArrowButton = new Button();
        leftArrowButton.getStyleClass().add("left-arrow-button");
        leftArrowButton.setGraphic(new FontIcon(IkonUtil.arrowLeft));
        leftArrowButton.setFocusTraversable(false);
        leftArrowButton.setOnAction(e -> {
            int newIndex = control.getCurrentPageIndex() - 1;
            if (newIndex >= 0) {
                control.setCurrentPageIndex(newIndex);
            }
        });

        rightArrowButton = new Button();
        rightArrowButton.getStyleClass().add("left-arrow-button");
        rightArrowButton.setGraphic(new FontIcon(IkonUtil.arrowRight));
        rightArrowButton.setFocusTraversable(false);
        rightArrowButton.setOnAction(e -> {
            int newIndex = control.getCurrentPageIndex() + 1;
            if (newIndex < control.getPageCount()) {
                control.setCurrentPageIndex(newIndex);
            }
        });

        toFirstButton = new Button("1");
        toFirstButton.getStyleClass().addAll("first", "number-button");
        toFirstButton.setOnAction(e -> control.setCurrentPageIndex(0));
        toFirstButton.managedProperty().bind(toFirstButton.visibleProperty());

        Label ellipsisLeftLabel = new Label("...");
        ellipsisLeftLabel.getStyleClass().addAll("ellipsis-label", "ellipsis-left");
        ellipsisLeftLabel.managedProperty().bind(ellipsisLeftLabel.visibleProperty());
        ellipsisLeftLabel.visibleProperty().bind(toFirstButton.visibleProperty());

        toLastButton = new Button();
        toLastButton.getStyleClass().addAll("last", "number-button");
        toLastButton.setOnAction(e -> control.setCurrentPageIndex(control.getPageCount() - 1));
        toLastButton.managedProperty().bind(toLastButton.visibleProperty());

        Label ellipsisRightLabel = new Label("...");
        ellipsisRightLabel.getStyleClass().addAll("ellipsis-label", "ellipsis-right");
        ellipsisRightLabel.managedProperty().bind(ellipsisRightLabel.visibleProperty());
        ellipsisRightLabel.visibleProperty().bind(toLastButton.visibleProperty());

        controlBox.getChildren().addAll(leftArrowButton, toFirstButton, ellipsisLeftLabel);
        controlBox.visibleProperty().bind(control.pageCountProperty().greaterThan(1));
        controlBox.managedProperty().bind(control.pageCountProperty().greaterThan(1));

        toggleGroup = new ToggleGroup();
        for (int i = 0; i < control.getMaxPageIndicatorCount() && i < control.getPageCount(); i++) {
            CustomToggleButton button = new CustomToggleButton();
            button.getStyleClass().add("number-button");
            toggleGroup.getToggles().add(button);
            numButtons.add(button);
            controlBox.getChildren().add(button);
        }
        controlBox.getChildren().addAll(ellipsisRightLabel, toLastButton, rightArrowButton);
        contentPane.setBottom(controlBox);

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

        BorderPane.setAlignment(controlBox, Pos.CENTER);
        contentPane.setCenter(pagination);

        getChildren().add(contentPane);

        updateControlBox();
        control.currentPageIndexProperty().addListener(it -> updateControlBox());
        control.pageCountProperty().addListener((ob, ov, nv) -> {
            changeIndexButtons();
            updateControlBox();
            control.setCurrentPageIndex(0);
        });
        control.maxPageIndicatorCountProperty().addListener((ob, ov, nv) -> {
            changeIndexButtons();

            updateControlBox();
        });
    }

    private void changeIndexButtons() {
        int newLen = Math.min(control.getMaxPageIndicatorCount(), control.getPageCount());
        int oldLen = numButtons.size();
        if (newLen > oldLen) {
            for (int i = oldLen; i < newLen; i++) {
                CustomToggleButton button = new CustomToggleButton("");
                button.getStyleClass().add("number-button");
                toggleGroup.getToggles().add(button);
                numButtons.add(button);
                controlBox.getChildren().add(oldLen + 3, button);
            }
        } else if (newLen < oldLen) {
            List<CustomToggleButton> tempList = new ArrayList<>();
            for (int i = oldLen - 1; i >= newLen; i--) {
                CustomToggleButton button = numButtons.get(i);
                tempList.add(button);
            }
            toggleGroup.getToggles().removeAll(tempList);
            numButtons.removeAll(tempList);
            controlBox.getChildren().removeAll(tempList);
        }
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
        int maxPageIndicatorCount = control.getMaxPageIndicatorCount();
        int pageCount = control.getPageCount();
        int currentPage = control.getCurrentPageIndex();

        toLastButton.setText(String.valueOf(pageCount));
        leftArrowButton.setDisable(currentPage == 0);
        rightArrowButton.setDisable(currentPage == pageCount - 1);

        int max = Math.min(maxPageIndicatorCount, pageCount);
        for (int i = 0; i < max; i++) {
            int num = computeToggleButtonNumber(i, pageCount, currentPage, maxPageIndicatorCount);
            CustomToggleButton numberToggleButton = numButtons.get(i);
            numberToggleButton.setText(String.valueOf(num + 1));
            numberToggleButton.setSelected(num == currentPage);
            numberToggleButton.setOnAction(e -> control.setCurrentPageIndex(num));
            if (i == 0) {
                toFirstButton.setVisible(num > 1);
            }
            if (i == max - 1) {
                toLastButton.setVisible(num < pageCount - 2);
            }
        }
    }
}
