package com.dlsc.jfxcentral2.mobile;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.Optional;

public class BottomMenuBar extends HBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public BottomMenuBar() {
        getStyleClass().add("bottom-menu-bar");

        CustomToggleButton homeButton = new CustomToggleButton();
        homeButton.setGraphic(new FontIcon(MaterialDesign.MDI_HOME));
        homeButton.setOnAction(e -> Optional.ofNullable(getOnHomeAction()).ifPresent(Runnable::run));

        CustomToggleButton newsButton = new CustomToggleButton();
        newsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class)));
        newsButton.setOnAction(e -> Optional.ofNullable(getOnNewsAction()).ifPresent(Runnable::run));

        CustomToggleButton videosButton = new CustomToggleButton();
        videosButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Video.class)));
        videosButton.setOnAction(e -> Optional.ofNullable(getOnVideosAction()).ifPresent(Runnable::run));

        CustomToggleButton showCaseButton = new CustomToggleButton();
        showCaseButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(RealWorldApp.class)));
        showCaseButton.setOnAction(e -> Optional.ofNullable(getOnShowCaseAction()).ifPresent(Runnable::run));

        // library button is not visible in small size.
        CustomToggleButton libraryButton = new CustomToggleButton();
        libraryButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Library.class)));
        libraryButton.managedProperty().bind(libraryButton.visibleProperty());
        libraryButton.visibleProperty().bind(sizeProperty().isNotEqualTo(Size.SMALL));
        libraryButton.setOnAction(e -> Optional.ofNullable(getOnLibraryAction()).ifPresent(Runnable::run));

        getChildren().addAll(homeButton, newsButton, videosButton, showCaseButton, libraryButton);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(homeButton, newsButton, videosButton, showCaseButton, libraryButton);

        setMaxHeight(Region.USE_PREF_SIZE);

        // select home button by default
        homeButton.fire();
    }

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    private final ObjectProperty<Runnable> onHomeAction = new SimpleObjectProperty<>(this, "onHomeAction");

    public final ObjectProperty<Runnable> onHomeActionProperty() {
        return onHomeAction;
    }

    public final Runnable getOnHomeAction() {
        return onHomeAction.get();
    }

    public final void setOnHomeAction(Runnable value) {
        onHomeAction.set(value);
    }

    private final ObjectProperty<Runnable> onNewsAction = new SimpleObjectProperty<>(this, "onNewsAction");

    public final ObjectProperty<Runnable> onNewsActionProperty() {
        return onNewsAction;
    }

    public final Runnable getOnNewsAction() {
        return onNewsAction.get();
    }

    public final void setOnNewsAction(Runnable value) {
        onNewsAction.set(value);
    }

    private final ObjectProperty<Runnable> onVideosAction = new SimpleObjectProperty<>(this, "onVideosAction");

    public final ObjectProperty<Runnable> onVideosActionProperty() {
        return onVideosAction;
    }

    public final Runnable getOnVideosAction() {
        return onVideosAction.get();
    }

    public final void setOnVideosAction(Runnable value) {
        onVideosAction.set(value);
    }

    private final ObjectProperty<Runnable> onLibraryAction = new SimpleObjectProperty<>(this, "onLibraryAction");

    public final ObjectProperty<Runnable> onLibraryActionProperty() {
        return onLibraryAction;
    }

    public final Runnable getOnLibraryAction() {
        return onLibraryAction.get();
    }

    public final void setOnLibraryAction(Runnable value) {
        onLibraryAction.set(value);
    }

    public final ObjectProperty<Runnable> onShowCaseAction = new SimpleObjectProperty<>(this, "onShowCaseAction");

    public final ObjectProperty<Runnable> onShowCaseActionProperty() {
        return onShowCaseAction;
    }

    public final Runnable getOnShowCaseAction() {
        return onShowCaseAction.get();
    }

    public final void setOnShowCaseAction(Runnable value) {
        onShowCaseAction.set(value);
    }

}
