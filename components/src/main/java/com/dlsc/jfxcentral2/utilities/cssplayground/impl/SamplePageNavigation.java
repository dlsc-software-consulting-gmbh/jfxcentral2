/*
 * Copyright (c) 2008, 2014, Oracle and/or its affiliates.
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

import com.dlsc.gemsfx.Spacer;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

import java.util.Comparator;

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
 *
 * Container for samplePage that has scrolling and knows how to navigate to sections
 */
public class SamplePageNavigation extends BorderPane {
    private SamplePage samplePage = new SamplePage();

    public SamplePageNavigation() {
        setCenter(samplePage);
        getStyleClass().add("sample-page");

        ToolBar toolBar = new ToolBar();
        toolBar.setId("SamplePageToolBar");
        toolBar.getStyleClass().add("bottom");
        toolBar.setPadding(new Insets(10));

        toolBar.getItems().addAll(new Spacer(), new Label("Go to section:"));

        SortedList<SamplePage.SectionItem> sortedList = new SortedList<>(samplePage.getSectionItems());
        sortedList.setComparator(Comparator.comparing(SamplePage.SectionItem::toString));

        ChoiceBox<SamplePage.SectionItem> sectionChoiceBox = new ChoiceBox<>();
        sectionChoiceBox.setItems(sortedList);

        sectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> samplePage.scrollTo(newValue));
        toolBar.getItems().add(sectionChoiceBox);
        setBottom(toolBar);

        ChoiceBox<SamplePage.SectionItem> topSectionChoiceBox = new ChoiceBox<>();
        topSectionChoiceBox.setItems(sortedList);
        topSectionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> samplePage.scrollTo(newValue));
        setTop(new ToolBar(new Label("Go to section:"),topSectionChoiceBox, new Spacer()));

        sectionChoiceBox.valueProperty().bindBidirectional(topSectionChoiceBox.valueProperty());

    }

    public SamplePage getSamplePage() {
        return samplePage;
    }
}
