package com.dlsc.jfxcentral2.utilities.effectdesigner;

import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.JFXCentralUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class EffectFlowPane extends FlowPane {
    private static final Logger LOGGER = LogManager.getLogger(EffectFlowPane.class);
    private static final ToggleGroup TOGGLE_GROUP = new ToggleGroup();

    public EffectFlowPane() {
        getStyleClass().add("effect-flow-pane");

        effectValueBind();

        effectsAddListener();

        getChildren().addAll(createAddEffectButton(), createCopyCodeButton());

        toggleGroupAddListener();
    }

    private void effectValueBind() {
        value.bind(Bindings.createObjectBinding(() -> effects.stream().reduce((effect1, effect2) -> {
            if (supportsInputMethod(effect2)) {
                try {
                    effect2.getClass().getMethod("setInput", Effect.class).invoke(effect2, effect1);
                } catch (Exception e) {
                    LOGGER.error("Error while setting input for effect", e);
                }
            }
            return effect2;
        }).orElse(null), effects));
    }

    private void toggleGroupAddListener() {
        TOGGLE_GROUP.selectedToggleProperty().addListener((ob, ov, nv) -> {
            if (effects.isEmpty()) {
                setSelectedEffect(null);
                return;
            }
            setSelectedEffect(effects.get(TOGGLE_GROUP.getToggles().indexOf(nv)));
        });
    }

    private MenuButton createCopyCodeButton() {
        MenuButton copyCodeButton = new MenuButton("Copy Code");
        copyCodeButton.getStyleClass().addAll("fill-button", "copy-effect-code-button");
        copyCodeButton.setFocusTraversable(false);
        copyCodeButton.setGraphic(new FontIcon(IkonUtil.copy));

        CopyEffectCodeView copyEffectCodeView = new CopyEffectCodeView(() -> JFXCentralUtil.runInFXThread(copyCodeButton::hide, 220));
        CustomMenuItem customMenuItem = new CustomMenuItem(copyEffectCodeView, false);
        copyCodeButton.setOnShowing(event -> {
            String javaCode = EffectCodeGenerator.effectsToJavaCode(effects);
            copyEffectCodeView.setJavaCode(javaCode);

            String cssCode = "";
            if (effects.size() == 1) {
                Effect effect = effects.get(0);
                if (effect instanceof DropShadow shadow) {
                    cssCode = EffectCodeGenerator.convertToCss(shadow);
                } else if (effect instanceof InnerShadow shadow) {
                    cssCode = EffectCodeGenerator.convertToCss(shadow);
                }
            }
            copyEffectCodeView.setCssCode(cssCode);
        });
        copyCodeButton.getItems().addAll(customMenuItem);
        copyCodeButton.managedProperty().bind(copyCodeButton.visibleProperty());
        copyCodeButton.visibleProperty().bind(Bindings.createBooleanBinding(() -> !effects.isEmpty(), effects));
        return copyCodeButton;
    }

    private MenuButton createAddEffectButton() {
        MenuButton addEffectButton = new MenuButton("Add Effect");
        addEffectButton.setFocusTraversable(false);
        addEffectButton.setGraphic(new FontIcon(Material2AL.ADD_CIRCLE));
        addEffectButton.getStyleClass().add("fill-button");

        List<MenuItem> menuItems = Arrays.stream(EffectEnum.getAllNames())
                .map(name -> {
                    MenuItem menuItem = new MenuItem(name);
                    menuItem.setOnAction(event -> {
                        try {
                            EffectEnum effectEnum = EffectEnum.findByName(name);
                            if (effectEnum == null) {
                                return;
                            }
                            Effect effect = effectEnum.getEffectClass().getDeclaredConstructor().newInstance();
                            effects.add(effect);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                 NoSuchMethodException e) {
                            LOGGER.error("Error while creating effect", e);
                        }
                    });
                    return menuItem;
                })
                .toList();
        addEffectButton.getItems().addAll(menuItems);

        // Disable add effect button when the last effect is ColorInput;Because ColorInput doesn't support input method.
        addEffectButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !effects.isEmpty() && effects.get(effects.size() - 1) instanceof ColorInput,
                        effects
                )
        );
        return addEffectButton;
    }

    private void effectsAddListener() {
        effects.addListener((ListChangeListener.Change<? extends Effect> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    for (Effect removedEffect : change.getRemoved()) {
                        for (Effect effect : effects) {
                            try {
                                Method setInputMethod = effect.getClass().getMethod("setInput", Effect.class);
                                Effect currentInput = (Effect) effect.getClass().getMethod("getInput").invoke(effect);
                                if (currentInput == removedEffect) {
                                    setInputMethod.invoke(effect, (Effect) null);
                                }
                            } catch (Exception e) {
                                LOGGER.error("Error while removing input for effect", e);
                            }
                        }
                    }
                }
            }

            TOGGLE_GROUP.getToggles().clear();
            int lastIndex = getChildren().size() - 2;
            getChildren().remove(0, lastIndex);
            effects.forEach(effect -> {
                int i = getChildren().size() - 2;
                String className = effect.getClass().getSimpleName();
                if (StringUtils.equalsIgnoreCase(className, "BlendTopInput")) {
                    className = "Blend Top";
                } else if (StringUtils.equalsIgnoreCase(className, "BlendBottomInput")) {
                    className = "Blend Bottom";
                } else if (StringUtils.equalsIgnoreCase(className, "LightingBumpInput")) {
                    className = "Lighting Bump";
                } else if (StringUtils.equalsIgnoreCase(className, "LightingContentInput")) {
                    className = "Lighting Content";
                }
                CustomToggleButton effectButton = new CustomToggleButton((i + 1) + "  " + className);
                effectButton.getStyleClass().add("effect-button");
                effectButton.setFocusTraversable(false);
                if (i != effects.size() - 1) {
                    effectButton.setGraphic(new FontIcon(Material2OutlinedMZ.NAVIGATE_NEXT));
                }
                getChildren().add(i, effectButton);
                effectButton.setToggleGroup(TOGGLE_GROUP);
                effectButton.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> this.requestFocus());
            });
            requestFocus();
            ObservableList<Toggle> toggles = TOGGLE_GROUP.getToggles();
            toggles.get(toggles.size() - 1).setSelected(true);
        });
    }

    private final ObservableList<Effect> effects = FXCollections.observableArrayList();

    public ObservableList<Effect> getEffects() {
        return effects;
    }

    private final ReadOnlyObjectWrapper<Effect> value = new ReadOnlyObjectWrapper<>(this, "value", null);

    public Effect getValue() {
        return value.get();
    }

    public ReadOnlyObjectProperty<Effect> valueProperty() {
        return value.getReadOnlyProperty();
    }

    private void setValue(Effect value) {
        this.value.set(value);
    }

    private boolean supportsInputMethod(Effect effect) {
        try {
            effect.getClass().getMethod("setInput", Effect.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private final ReadOnlyObjectWrapper<Effect> selectedEffect = new ReadOnlyObjectWrapper<>(this, "selectedEffect");

    public Effect getSelectedEffect() {
        return selectedEffect.get();
    }

    public ReadOnlyObjectProperty<Effect> selectedEffectProperty() {
        return selectedEffect.getReadOnlyProperty();
    }

    private void setSelectedEffect(Effect selectedEffect) {
        this.selectedEffect.set(selectedEffect);
    }
}
