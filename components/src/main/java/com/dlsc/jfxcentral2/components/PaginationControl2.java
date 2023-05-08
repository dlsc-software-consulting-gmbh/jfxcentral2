package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.PaginationControlSkin2;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.Callback;

public class PaginationControl2 extends Control {

    private static final int DEFAULT_PAGE_COUNT = 10;
    private static final int DEFAULT_CURRENT_PAGE_INDEX = 0;
    private static final int DEFAULT_MAX_PAGE_INDICATOR_COUNT = 3;

    public PaginationControl2() {
        getStyleClass().add("custom-pagination-control2");
    }

    public PaginationControl2(int pageCount, int pageIndex) {
        this();
        setPageCount(pageCount);
        setCurrentPageIndex(pageIndex);
    }

    public PaginationControl2(int pageCount, int pageIndex, int maxPageIndicatorCount) {
        this(pageCount, pageIndex);
        setMaxPageIndicatorCount(maxPageIndicatorCount);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new PaginationControlSkin2(this);
    }

    private final IntegerProperty pageCount = new SimpleIntegerProperty(this, "pageCount", DEFAULT_PAGE_COUNT);

    public int getPageCount() {
        return pageCount.get();
    }

    public IntegerProperty pageCountProperty() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount.set(pageCount);
    }

    private final IntegerProperty currentPageIndex = new SimpleIntegerProperty(this, "currentPageIndex", DEFAULT_CURRENT_PAGE_INDEX);

    public int getCurrentPageIndex() {
        return currentPageIndex.get();
    }

    public IntegerProperty currentPageIndexProperty() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex.set(currentPageIndex);
    }

    private final ObjectProperty<Callback<Integer, Node>> pageFactory = new SimpleObjectProperty<>(this, "pageFactory");

    public Callback<Integer, Node> getPageFactory() {
        return pageFactory.get();
    }

    public ObjectProperty<Callback<Integer, Node>> pageFactoryProperty() {
        return pageFactory;
    }

    public void setPageFactory(Callback<Integer, Node> pageFactory) {
        this.pageFactory.set(pageFactory);
    }

    private final IntegerProperty maxPageIndicatorCount = new SimpleIntegerProperty(this, "maxPageIndicatorCount", DEFAULT_MAX_PAGE_INDICATOR_COUNT);

    public int getMaxPageIndicatorCount() {
        return maxPageIndicatorCount.get();
    }

    public IntegerProperty maxPageIndicatorCountProperty() {
        return maxPageIndicatorCount;
    }

    public void setMaxPageIndicatorCount(int maxPageIndicatorCount) {
        this.maxPageIndicatorCount.set(maxPageIndicatorCount);
    }
}