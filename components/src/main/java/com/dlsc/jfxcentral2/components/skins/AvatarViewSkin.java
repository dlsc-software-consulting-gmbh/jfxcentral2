package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.AvatarView;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class AvatarViewSkin extends SkinBase<AvatarView> {

    private final ImageView imageView = new ImageView();
    private final AvatarView control;
    private final StackPane wrapper;

    public AvatarViewSkin(AvatarView control) {
        super(control);
        this.control = control;

        wrapper = new StackPane();

        imageView.imageProperty().bind(control.imageProperty());
        imageView.smoothProperty().bind(control.smoothProperty());
        imageView.setPreserveRatio(true);
        imageView.clipProperty().bind(Bindings.createObjectBinding(this::createClip, control.typeProperty()));
        imageView.imageProperty().addListener((ob, ov, nv) -> {
            if (ov != null) {
                ov.progressProperty().removeListener(progressChangeListener);
            }
            if (nv != null) {
                if (control.getImage().getProgress() == 1) {
                    imageView.setClip(createClip());
                } else {
                    nv.progressProperty().addListener(progressChangeListener);
                }
            }
        });

        control.sizeProperty().addListener((ob, ov, nv) -> imageView.setClip(createClip()));
        control.avatarSizeProperty().addListener((ob, ov, nv) -> imageView.setClip(createClip()));
        control.roundSizeProperty().addListener((ob, ov, nv) -> imageView.setClip(createClip()));
        control.typeProperty().addListener((ob, ov, nv) -> imageView.setClip(createClip()));

        wrapper.getChildren().setAll(new Group(imageView));
        wrapper.prefWidthProperty().bind(control.avatarSizeProperty());
        wrapper.prefHeightProperty().bind(control.avatarSizeProperty());

        getChildren().setAll(wrapper);
    }

    private Shape createClip() {
        Image image = control.getImage();
        imageView.setFitHeight(-1);
        imageView.setFitWidth(-1);
        if (image == null) {
            return null;
        }
        double width = image.getWidth();
        double height = image.getHeight();
        boolean isWidthGreaterThanHeight = width > height;
        double avatarSize = control.getAvatarSize();
        double scale = isWidthGreaterThanHeight ? avatarSize / height : avatarSize / width;
        if (isWidthGreaterThanHeight) {
            imageView.setFitHeight(avatarSize);
        } else {
            imageView.setFitWidth(avatarSize);
        }

        if (control.getType() == AvatarView.Type.SQUARE) {
            Rectangle rectangle = new Rectangle();
            rectangle.widthProperty().bind(control.avatarSizeProperty());
            rectangle.heightProperty().bind(control.avatarSizeProperty());
            rectangle.arcWidthProperty().bind(control.roundSizeProperty());
            rectangle.arcHeightProperty().bind(control.roundSizeProperty());

            if (isWidthGreaterThanHeight) {
                rectangle.setX((width * scale - avatarSize) / 2);
            } else {
                rectangle.setY((height * scale - avatarSize) / 2);
            }
            return rectangle;
        } else if (control.getType().equals(AvatarView.Type.CIRCLE)) {
            Circle circle = new Circle();
            circle.radiusProperty().bind(control.avatarSizeProperty().divide(2));
            if (isWidthGreaterThanHeight) {
                circle.setCenterX(width * scale / 2);
                circle.setCenterY(avatarSize / 2);
            } else {
                circle.setCenterX(avatarSize / 2);
                circle.setCenterY(height * scale / 2);
            }
            return circle;
        }

        return null;
    }

    private final ChangeListener<Number> progressChangeListener = (ob, ov, nv) -> {
        if (nv.intValue() == 1) {
            imageView.setClip(createClip());
        }
    };

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return wrapper.prefWidth(-1) + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return wrapper.prefHeight(-1) + topInset + bottomInset;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }
}
