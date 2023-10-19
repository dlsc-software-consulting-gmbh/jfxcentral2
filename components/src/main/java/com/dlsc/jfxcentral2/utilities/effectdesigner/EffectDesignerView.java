package com.dlsc.jfxcentral2.utilities.effectdesigner;

import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.BlendBottomInputEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.BlendTopInputEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.BloomEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.BoxBlurEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.ColorAdjustEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.ColorInputEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.DisplacementMapEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.DropShadowEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.GaussianBlurEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.GlowEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.ImageInputEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.InnerShadowEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.LightingBumpInputEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.LightingContentInputEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.MotionBlurEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.PerspectiveTransformEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.ReflectionEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.SepiaToneEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.editor.ShadowEditor;
import com.dlsc.jfxcentral2.utilities.effectdesigner.effect.BlendBottomInput;
import com.dlsc.jfxcentral2.utilities.effectdesigner.effect.BlendTopInput;
import com.dlsc.jfxcentral2.utilities.effectdesigner.effect.LightingBumpInput;
import com.dlsc.jfxcentral2.utilities.effectdesigner.effect.LightingContentInput;
import javafx.beans.InvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EffectDesignerView extends PaneBase {

    public EffectDesignerView() {
        getStyleClass().add("effect-designer-view");
        setAlignment(Pos.TOP_LEFT);

        StackPane previewPane = new StackPane();
        previewPane.getStyleClass().add("effect-preview-pane");
        previewPane.getChildren().add(new Label("Preview Pane"));

        EffectFlowPane effectFlowPane = new EffectFlowPane();
        previewPane.effectProperty().bind(effectFlowPane.valueProperty());

        StackPane effectPaneWrapper = new StackPane();
        effectPaneWrapper.getStyleClass().add("effect-pane-wrapper");
        effectPaneWrapper.managedProperty().bind(effectPaneWrapper.visibleProperty());
        effectPaneWrapper.visibleProperty().bind(effectFlowPane.selectedEffectProperty().isNotNull());

        effectFlowPane.selectedEffectProperty().addListener((ob, ov, newEffect) -> {
            if (newEffect == null) {
                effectPaneWrapper.getChildren().clear();
            }
            Node node = createOptionPane(newEffect, () -> {
                effectFlowPane.requestFocus();
                effectFlowPane.getEffects().remove(newEffect);
            });
            if (node == null) {
                effectPaneWrapper.getChildren().clear();
            } else {
                effectPaneWrapper.getChildren().setAll(node);
            }
        });

        VBox leftBox = new VBox(previewPane);
        leftBox.getStyleClass().add("left-box");

        VBox rightBox = new VBox(effectFlowPane, effectPaneWrapper);
        rightBox.getStyleClass().addAll("right-box", "with-non-effect");

        effectFlowPane.getEffects().addListener((InvalidationListener) observable -> {
            if (effectFlowPane.getEffects().isEmpty()) {
                if (!rightBox.getStyleClass().contains("with-non-effect")) {
                    rightBox.getStyleClass().add("with-non-effect");
                }
            } else {
                rightBox.getStyleClass().remove("with-non-effect");
            }
        });

        FlowPane flowPane = new FlowPane(leftBox, rightBox);
        flowPane.getStyleClass().add("flow-pane");

        getChildren().addAll(flowPane);
    }

    private Node createOptionPane(Effect newEffect, Runnable deleteAction) {
        Node node = null;
        if (newEffect instanceof BlendTopInput effect) {
            node = new BlendTopInputEditor(effect, deleteAction);
        } else if (newEffect instanceof BlendBottomInput effect) {
            node = new BlendBottomInputEditor(effect, deleteAction);
        } else if (newEffect instanceof Bloom effect) {
            node = new BloomEditor(effect, deleteAction);
        } else if (newEffect instanceof BoxBlur effect) {
            node = new BoxBlurEditor(effect, deleteAction);
        } else if (newEffect instanceof ColorAdjust effect) {
            node = new ColorAdjustEditor(effect, deleteAction);
        } else if (newEffect instanceof ColorInput effect) {
            node = new ColorInputEditor(effect, deleteAction);
        } else if (newEffect instanceof DisplacementMap effect) {
            node = new DisplacementMapEditor(effect, deleteAction);
        } else if (newEffect instanceof DropShadow effect) {
            node = new DropShadowEditor(effect, deleteAction);
        } else if (newEffect instanceof GaussianBlur effect) {
            node = new GaussianBlurEditor(effect, deleteAction);
        } else if (newEffect instanceof Glow effect) {
            node = new GlowEditor(effect, deleteAction);
        } else if (newEffect instanceof ImageInput effect) {
            node = new ImageInputEditor(effect, deleteAction);
        } else if (newEffect instanceof InnerShadow effect) {
            node = new InnerShadowEditor(effect, deleteAction);
        } else if (newEffect instanceof LightingBumpInput effect){
            node = new LightingBumpInputEditor(effect, deleteAction);
        } else if (newEffect instanceof LightingContentInput effect){
            node = new LightingContentInputEditor(effect, deleteAction);
        } else if (newEffect instanceof MotionBlur effect) {
            node = new MotionBlurEditor(effect, deleteAction);
        } else if (newEffect instanceof PerspectiveTransform effect) {
            node = new PerspectiveTransformEditor(effect, deleteAction);
        } else if (newEffect instanceof Reflection effect) {
            node = new ReflectionEditor(effect, deleteAction);
        } else if (newEffect instanceof SepiaTone effect) {
            node = new SepiaToneEditor(effect, deleteAction);
        } else if (newEffect instanceof Shadow effect) {
            node = new ShadowEditor(effect, deleteAction);
        }
        return node;
    }

}
