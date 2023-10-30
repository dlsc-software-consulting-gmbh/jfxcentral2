/*
 * Copyright (c) 2008, 2017, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.dlsc.jfxcentral2.utilities.cssplayground.impl;

import com.dlsc.jfxcentral2.utilities.cssplayground.CssShowcaseView;
import com.dlsc.jfxcentral2.utilities.cssplayground.impl.component.CombinationTestView;
import com.dlsc.jfxcentral2.utilities.cssplayground.impl.component.SameHeightTestView;
import com.dlsc.jfxcentral2.utilities.cssplayground.impl.component.UiMosaicView;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.util.Map;
/**
 * This class is derived from the ShowcaseFX project, available at
 * <a href="https://github.com/dlsc-software-consulting-gmbh/ShowcaseFX">https://github.com/dlsc-software-consulting-gmbh/ShowcaseFX</a>.
 *
 * Modifications have been made to the original code. For specific licensing information,
 * please refer to the licensing section of the original
 * <a href="https://github.com/dlsc-software-consulting-gmbh/ShowcaseFX">ShowcaseFX project</a>.
 *
 * If you are interested in learning more about ShowcaseFX, it is recommended to visit the
 * original repository rather than relying on the modified version contained here.
 */

public class CssShowcaseViewSkin extends SkinBase<CssShowcaseView> {

    static {
        System.getProperties().put("javafx.pseudoClassOverrideEnabled", "true");
    }

    private BorderPane root;
    private SamplePageNavigation samplePageNavigation;
    private SamplePage samplePage;
    private SimpleWindowPage simpleWindows;
    private Node mosaic;
    private Node heightTest;
    private Node combinationsTest;
    private Node customerTest;

    private CheckComboBox<CssShowcaseView.CssConfiguration> stylesheetsBox;
    private CheckBox retinaButton, rtlButton;
    private Button clearButton;
    private TabPane contentTabs;
    private Pane contentGroup;

    private ListChangeListener<CssShowcaseView.CssConfiguration> checkComboBoxListener;
    private ListChangeListener<CssShowcaseView.CssConfiguration> observableListListener;

    public CssShowcaseViewSkin(CssShowcaseView view) {
        super(view);

        root = new BorderPane();

        Label dropLabel = new Label("Drop one or more CSS files ...");

        StackPane glasspane = new StackPane(dropLabel);
        glasspane.setMouseTransparent(true);
        glasspane.getStyleClass().add("showcase-glass-pane");

        StackPane wrapper = new StackPane(root, glasspane);

        getChildren().add(wrapper);

        view.getSelectedConfiguration().addListener((Observable it) -> updateStylesheets());
        view.additionalTabsProperty().addListener((Observable it) -> updateView());
        updateView();
        updateStylesheets();
    }

    private void updateStylesheets() {
        CssShowcaseView view = getSkinnable();

        if (contentGroup == null) {
            return;
        }

        contentGroup.getStylesheets().clear();

        ObservableList<CssShowcaseView.CssConfiguration> selectedConfiguration = view.getSelectedConfiguration();
        if (selectedConfiguration != null) {
            selectedConfiguration.forEach(it -> contentGroup.getStylesheets().addAll(it.getStylesheetUrls()));
        }
    }

    public Map<String, Node> getContent() {
        return samplePage.getContent();
    }

    public void setRetinaMode(boolean retinaMode) {
        if (retinaMode) {
            contentTabs.getTransforms().setAll(new Scale(2, 2));
        } else {
            contentTabs.getTransforms().setAll(new Scale(1, 1));
        }
        contentTabs.requestLayout();
    }

    private void updateView() {
        // Create sample page and nav
        samplePageNavigation = new SamplePageNavigation();
        samplePage = samplePageNavigation.getSamplePage();
        // Create Content Area
        contentTabs = new TabPane();

        Tab tab1 = new Tab("All Controls");
        tab1.setContent(samplePageNavigation);
        tab1.setClosable(false);

        Tab tab2 = new Tab("Mosaic");
        tab2.setContent(new ScrollPane(mosaic = new UiMosaicView()));
        tab2.setClosable(false);

        Tab tab3 = new Tab("Alignment");
        tab3.setContent(new ScrollPane(heightTest = new SameHeightTestView()));
        tab3.setClosable(false);

        Tab tab4 = new Tab("Windows");
        tab4.setContent(new ScrollPane(simpleWindows = new SimpleWindowPage()));
        tab4.setClosable(false);

        Tab tab5 = new Tab("Combinations");
        ScrollPane combinationsScrollPane = new ScrollPane(combinationsTest = new CombinationTestView());
        //combinationsScrollPane.setPrefHeight(1620);

        tab5.setContent(combinationsScrollPane);
        tab5.setClosable(false);

        contentTabs.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);
        contentTabs.getTabs().addAll(getSkinnable().getAdditionalTabs());

