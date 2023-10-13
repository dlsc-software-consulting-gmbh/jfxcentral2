package com.dlsc.jfxcentral2.utilities.shadowdesigner;

import com.dlsc.jfxcentral2.components.CustomToggleButton;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Effect;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class EffectFlowPane extends FlowPane {
    private static final Logger LOGGER = LogManager.getLogger(EffectFlowPane.class);
    private static final ToggleGroup TOGGLE_GROUP = new ToggleGroup();

    public EffectFlowPane() {
        getStyleClass().add("effect-flow-pane");

        value.bind(Bindings.createObjectBinding(() -> effects.stream().reduce((effect1, effect2) -> {
            if (effect2 instanceof Blend blend2) {
                blend2.setTopInput(effect1);
                return blend2;
            }

            if (supportsInputMethod(effect2)) {
                try {
                    effect2.getClass().getMethod("setInput", Effect.class).invoke(effect2, effect1);
                } catch (Exception e) {
                    LOGGER.error("Error while setting input for effect", e);
                }
            }
            return effect2;
        }).orElse(null), effects));

        effects.addListener((InvalidationListener) it -> {
            Toggle selectedToggle = TOGGLE_GROUP.getSelectedToggle();
            int index = TOGGLE_GROUP.getToggles().indexOf(selectedToggle);
            Effect selectedEffect;
            if (selectedToggle != null && index >= 0 && index < effects.size()) {
                selectedEffect = effects.get(index);
            } else {
                selectedEffect = null;
            }
            TOGGLE_GROUP.getToggles().clear();
            int lastIndex = getChildren().size() - 1;
            getChildren().remove(0, lastIndex);
            effects.forEach(effect -> {
                int i = getChildren().size() - 1;
                CustomToggleButton effectButton = new CustomToggleButton((i + 1) + "  " + effect.getClass().getSimpleName());
                effectButton.getStyleClass().add("effect-button");
                effectButton.setFocusTraversable(false);
                if (i != effects.size() - 1) {
                    effectButton.setGraphic(new FontIcon(Material2OutlinedMZ.NAVIGATE_NEXT));
                }
                getChildren().add(i, effectButton);
                effectButton.setToggleGroup(TOGGLE_GROUP);
            });
            //effects.forEach(effect -> {
            //    CustomToggleButton effectView = (CustomToggleButton) getChildren().get(effects.indexOf(effect));
            //    effectView.setSelected(effect == selectedEffect);
            //});
        });

        MenuButton addEffectButton = new MenuButton("Add Effect");
        addEffectButton.setFocusTraversable(false);
        addEffectButton.setGraphic(new FontIcon(Material2AL.ADD_CIRCLE));
        addEffectButton.getStyleClass().add("fill-button");
        getChildren().add(addEffectButton);

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

        TOGGLE_GROUP.selectedToggleProperty().addListener((ob, ov, nv) -> {
            if (nv == null) {
                setSelectedEffect(null);
                return;
            }
            setSelectedEffect(effects.get(TOGGLE_GROUP.getToggles().indexOf(nv)));
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
