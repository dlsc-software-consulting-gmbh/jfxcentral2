package com.dlsc.jfxcentral2.components;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Window;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class MastodonInputField extends TextField {

    private static final String DEFAULT_STYLE_CLASS = "mastodon-input-field";

    private final PopupControl suggestionPopup;
    private final ListView<String> suggestionListView;

    private static final List<String> MASTODON_URLS = Arrays.asList(
            "mastodon.social", "mastodon.cloud", "mstdn.social", "mastodon.online",
            "mas.to", "mastodon.world", "mastodon.sdf.org", "pixelfed.social", "c.im", "mastodon.uno", "mastodonapp.uk",
            "fosstodon.org", "universeodon.com", "mastodon.top", "techhub.social", "mamot.fr", "mstdn.party", "mastodon.art", "loforo.com", "m.cmx.im",
            "infosec.exchange", "ohai.social", "troet.cafe", "masto.ai", "foojay.social"
    );

    public MastodonInputField() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        suggestionListView = new ListView<>();
        suggestionListView.getStyleClass().add("mastodon-suggestion-list-view");
        suggestionListView.prefWidthProperty().bind(widthProperty());
        addListenersToSuggestionListView();

        suggestionPopup = new PopupControl();
        suggestionPopup.getStyleClass().add("suggestion-popup");
        suggestionPopup.setAutoHide(true);
        suggestionPopup.setHideOnEscape(true);
        suggestionPopup.setAutoFix(true);
        suggestionPopup.setSkin(new Skin<>() {
            @Override
            public PopupControl getSkinnable() {
                return suggestionPopup;
            }

            @Override
            public Node getNode() {
                return suggestionListView;
            }

            @Override
            public void dispose() {
            }
        });

        setSuggestionProvider(query -> MASTODON_URLS.stream()
                .filter(url -> StringUtils.containsIgnoreCase(url, query) && !StringUtils.equalsAnyIgnoreCase(url, query))
                .toList()
        );

        textProperty().addListener((obs, oldText, newText) -> {
            if (StringUtils.isNotBlank(newText)) {
                List<String> result = getSuggestionProvider().call(newText);
                if (!result.isEmpty()) {
                    suggestionListView.getItems().setAll(result);
                    showPopup();
                } else {
                    hidePopup();
                }
            } else {
                hidePopup();
            }
        });
    }

    private void addListenersToSuggestionListView() {
        suggestionListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                String selectedItem = suggestionListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    commitSelectedSuggestion(selectedItem);
                    event.consume();
                }
            }
        });

        suggestionListView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case UP -> {
                    navigateListView(-1);
                    event.consume();
                }
                case DOWN -> {
                    navigateListView(1);
                    event.consume();
                }
                case ENTER, TAB -> {
                    String selectedItem = suggestionListView.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        commitSelectedSuggestion(selectedItem);
                        event.consume();
                    }
                }
                case ESCAPE -> {
                    hidePopup();
                    event.consume();
                }
                default -> {
                }
            }
        });
    }

    public void hidePopup() {
        if (suggestionPopup.isShowing()) {
            suggestionPopup.hide();
        }

        Platform.runLater(() -> {
            if (!suggestionListView.getItems().isEmpty()) {
                suggestionListView.getItems().clear();
            }
            suggestionListView.getSelectionModel().clearSelection();
        });
    }

    private void showPopup() {
        Window window = getScene().getWindow();
        boolean hasItems = !suggestionListView.getItems().isEmpty();
        if (window == null || !hasItems) {
            return;
        }
        Bounds bounds = localToScreen(getLayoutBounds());
        suggestionPopup.show(getScene().getWindow(), bounds.getMinX(), bounds.getMaxY());
        suggestionListView.getSelectionModel().select(0);
        Platform.runLater(() -> suggestionListView.scrollTo(0));
    }


    private void navigateListView(int direction) {
        if (!suggestionPopup.isShowing() || suggestionListView.getItems().isEmpty()) {
            return;
        }

        SelectionModel<String> model = suggestionListView.getSelectionModel();
        int currentSize = suggestionListView.getItems().size();
        int currentIndex = model.getSelectedIndex();
        int newIndex;
        if (currentIndex == -1) {
            newIndex = (direction > 0) ? 0 : currentSize - 1;
        } else {
            newIndex = (currentIndex + direction + currentSize) % currentSize;
        }
        newIndex = Math.max(0, Math.min(newIndex, currentSize - 1));
        model.select(newIndex);
        suggestionListView.scrollTo(newIndex);
    }

    private void commitSelectedSuggestion(String selectedSuggestion) {
        if (selectedSuggestion != null) {
            String currentText = getText();
            replaceText(0, currentText.length(), selectedSuggestion);
            hidePopup();
        }
    }

    private final ObjectProperty<Callback<String, List<String>>> suggestionProvider = new SimpleObjectProperty<>(this, "suggestionProvider");

    public final ObjectProperty<Callback<String, List<String>>> suggestionProviderProperty() {
        return suggestionProvider;
    }

    public final Callback<String, List<String>> getSuggestionProvider() {
        return suggestionProvider.get();
    }

    public final void setSuggestionProvider(Callback<String, List<String>> value) {
        suggestionProvider.set(value);
    }

}
