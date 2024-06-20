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
import javafx.scene.layout.Priority;
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
        homeButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(homeButton, Priority.ALWAYS);
        LinkUtil.setLink(homeButton, PagePath.HOME);

        CustomToggleButton linksWeekButton = new CustomToggleButton();
        linksWeekButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class)));
        linksWeekButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(linksWeekButton, Priority.ALWAYS);
        LinkUtil.setLink(linksWeekButton, PagePath.LINKS);

        CustomToggleButton showCaseButton = new CustomToggleButton();
        showCaseButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(RealWorldApp.class)));
        showCaseButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(showCaseButton, Priority.ALWAYS);
        LinkUtil.setLink(showCaseButton, PagePath.SHOWCASES);

        CustomToggleButton libraryButton = new CustomToggleButton();
        libraryButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Library.class)));
        libraryButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(libraryButton, Priority.ALWAYS);
        LinkUtil.setLink(libraryButton, PagePath.LIBRARIES);

        CustomToggleButton peopleButton = new CustomToggleButton();
        peopleButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Person.class)));
        peopleButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(peopleButton, Priority.ALWAYS);
        LinkUtil.setLink(peopleButton, PagePath.PEOPLE);

        getChildren().addAll(homeButton, linksWeekButton, showCaseButton, libraryButton, peopleButton);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(homeButton, linksWeekButton, showCaseButton, libraryButton, peopleButton);

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
