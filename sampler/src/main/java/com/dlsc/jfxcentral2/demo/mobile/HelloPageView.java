package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.components.PageView;
import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class HelloPageView extends JFXCentralSampleBase {

    private static final String[] WEB_COLORS = new String[]{"#8D33FF", "#9c27b0", "#3f51b5", "#2196f3", "#009688"};

    private PageView pageView;

    @Override
    protected Region createControl() {
        pageView = new PageView();
        pageView.setPageCount(5);
        pageView.setPageFactory(index -> {
            Label text = new Label(String.valueOf(index + 1));
            text.setAlignment(Pos.CENTER);
            text.setFont(Font.font(60));
            text.setTextFill(Color.WHITE);
            text.setPrefSize(80, 80);
            text.setStyle("-fx-background-color: lightgrey; -fx-background-radius: 50%;");
            StackPane pane = new StackPane(text);
            pane.setStyle("-fx-background-color: " + WEB_COLORS[index % WEB_COLORS.length] + ";");
            return pane;
        });

        // MarkdownView markdownView = new MarkdownView();
        // markdownView.setMdString("""
        //         ### PageView Usage
        //         > For horizontal swiping, the usage of PageView is as follows:
        //         1. `<<<<<` Drag from right to left will switch to the previous page.
        //         2. `>>>>>` Drag from left to right will switch to the next page.
        //         3. If the current page is the first page, releasing after swiping right will automatically return to the first page.
        //         4. If the current page is the last page, releasing after swiping left will automatically return to the last page.
        //         """);

        return pageView;
    }

    @Override
    public Node getControlPanel() {
        Spinner<Integer> switchDurationSpinner = new Spinner<>(50, 350, 200, 50);
        pageView.switchPageDurationProperty().bind(Bindings.createObjectBinding(() -> new Duration(switchDurationSpinner.getValue()), switchDurationSpinner.valueProperty()));

        Spinner<Integer> revertDurationSpinner = new Spinner<>(50, 350, 150, 50);
        pageView.revertPageDurationProperty().bind(Bindings.createObjectBinding(() -> new Duration(revertDurationSpinner.getValue()), revertDurationSpinner.valueProperty()));

        Slider thresholdSlider = new Slider(0, 1, pageView.getPageSwitchMinRatio());
        pageView.pageSwitchMinRatioProperty().bind(thresholdSlider.valueProperty());

        Slider edgeThresholdSlider = new Slider(0, 1, pageView.getEdgeSwipeMaxRatio());
        pageView.edgeSwipeMaxRatioProperty().bind(edgeThresholdSlider.valueProperty());

        ComboBox<Orientation> swipeDirectionComboBox = new ComboBox<>();
        swipeDirectionComboBox.getItems().addAll(Orientation.HORIZONTAL, Orientation.VERTICAL);
        swipeDirectionComboBox.setValue(Orientation.HORIZONTAL);
        pageView.swipeOrientationProperty().bind(swipeDirectionComboBox.valueProperty());

        ComboBox<Number> currentIndexComboBox = new ComboBox<>();
        currentIndexComboBox.getItems().addAll(0, 1, 2, 3, 4);
        pageView.currentPageIndexProperty().bindBidirectional(currentIndexComboBox.valueProperty());

        Button nextPageButton = new Button(">");
        nextPageButton.setOnAction(e -> pageView.gotoNextPage());

        Button prevPageButton = new Button("<");
        prevPageButton.setOnAction(e -> pageView.gotoPreviousPage());

        HBox buttonBox = new HBox(prevPageButton, nextPageButton);

        return createSimpleControlPanel(
                createControlCell("Switch Duration", switchDurationSpinner),
                createControlCell("Revert Duration", revertDurationSpinner),
                createControlCell("Switch Threshold", thresholdSlider),
                createControlCell("Edge Threshold", edgeThresholdSlider),
                createControlCell("Swipe Direction", swipeDirectionComboBox),
                createControlCell("Change Page Index", currentIndexComboBox),
                createControlCell("Navigation", buttonBox)
        );
    }

    @Override
    public String getSampleName() {
        return "PageView";
    }

}
