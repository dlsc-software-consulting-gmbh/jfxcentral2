package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.CopyUtil;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class TextCopyView extends PaneBase {
    public enum ViewMode {
        RADIO_BUTTON, COMBO_BOX
    }

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final HBox optionsBox;

    public TextCopyView() {
        getStyleClass().add("text-copy-view");

        Button copyButton = new Button();
        copyButton.getStyleClass().addAll("fill-button", "copy-button");
        copyButton.textProperty().bind(copyButtonTextProperty());
        FontIcon graphic = new FontIcon();
        graphic.iconCodeProperty().bindBidirectional(copyButtonIconProperty());
        copyButton.setGraphic(graphic);

        optionsBox = new HBox();
        optionsBox.getStyleClass().add("options-box");

        HBox topBox = new HBox(optionsBox, new Spacer(), copyButton);
        topBox.getStyleClass().add("top-box");

        TextArea textArea = new TextArea();
        textArea.getStyleClass().add("code-text-area");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.promptTextProperty().bind(promptTextProperty());
        textArea.maxHeight(Double.MAX_VALUE);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        VBox container = new VBox(topBox, textArea);
        container.getStyleClass().add("container");

        getChildren().add(container);

        options.addListener((InvalidationListener) c -> updateView());

        selectedItemProperty().addListener((ob, ov, nv) -> selectedItemHandler(nv));
        selectedIndexProperty().addListener((ob, ov, nv) -> selectedIndexHandler(nv));

        toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> selectedToggleHandler(newToggle));

        viewMode.addListener((obs, oldType, newType) -> updateView());
        textArea.textProperty().bind(textProperty());
        CopyUtil.setCopyOnClick(copyButton, textArea.getText());
        textProperty().addListener((obs, oldVal, newVal) -> CopyUtil.setCopyOnClick(copyButton, newVal));
        updateView();
    }

    private void selectedToggleHandler(Toggle newToggle) {
        if (newToggle != null) {
            String item = (String) newToggle.getUserData();
            setSelectedItem(item);
            setSelectedIndex(options.indexOf(item));
        } else {
            setSelectedItem(null);
            setSelectedIndex(-1);
        }
    }

    private void selectedIndexHandler(Number nv) {
        if (nv == null || nv.intValue() < 0 || nv.intValue() >= options.size()) {
            toggleGroup.selectToggle(null);
            return;
        }
        int index = nv.intValue();

        if (getViewMode() == ViewMode.RADIO_BUTTON) {
            if (index >= 0 && index < options.size()) {
                Node node = optionsBox.getChildren().get(index);
                if (node instanceof RadioButton btn) {
                    btn.setSelected(true);
                }
            }
        } else if (getViewMode() == ViewMode.COMBO_BOX) {
            ComboBox<?> comboBox = (ComboBox<?>) optionsBox.getChildren().get(0);
            comboBox.getSelectionModel().select(index);
        }
    }

    private void selectedItemHandler(String nv) {
        if (nv == null || !options.contains(nv)) {
            toggleGroup.selectToggle(null);
            return;
        }

        if (getViewMode() == ViewMode.RADIO_BUTTON) {
            ObservableList<Node> optionButtons = optionsBox.getChildren();
            for (Node child : optionButtons) {
                if (child instanceof RadioButton btn) {
                    if (nv.equals(btn.getUserData())) {
                        btn.setSelected(true);
                        break;
                    }
                }
            }
        } else if (getViewMode() == ViewMode.COMBO_BOX) {
            ComboBox<String> comboBox = (ComboBox<String>) optionsBox.getChildren().get(0);
            comboBox.setValue(nv);
        }
    }

    private void updateView() {
        if (getViewMode() == ViewMode.RADIO_BUTTON) {
            updateViewWithRadioButtons();
        } else if (getViewMode() == ViewMode.COMBO_BOX) {
            updateViewWithComboBox();
        }
    }

    private void updateViewWithRadioButtons() {
        String currentSelectedItem = getSelectedItem();
        optionsBox.getChildren().clear();
        toggleGroup.getToggles().clear();

        List<RadioButton> radioButtons = options.stream()
                .map(option -> {
                    RadioButton button = new RadioButton(option);
                    button.setToggleGroup(toggleGroup);
                    button.getStyleClass().add("option-button");
                    button.setUserData(option);
                    return button;
                })
                .toList();

        optionsBox.getChildren().addAll(radioButtons);

        if (currentSelectedItem != null) {
            toggleGroup.selectToggle(radioButtons.get(options.indexOf(currentSelectedItem)));
        }
    }

    private void updateViewWithComboBox() {
        String currentSelectedItem = getSelectedItem();
        optionsBox.getChildren().clear();
        toggleGroup.getToggles().clear();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getStyleClass().add("option-combo-box");

        comboBox.setItems(options);
        comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            setSelectedItem(newVal);
            setSelectedIndex(options.indexOf(newVal));
        });
        optionsBox.getChildren().add(comboBox);
        if (currentSelectedItem != null) {
            comboBox.setValue(currentSelectedItem);
        }
    }

    private final ObservableList<String> options = FXCollections.observableArrayList();

    public ObservableList<String> getOptions() {
        return options;
    }

    private final ObjectProperty<String> selectedItem = new SimpleObjectProperty<>(this, "selectedItem");

    public ObjectProperty<String> selectedItemProperty() {
        return selectedItem;
    }

    public String getSelectedItem() {
        return selectedItem.get();
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem.set(selectedItem);
    }

    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(this, "selectedIndex", -1);

    public IntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex.set(selectedIndex);
    }

    private final StringProperty promptText = new SimpleStringProperty(this, "promptText", "No content available for copying.");

    public String getPromptText() {
        return promptText.get();
    }

    public StringProperty promptTextProperty() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText.set(promptText);
    }

    private final ObjectProperty<ViewMode> viewMode = new SimpleObjectProperty<>(this, "type", ViewMode.RADIO_BUTTON);

    public ViewMode getViewMode() {
        return viewMode.get();
    }

    public ObjectProperty<ViewMode> viewModeProperty() {
        return viewMode;
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode.set(viewMode);
    }

    private final StringProperty copyButtonText = new SimpleStringProperty(this, "copyButtonText");

    public String getCopyButtonText() {
        return copyButtonText.get();
    }

    public StringProperty copyButtonTextProperty() {
        return copyButtonText;
    }

    public void setCopyButtonText(String copyButtonText) {
        this.copyButtonText.set(copyButtonText);
    }

    private final ObjectProperty<Ikon> copyButtonIcon = new SimpleObjectProperty<>(this, "copyButtonIcon", IkonUtil.copy);

    public Ikon getCopyButtonIcon() {
        return copyButtonIcon.get();
    }

    public ObjectProperty<Ikon> copyButtonIconProperty() {
        return copyButtonIcon;
    }

    public void setCopyButtonIcon(Ikon copyButtonIcon) {
        this.copyButtonIcon.set(copyButtonIcon);
    }

    private final StringProperty text = new SimpleStringProperty(this, "text");

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }
}

