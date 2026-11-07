package com.dlsc.jfxcentral2.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import com.dlsc.jfxcentral2.components.skins.AvatarViewSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import com.dlsc.jfxcentral2.utils.skin.Skin;
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
        SQUARE,
        PLAIN
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

    private final ObjectProperty<Type> type = new SimpleObjectProperty<>(this, "type", DEFAULT_TYPE);

    public Type getType() {
        return type.get();
    }

    public ObjectProperty<Type> typeProperty() {
        return type;
    }

    public void setType(Type type) {
        this.type.set(type);
    }

    private final DoubleProperty avatarSize = new SimpleDoubleProperty(this, "avatarSize", DEFAULT_AVATAR_SIZE);

    public double getAvatarSize() {
        return avatarSize.get();
    }

    public DoubleProperty avatarSizeProperty() {
        return avatarSize;
    }

    public void setAvatarSize(double avatarSize) {
        this.avatarSize.set(avatarSize);
    }

    private final DoubleProperty roundSize = new SimpleDoubleProperty(this, "roundSize", DEFAULT_ROUND_SIZE);

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

    private final BooleanProperty smooth = new SimpleBooleanProperty(this, "smooth", DEFAULT_SMOOTH);

    public boolean isSmooth() {
        return smooth.get();
    }

    public BooleanProperty smoothProperty() {
        return smooth;
    }

    public void setSmooth(boolean smooth) {
        this.smooth.set(smooth);
    }


}
