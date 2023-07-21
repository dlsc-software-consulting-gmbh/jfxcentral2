package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SearchField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import org.kordamp.ikonli.Ikon;

import java.util.Comparator;
import java.util.stream.Collectors;

public class IkonSearchField extends SearchField<Ikon> {

    public IkonSearchField() {
        icons.addListener(it -> updateField());
        setHidePopupWithSingleChoice(true);
    }

    private void updateField() {
        setSuggestionProvider(request -> getIcons().stream().filter(ikon -> ikon.getDescription().toLowerCase().contains(request.getUserText().toLowerCase())).collect(Collectors.toList()));

        setMatcher((ikon, text) -> ikon.getDescription().toLowerCase().startsWith(text.toLowerCase()));
        setComparator(Comparator.comparing(Ikon::getDescription));
        setConverter(new StringConverter<>() {
            @Override
            public String toString(Ikon ikon) {
                if (ikon != null) {
                    return ikon.getDescription();
                }
                return "";
            }

            @Override
            public Ikon fromString(String s) {
                return null;
            }
        });
    }

    private final ObjectProperty<ObservableList<? extends Ikon>> icons = new SimpleObjectProperty<>(this, "iconSet", FXCollections.observableArrayList());

    public ObservableList<? extends Ikon> getIcons() {
        return icons.get();
    }

    public ObjectProperty<ObservableList<? extends Ikon>> iconsProperty() {
        return icons;
    }

    public void setIcons(ObservableList<? extends Ikon> icons) {
        this.icons.set(icons);
    }
}
