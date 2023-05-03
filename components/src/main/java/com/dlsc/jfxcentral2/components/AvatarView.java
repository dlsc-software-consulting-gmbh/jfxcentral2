package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.AvatarViewSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.BooleanConverter;
import javafx.css.converter.EnumConverter;
import javafx.css.converter.SizeConverter;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvatarView extends ControlBase {
    private static final Type DEFAULT_TYPE = Type.CIRCLE;
    private static final double DEFAULT_AVATAR_SIZE = 100;
    private static final double DEFAULT_ROUND_SIZE = 5;
    private static final boolean DEFAULT_SMOOTH = true;

    public enum Type {
        CIRCLE,
        SQUARE
    }

    public AvatarView(Image image) {
        this();
        setImage(image);
    }

    public AvatarView(String imageUrl) {
        this(new Image(imageUrl, true));
    }

    public AvatarView() {
        getStyleClass().add("avatar-view");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AvatarViewSkin(this);
    }

    private final StyleableObjectProperty<Type> type = new StyleableObjectProperty<>(DEFAULT_TYPE) {
        @Override
        public CssMetaData<? extends Styleable, Type> getCssMetaData() {
            return StyleableProperties.TYPE;
        }

        @Override
        public Object getBean() {
            return AvatarView.this;
        }

        @Override
        public String getName() {
            return "type";
        }

    };

    public Type getType() {
        return type.get();
    }

    public StyleableObjectProperty<Type> typeProperty() {
        return type;
    }

    public void setType(Type type) {
        this.type.set(type);
    }

    private final DoubleProperty avatarSize = new StyleableDoubleProperty(DEFAULT_AVATAR_SIZE) {
        @Override
        public Object getBean() {
            return AvatarView.this;
        }

        @Override
        public String getName() {
            return "avatarSize";
        }

        @Override
        public CssMetaData<? extends Styleable, Number> getCssMetaData() {
            return StyleableProperties.AVATAR_SIZE;
        }
    };

    public double getAvatarSize() {
        return avatarSize.get();
    }

    public DoubleProperty avatarSizeProperty() {
        return avatarSize;
    }

    public void setAvatarSize(double avatarSize) {
        this.avatarSize.set(avatarSize);
    }

    private final DoubleProperty roundSize = new StyleableDoubleProperty(DEFAULT_ROUND_SIZE) {
        @Override
        public Object getBean() {
            return AvatarView.this;
        }

        @Override
        public String getName() {
            return "roundSize";
        }

        @Override
        public CssMetaData<? extends Styleable, Number> getCssMetaData() {
            return StyleableProperties.ROUND_SIZE;
        }
    };

    public double getRoundSize() {
        return roundSize.get();
    }

    public DoubleProperty roundSizeProperty() {
        return roundSize;
    }

    public void setRoundSize(double roundSize) {
        this.roundSize.set(roundSize);
    }

    private final ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    private final BooleanProperty smooth = new StyleableBooleanProperty(DEFAULT_SMOOTH) {
        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "smooth";
        }

        @Override
        public CssMetaData<? extends Styleable, Boolean> getCssMetaData() {
            return StyleableProperties.SMOOTH;
        }
    };

    public boolean isSmooth() {
        return smooth.get();
    }

    public BooleanProperty smoothProperty() {
        return smooth;
    }

    public void setSmooth(boolean smooth) {
        this.smooth.set(smooth);
    }

    private static class StyleableProperties {

        private static final CssMetaData<AvatarView, Number> AVATAR_SIZE =
                new CssMetaData<>("-fx-avatar-size",
                        SizeConverter.getInstance(), DEFAULT_AVATAR_SIZE) {
                    @Override
                    public boolean isSettable(AvatarView n) {
                        return !n.avatarSize.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(AvatarView n) {
                        return (StyleableProperty<Number>) n.avatarSizeProperty();
                    }
                };

        private static final CssMetaData<AvatarView, Type> TYPE =
                new CssMetaData<>("-fx-avatar-type",
                        new EnumConverter<>(Type.class), DEFAULT_TYPE) {
                    @Override
                    public boolean isSettable(AvatarView control) {
                        return !control.type.isBound();
                    }

                    @Override
                    public StyleableProperty<Type> getStyleableProperty(AvatarView control) {
                        return (StyleableProperty<Type>) control.typeProperty();
                    }
                };

        private static final CssMetaData<AvatarView, Number> ROUND_SIZE =
                new CssMetaData<>("-fx-round-size",
                        SizeConverter.getInstance(), DEFAULT_ROUND_SIZE) {
                    @Override
                    public boolean isSettable(AvatarView n) {
                        return !n.roundSize.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(AvatarView n) {
                        return (StyleableProperty<Number>) n.roundSizeProperty();
                    }
                };

        private static final CssMetaData<AvatarView, Boolean> SMOOTH = new CssMetaData<>(
                "-fx-smooth", BooleanConverter.getInstance(), DEFAULT_SMOOTH) {

            @Override
            public boolean isSettable(AvatarView control) {
                return !control.smooth.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(AvatarView control) {
                return (StyleableProperty<Boolean>) control.smoothProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(ControlBase.getClassCssMetaData());
            Collections.addAll(styleables, AVATAR_SIZE, ROUND_SIZE, TYPE, SMOOTH);
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