        // height test set selection for
        Platform.runLater(() -> {
            for (Node n : heightTest.lookupAll(".choice-box")) {
                ((ChoiceBox) n).getSelectionModel().selectFirst();
            }
            for (Node n : heightTest.lookupAll(".combo-box")) {
                ((ComboBox) n).getSelectionModel().selectFirst();
            }
        });

        // Create Toolbar
        retinaButton = new CheckBox("@2x");
        retinaButton.getStyleClass().add("retina-button");
        retinaButton.selectedProperty().addListener((observable, oldValue, newValue) -> setRetinaMode(newValue));


        stylesheetsBox = new CheckComboBox<>();
        stylesheetsBox.getStyleClass().add("check-combo-box");
        stylesheetsBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(CssShowcaseView.CssConfiguration entry) {
                if (entry != null) {
                    return entry.getName();
                }
                return "";
            }

            @Override
            public CssShowcaseView.CssConfiguration fromString(String s) {
                return null;
            }
        });

        Bindings.bindContent(stylesheetsBox.getItems(), getSkinnable().getConfigurations());

        observableListListener = change -> {
            stylesheetsBox.getCheckModel().getCheckedItems().removeListener(checkComboBoxListener);
            stylesheetsBox.getCheckModel().clearChecks();
            getSkinnable().getSelectedConfiguration().forEach(configuration -> stylesheetsBox.getCheckModel().check(configuration));
            stylesheetsBox.getCheckModel().getCheckedItems().addListener(checkComboBoxListener);
        };

        checkComboBoxListener = change -> {
            getSkinnable().getSelectedConfiguration().removeListener(observableListListener);
            getSkinnable().getSelectedConfiguration().setAll(stylesheetsBox.getCheckModel().getCheckedItems());
            getSkinnable().getSelectedConfiguration().addListener(observableListListener);
        };

        stylesheetsBox.getCheckModel().getCheckedItems().addListener(checkComboBoxListener);
        getSkinnable().getSelectedConfiguration().addListener(observableListListener);
        getSkinnable().getSelectedConfiguration().addListener(observableListListener);

        rtlButton = new CheckBox("RTL");
        rtlButton.getStyleClass().add("rtl-button");

        Label styleSheetLabel = new Label("Stylesheet:");
        styleSheetLabel.getStyleClass().add("stylesheet-label");

        clearButton = new Button("Clear Selections");
        clearButton.getStyleClass().add("clear-button");
        clearButton.setOnAction(event -> stylesheetsBox.getCheckModel().clearChecks());

        ToolBar toolBar = new ToolBar(styleSheetLabel,
                stylesheetsBox,
                clearButton,
                rtlButton,
                retinaButton
        );
        toolBar.getStyleClass().add("top-tool-bar");

        toolBar.setId("TestAppToolbar");
        // Create content group used for scaleing @2x
        contentGroup = new Pane() {
            @Override
            protected void layoutChildren() {
                double scale = contentTabs.getTransforms().isEmpty() ? 1 : ((Scale) contentTabs.getTransforms().get(0)).getX();
                contentTabs.resizeRelocate(0, 0, getWidth() / scale, getHeight() / scale);
            }
        };
        contentGroup.getChildren().add(contentTabs);
        contentGroup.getStyleClass().add("root");
        contentGroup.nodeOrientationProperty().bind(rtlButton.selectedProperty().map(it -> it ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT));


        // populate root
        root.setTop(toolBar);
        root.setCenter(contentGroup);

        samplePage.getStyleClass().add("needs-background");
        mosaic.getStyleClass().add("needs-background");
        heightTest.getStyleClass().add("needs-background");
        combinationsTest.getStyleClass().add("needs-background");
        simpleWindows.setModena(true);
    }
}
