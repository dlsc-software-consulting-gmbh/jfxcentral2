package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.PaginationControlSkin;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class PaginationControl extends ControlBase {

    private static final String DEFAULT_EMPTY_TEXT = "No content to display";

    public PaginationControl() {
        getStyleClass().add("pagination-control");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new PaginationControlSkin(this);
    }
    
    private final ObjectProperty<Node> placeholder = new SimpleObjectProperty<>(this, "placeholder", createDefaultPlaceholder());

    public Node getPlaceholder() {
        return placeholder.get();
    }

    public ObjectProperty<Node> placeholderProperty() {
        return placeholder;
    }

    public void setPlaceholder(Node placeholder) {
        this.placeholder.set(placeholder);
    }

    private Node createDefaultPlaceholder() {
        Label label = new Label(DEFAULT_EMPTY_TEXT, new FontIcon(MaterialDesign.MDI_ALERT));
        label.getStyleClass().add("default-placeholder");
        return label;
    }

    private final StringProperty separatorText = new SimpleStringProperty(this, "separatorText", " OF ");

    public String getSeparatorText() {
        return separatorText.get();
    }

    public StringProperty separatorTextProperty() {
        return separatorText;
    }

    public void setSeparatorText(String separatorText) {
        this.separatorText.set(separatorText);
    }

    private final IntegerProperty pageCount = new SimpleIntegerProperty(this, "pageCount", 0);

    public int getPageCount() {
        return pageCount.get();
    }

    public IntegerProperty pageCountProperty() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount.set(pageCount);
    }

    private final IntegerProperty currentPageIndex = new SimpleIntegerProperty(this, "currentPageIndex");

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
}
