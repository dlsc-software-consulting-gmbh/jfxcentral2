package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.mobile.components.ModelListCell;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.Node;
import java.util.List;
import com.dlsc.jfxcentral2.mobile.components.ModelListView;
import javafx.scene.image.Image;
import javafx.util.Callback;
import com.dlsc.jfxcentral.data.util.StringUtils;
import com.dlsc.jfxcentral2.icons.Ikon;

import java.util.function.Predicate;

public class MobilePeopleCategoryPage extends MobileCategoryPageBase<Person> {

    public MobilePeopleCategoryPage(ObjectProperty<Size> size) {
        super(size);
        getStyleClass().add("rounded-preview-page");
    }

    @Override
    protected Callback<ListView<Person>, ListCell<Person>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Person person, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().personImageProperty(person));
            }

            @Override
            protected String getSummary(Person person) {
                // The readme when this run has it, never fetched here: a cell
                // is filled while the list scrolls. Until the prefetch the
                // page starts brings it, the short description stands in.
                String readMe = DataRepository.getInstance().peekPersonReadMe(person);
                return StringUtils.isBlank(readMe) ? super.getSummary(person) : summaryOf(readMe);
            }
        };
    }

    @Override
    protected Callback<String, Predicate<Person>> getFilter() {
        return text -> person -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(person.getName(), text)
                || StringUtils.containsIgnoreCase(DataRepository.getInstance().peekPersonReadMe(person), text)
                || StringUtils.containsIgnoreCase(person.getDescription(), text);
    }

    @Override
    public List<Node> content() {
        List<Node> nodes = super.content();
        for (Node node : nodes) {
            if (node instanceof ModelListView<?> list) {
                DataRepository.getInstance().prefetchPeopleReadMes(list::refresh);
            }
        }
        return nodes;
    }

    /// The start of a readme as plain text: two lines show, so the cell is
    /// not handed kilobytes of markdown to wrap.
    static String summaryOf(String readMe) {
        StringBuilder b = new StringBuilder();
        for (String line : readMe.split("\n")) {
            String t = line.trim();
            if (t.isEmpty() || t.startsWith("#") || t.startsWith("![") || t.startsWith("<")) {
                if (b.length() > 0 && t.isEmpty()) {
                    break;
                }
                continue;
            }
            b.append(b.length() == 0 ? "" : " ").append(t);
            if (b.length() > 240) {
                break;
            }
        }
        String plain = b.toString().replace("**", "").replace("__", "").replace("`", "")
                .replaceAll("\\[([^\\]]*)\\]\\([^)]*\\)", "$1");
        return plain.length() > 240 ? plain.substring(0, 240) : plain;
    }

    @Override
    protected String getCategoryTitle() {
        return "People";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Person.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a JFX person";
    }

    @Override
    protected ObservableList<Person> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getPeople());
    }
}
