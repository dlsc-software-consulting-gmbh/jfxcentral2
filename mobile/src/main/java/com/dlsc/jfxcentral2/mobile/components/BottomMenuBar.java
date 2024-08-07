package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.events.MobileLinkEvent;
import com.dlsc.jfxcentral2.mobile.pages.MobileHomePage;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.EventBusUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import com.dlsc.jfxcentral2.utils.Subscribe;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class BottomMenuBar extends HBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);
    private final ToggleGroup toggleGroup;

    public BottomMenuBar() {
        getStyleClass().add("bottom-menu-bar");
        EventBusUtil.register(this);

        CustomToggleButton homeButton = new CustomToggleButton();
        homeButton.setText("Home");
        homeButton.setGraphic(new FontIcon(MaterialDesign.MDI_HOME));
        homeButton.setMaxWidth(Double.MAX_VALUE);
        homeButton.setUserData(PagePath.HOME);
        homeButton.setOnMousePressed(evt -> {
            if (homeButton.isSelected()) {
                // If the home page is being displayed, clicking homeButton will hide the search view and display the normal content.
                MobileHomePage.getInstance().setContentType(MobileHomePage.ContentType.NORMAL);
            } else {
                MobileLinkUtil.getToPage(PagePath.HOME);
            }
        });
        HBox.setHgrow(homeButton, Priority.ALWAYS);

        CustomToggleButton linksWeekButton = new CustomToggleButton();
        linksWeekButton.setText("News");
        linksWeekButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class)));
        linksWeekButton.setMaxWidth(Double.MAX_VALUE);
        linksWeekButton.setUserData(PagePath.LINKS);
        MobileLinkUtil.setLink(linksWeekButton, PagePath.LINKS);
        HBox.setHgrow(linksWeekButton, Priority.ALWAYS);

        CustomToggleButton showcasesButton = new CustomToggleButton();
        showcasesButton.setText("Apps");
        showcasesButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(RealWorldApp.class)));
        showcasesButton.setMaxWidth(Double.MAX_VALUE);
        showcasesButton.setUserData(PagePath.SHOWCASES);
        MobileLinkUtil.setLink(showcasesButton, PagePath.SHOWCASES);
        HBox.setHgrow(showcasesButton, Priority.ALWAYS);

        CustomToggleButton libraryButton = new CustomToggleButton();
        libraryButton.setText("Libs");
        libraryButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Library.class)));
        libraryButton.setMaxWidth(Double.MAX_VALUE);
        libraryButton.setUserData(PagePath.LIBRARIES);
        MobileLinkUtil.setLink(libraryButton, PagePath.LIBRARIES);
        HBox.setHgrow(libraryButton, Priority.ALWAYS);

        CustomToggleButton peopleButton = new CustomToggleButton();
        peopleButton.setText("People");
        peopleButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Person.class)));
        peopleButton.setMaxWidth(Double.MAX_VALUE);
        peopleButton.setUserData(PagePath.PEOPLE);
        MobileLinkUtil.setLink(peopleButton, PagePath.PEOPLE);
        HBox.setHgrow(peopleButton, Priority.ALWAYS);

        getChildren().addAll(homeButton, linksWeekButton, showcasesButton, libraryButton, peopleButton);

        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(homeButton, linksWeekButton, showcasesButton, libraryButton, peopleButton);

        setMaxHeight(Region.USE_PREF_SIZE);

        // select home button by default
        // homeButton.fire();
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

    @Subscribe
    public void handleNavigation(MobileLinkEvent linkEvent) {
        toggleGroup.getToggles().stream()
                .filter(toggle -> toggle.getUserData().equals(linkEvent.link()))
                .findFirst()
                .ifPresentOrElse(
                        toggle -> toggle.setSelected(true),
                        () -> toggleGroup.selectToggle(null)
                );
    }

}