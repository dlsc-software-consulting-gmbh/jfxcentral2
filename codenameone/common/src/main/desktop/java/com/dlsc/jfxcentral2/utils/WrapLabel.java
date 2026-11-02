package com.dlsc.jfxcentral2.utils;

import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * A label whose text wraps onto as many lines as its width needs.
 *
 * <p>Codename One 7.0.280's JavaFX layer draws a {@code Label} on one line
 * whatever {@code wrapText} says, while its {@code Text} wraps at a
 * {@code wrappingWidth}. This region draws through a {@code Text} and keeps a
 * hidden {@code Label} with the same style classes, so the application's
 * stylesheet, written for labels, still decides the font and the colour.
 * Rules that name this region directly ({@code .cell > .description}) do not
 * reach the hidden label, its child, so the text properties this region is
 * given are passed on to it.
 * Once the layer wraps labels itself, the uses can go back to
 * {@code Label} with {@code setWrapText(true)}.</p>
 */
public class WrapLabel extends Region {

    private final StyleSink styled = new StyleSink();
    private final Text text = new Text();
    private final Rectangle clip = new Rectangle();
    /// Answers the size queries, so that asking for a height at some width
    /// does not rewrap the text that is shown.
    private final Text measure = new Text();
    private int maxLines;

    public WrapLabel() {
        this(null);
    }

    public WrapLabel(String value) {
        getStyleClass().add("wrap-label");
        styled.setVisible(false);
        styled.setManaged(false);
        styled.textProperty().bind(text.textProperty());
        text.setManaged(false);
        text.setTextOrigin(VPos.TOP);
        // The hidden label carries the classes the stylesheet targets.
        getStyleClass().addListener((ListChangeListener<String>) c -> copyClasses());
        copyClasses();
        styled.fontProperty().addListener((obs, o, n) -> applyStyle());
        styled.textFillProperty().addListener((obs, o, n) -> applyStyle());
        text.textProperty().addListener((obs, o, n) -> requestLayout());
        getChildren().addAll(styled, text);
        setClip(clip);
        setText(value);
        applyStyle();
    }

    private void copyClasses() {
        styled.getStyleClass().setAll("label");
        for (String c : getStyleClass()) {
            if (!"wrap-label".equals(c)) {
                styled.getStyleClass().add(c);
            }
        }
    }

    private void applyStyle() {
        if (styled.getFont() != null) {
            text.setFont(styled.getFont());
        }
        if (styled.getTextFill() != null) {
            text.setFill(styled.getTextFill());
        }
        requestLayout();
    }

    private static boolean textProperty(String property) {
        return "-fx-text-fill".equals(property) || property.startsWith("-fx-font");
    }

    @Override
    protected Object cn1StyleValue(String property) {
        return textProperty(property) ? styled.value(property) : super.cn1StyleValue(property);
    }

    @Override
    protected boolean cn1SetStyleValue(String property, Object value) {
        return textProperty(property) ? styled.apply(property, value) : super.cn1SetStyleValue(property, value);
    }

    /// The hidden label, opened up so the region can hand it a style value.
    private static final class StyleSink extends Label {
        Object value(String property) {
            return cn1StyleValue(property);
        }

        boolean apply(String property, Object value) {
            return cn1SetStyleValue(property, value);
        }
    }

    public final StringProperty textProperty() {
        return text.textProperty();
    }

    public final String getText() {
        return text.getText();
    }

    public final void setText(String value) {
        text.setText(value);
    }

    @Override
    public Orientation getContentBias() {
        return Orientation.HORIZONTAL;
    }

    @Override
    protected double computeMinWidth(double height) {
        Insets in = getInsets();
        return in.getLeft() + in.getRight();
    }

    private Text measured(double wrappingWidth) {
        measure.setFont(text.getFont());
        measure.setText(text.getText());
        measure.setWrappingWidth(wrappingWidth);
        return measure;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets in = getInsets();
        return in.getLeft() + in.getRight() + measured(0).getLayoutBounds().getWidth();
    }

    /// Shows at most this many lines, cutting off the rest; 0 shows all.
    public final void setMaxLines(int lines) {
        maxLines = lines;
        requestLayout();
    }

    /// The width the text wraps at inside `inner`; with none, it is not
    /// wrapped.
    private double wrapAt(double inner) {
        return inner <= 0 ? 0 : Math.max(1, inner);
    }

    private final Text line = new Text("X");

    private double lineHeight() {
        if (line.getFont() != text.getFont()) {
            line.setFont(text.getFont());
        }
        return line.getLayoutBounds().getHeight();
    }

    private double textHeight(Text t) {
        double h = t.getLayoutBounds().getHeight();
        if (maxLines > 0) {
            h = Math.min(h, lineHeight() * maxLines);
        }
        return h;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets in = getInsets();
        // With no width given, the text is measured unwrapped.
        double inner = width < 0 ? 0 : wrapAt(width - in.getLeft() - in.getRight());
        return in.getTop() + in.getBottom() + textHeight(measured(inner));
    }

    @Override
    protected double computeMinHeight(double width) {
        return computePrefHeight(width);
    }

    @Override
    protected void layoutChildren() {
        Insets in = getInsets();
        text.setWrappingWidth(wrapAt(getWidth() - in.getLeft() - in.getRight()));
        text.relocate(in.getLeft(), in.getTop());
        clip.setWidth(getWidth());
        clip.setHeight(Math.min(getHeight(), in.getTop() + textHeight(text) + in.getBottom()));
    }
}
