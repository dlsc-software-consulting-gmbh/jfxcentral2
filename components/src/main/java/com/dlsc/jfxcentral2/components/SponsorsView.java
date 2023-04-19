package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.SponsorsSkin;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.BooleanConverter;
import javafx.css.converter.SizeConverter;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SponsorsView extends ControlBase {

    private static final String DEFAULT_STYLE_CLASS = "sponsors-view";
    private static final String DEFAULT_TITLE = "OUR PARTNERS";
    private static final int DEFAULT_LOGO_FIT_HEIGHT = 35;
    private static final int DEFAULT_LOGO_FIT_WIDTH = 200;


    public SponsorsView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        itemsProperty().setAll(createSponsors());
    }

    private List<SponsorsView.Sponsor> createSponsors() {
        return List.of(
                new SponsorsView.Sponsor("JPRO",
                        getClass().getResource("logos/jpro.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new SponsorsView.Sponsor("SANDEC",
                        getClass().getResource("logos/sandec.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new SponsorsView.Sponsor("DLSC",
                        getClass().getResource("logos/dlsc.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new SponsorsView.Sponsor("NAVIELEKTRO",
                        getClass().getResource("logos/navielektro.png").toExternalForm(),
                        "https://www.jpro.one/"),
                new SponsorsView.Sponsor(
                        "Hydraulic",
                        getClass().getResource("logos/hydraulic.png").toExternalForm(),
                        "https://www.jpro.one/"));
    }

    public record Sponsor(String name, String logoUrl, String url) {
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SponsorsSkin(this);
    }

    private final IntegerProperty showLogoCount = new SimpleIntegerProperty(this, "showCount", 5);

    public int getShowLogoCount() {
        return showLogoCount.get();
    }

    public IntegerProperty showLogoCountProperty() {
        return showLogoCount;
    }

    public void setShowLogoCount(int showLogoCount) {
        this.showLogoCount.set(showLogoCount);
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", DEFAULT_TITLE);

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    private final ListProperty<Sponsor> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ObservableList<Sponsor> getItems() {
        return items.get();
    }

    public ListProperty<Sponsor> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<Sponsor> items) {
        this.items.set(items);
    }

    private final StyleableBooleanProperty dividerVisible = new SimpleStyleableBooleanProperty(StyleableProperties.DIVIDER_VISIBLE, SponsorsView.this,
            "dividerVisible", true);

    public boolean isDividerVisible() {
        return dividerVisible.get();
    }

    public StyleableBooleanProperty dividerVisibleProperty() {
        return dividerVisible;
    }

    public void setDividerVisible(boolean dividerVisible) {
        this.dividerVisible.set(dividerVisible);
    }

    private final DoubleProperty logoFitHeight = new StyleableDoubleProperty(DEFAULT_LOGO_FIT_HEIGHT) {
        @Override
        public void invalidated() {
            requestLayout();
        }

        @Override
        public CssMetaData<SponsorsView, Number> getCssMetaData() {
            return StyleableProperties.LOGO_FIT_HEIGHT;
        }

        @Override
        public Object getBean() {
            return SponsorsView.this;
        }

        @Override
        public String getName() {
            return "logoFitHeight";
        }
    };

    public final DoubleProperty logoFitHeightProperty() {
        return logoFitHeight;
    }

    public double getLogoFitHeight() {
        return logoFitHeight.get();
    }

    public void setLogoFitHeight(double logoFitHeight) {
        this.logoFitHeight.set(logoFitHeight);
    }


    private final DoubleProperty logoFitWidth = new StyleableDoubleProperty(DEFAULT_LOGO_FIT_WIDTH) {
        @Override
        public void invalidated() {
            requestLayout();
        }

        @Override
        public CssMetaData<SponsorsView, Number> getCssMetaData() {
            return StyleableProperties.LOGO_FIT_WIDTH;
        }

        @Override
        public Object getBean() {
            return SponsorsView.this;
        }

        @Override
        public String getName() {
            return "logoFitWidth";
        }
    };

    public final double getLogoFitWidth() {
        return logoFitWidth.get();
    }

    public final void setLogoFitWidth(double logoFitWidth) {
        this.logoFitWidth.set(logoFitWidth);
    }

    public final DoubleProperty logoFitWidthProperty() {
        return logoFitWidth;
    }


    private static class StyleableProperties {

        private static final CssMetaData<SponsorsView, Boolean> DIVIDER_VISIBLE = new CssMetaData<>(
                "-fx-divider-visible", BooleanConverter.getInstance(), true) {

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(SponsorsView control) {
                return control.dividerVisibleProperty();
            }

            @Override
            public boolean isSettable(SponsorsView control) {
                return !control.dividerVisible.isBound();
            }
        };

        private static final CssMetaData<SponsorsView, Number> LOGO_FIT_HEIGHT =
                new CssMetaData<>("-fx-logo-fit-height", SizeConverter.getInstance(), DEFAULT_LOGO_FIT_HEIGHT) {
                    @Override
                    public boolean isSettable(SponsorsView node) {
                        return !node.logoFitHeight.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(SponsorsView node) {
                        return (StyleableProperty<Number>) node.logoFitHeightProperty();
                    }
                };

        private static final CssMetaData<SponsorsView, Number> LOGO_FIT_WIDTH =
                new CssMetaData<>("-fx-logo-fit-width", SizeConverter.getInstance(), DEFAULT_LOGO_FIT_WIDTH) {
                    @Override
                    public boolean isSettable(SponsorsView node) {
                        return !node.logoFitWidth.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(SponsorsView node) {
                        return (StyleableProperty<Number>) node.logoFitWidthProperty();
                    }
                };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
            Collections.addAll(styleables, DIVIDER_VISIBLE, LOGO_FIT_HEIGHT, LOGO_FIT_WIDTH);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    protected List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

}
