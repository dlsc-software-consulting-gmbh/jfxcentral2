package com.dlsc.jfxcentral2.mobile;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class BottomMenuBar extends HBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public BottomMenuBar() {
        getStyleClass().add("bottom-menu-bar");

        CustomToggleButton homeButton = new CustomToggleButton();
        homeButton.setGraphic(new FontIcon(MaterialDesign.MDI_HOME));
        LinkUtil.setLink(homeButton, PagePath.HOME);

        CustomToggleButton linksWeekButton = new CustomToggleButton();
        linksWeekButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class)));
        LinkUtil.setLink(linksWeekButton, PagePath.LINKS);

        CustomToggleButton showCaseButton = new CustomToggleButton();
        showCaseButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(RealWorldApp.class)));
        LinkUtil.setLink(showCaseButton, PagePath.SHOWCASES);

        CustomToggleButton libraryButton = new CustomToggleButton();
        libraryButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Library.class)));
        LinkUtil.setLink(libraryButton, PagePath.LIBRARIES);

        // people category is hidden when size is small
        CustomToggleButton peopleButton = new CustomToggleButton();
        peopleButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Person.class)));
        peopleButton.managedProperty().bind(peopleButton.visibleProperty());
        peopleButton.visibleProperty().bind(sizeProperty().isNotEqualTo(Size.SMALL));
        LinkUtil.setLink(peopleButton, PagePath.PEOPLE);

        getChildren().addAll(homeButton, linksWeekButton, showCaseButton, libraryButton);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(homeButton, linksWeekButton, showCaseButton, libraryButton);

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

}
