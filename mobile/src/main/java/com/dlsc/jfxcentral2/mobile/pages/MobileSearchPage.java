package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.SearchResultCell;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.mobile.utils.ListViewUtil;
import com.dlsc.jfxcentral2.mobile.utils.PreferredFocusedNodeProvider;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MobileSearchPage extends VBox implements PreferredFocusedNodeProvider {

    private static final String DEFAULT_STYLE_CLASS = "mobile-search-page";
    private final SizeSupport sizeSupport = new SizeSupport(this);
    private final SearchTextField searchField;
    private final ObservableList<ModelObject> totalResources = FXCollections.observableArrayList();
    private final List<String> searchSuggestions = new ArrayList<>(List.of(
            "JavaFX", "JFX", "JavaFX 8", "JavaFX 9", "JavaFX 10", "JavaFX 11", "JavaFX 16", "JavaFX 17",
            "JavaFX 18", "CSS", "CSSFX", "Demo", "Example", "Gluon", "Mobile", "Raspberry", "2D", "3D", "Chart",
            "Controls", "Game", "Medical", "Aviation", "Business", "Maps", "JFX Days", "Theme", "Guide", "Canvas",
            "Tutorial", "Icon", "Tool", "Started", "Learn Javafx", "JFX In Action", "Component", "Pane", "Test"));

    public MobileSearchPage(ObjectProperty<Size> size) {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        sizeProperty().bind(size);

        addModelsToCollection();
        addSuggestionsToList();

        Button backButton = new Button();
        backButton.getStyleClass().add("back-button");
        backButton.setGraphic(new FontIcon(FontAwesome.ANGLE_LEFT));
        MobileLinkUtil.setLink(backButton, PagePath.HOME);

        Label searchLabel = new Label("Search");
        searchField = new SearchTextField(true);
        searchField.setRight(searchLabel);
        searchField.setPromptText("Search for anything ...");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        FilteredList<ModelObject> filteredResults = new FilteredList<>(totalResources);
        filteredResults.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String text = searchField.textProperty().getValueSafe().trim();
            if (StringUtils.isBlank(text)) {
                return mo -> false;
            }
            return mo -> mo.matches(text);
        }, searchField.textProperty()));

        HBox searchBox = new HBox(backButton, searchField);
        searchBox.getStyleClass().add("search-box");

        ListView<ModelObject> searchResultListView = new ListView<>(filteredResults);
        searchResultListView.getStyleClass().add("mobile");
        searchResultListView.setCellFactory(param -> new SearchResultCell());
        searchResultListView.setPlaceholder(createPlaceholder());
        ListViewUtil.addCellClickHandler(searchResultListView, (integer, modelObject) -> MobileLinkUtil.getToPage(ModelObjectTool.getModelLink(modelObject)));

        StackPane contentPane = new StackPane(searchResultListView);
        contentPane.getStyleClass().add("content-pane");
        VBox.setVgrow(contentPane, Priority.ALWAYS);

        getChildren().addAll(searchBox, contentPane);
    }

    private void addSuggestionsToList() {
        DataRepository2 repository = DataRepository2.getInstance();
        repository.getPeople().forEach(person -> searchSuggestions.add(person.getName()));
    }

    private Node createPlaceholder() {
        Label tipsTitle = new Label("Tips", new FontIcon(MaterialDesignL.LIGHTBULB_ON_OUTLINE));
        tipsTitle.getStyleClass().add("tips-title");
        Label tipsLabel = new Label("You can search for JavaFX libraries, blogs, tips, people, tutorials, tools, videos, real-world apps, etc.");
        tipsLabel.getStyleClass().add("tips-content");
        tipsLabel.setWrapText(true);

        VBox tipsBox = new VBox(tipsTitle, tipsLabel);
        tipsBox.getStyleClass().add("tips-box");

        Label suggestionLabel = new Label("Random Search Keywords", new FontIcon(Elusive.RANDOM));

        Button randomButton = new Button();
        randomButton.setGraphic(new FontIcon(BoxiconsRegular.REFRESH));

        HBox randomHeader = new HBox(suggestionLabel, new Spacer(), randomButton);
        randomHeader.getStyleClass().add("random-header");

        GridPane randomContent = new GridPane();
        randomContent.getStyleClass().add("random-content");
        randomContent.getColumnConstraints().addAll(createColumnConstraints(), createColumnConstraints());
        for (int i = 0; i < 16; i++) {
            Label label = new Label();
            label.getStyleClass().add("random-label");
            label.setMaxWidth(Double.MAX_VALUE);
            randomContent.add(label, i % 2, i / 2);
            label.setOnMouseClicked(event -> searchField.setText(label.getText()));
        }

        randomSuggestionItems(randomContent);
        randomButton.setOnAction(event -> randomSuggestionItems(randomContent));

        VBox suggestionsBox = new VBox(randomHeader, randomContent);
        suggestionsBox.getStyleClass().add("random-box");
        HBox.setHgrow(randomContent, Priority.ALWAYS);

        VBox vBox = new VBox(tipsBox, suggestionsBox);
        vBox.getStyleClass().add("placeholder-vbox");
        return vBox;
    }

    private ColumnConstraints createColumnConstraints() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setHgrow(Priority.ALWAYS);
        return columnConstraints;
    }

    private void randomSuggestionItems(GridPane pane) {
        List<String> shuffledSuggestions = new ArrayList<>(searchSuggestions);
        Collections.shuffle(shuffledSuggestions);

        List<String> list = shuffledSuggestions.stream()
                .limit(16)
                .toList();

        for (int i = 0; i < 16; i++) {
            Label node = (Label) pane.getChildren().get(i);
            node.setText(list.get(i));
        }
    }

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public void addModelsToCollection() {
        DataRepository2 repository = DataRepository2.getInstance();
        totalResources.addAll(repository.getBooks());
        totalResources.addAll(repository.getBlogs());
        totalResources.addAll(repository.getCompanies());
        totalResources.addAll(repository.getPeople());
        totalResources.addAll(repository.getLibraries());
        totalResources.addAll(repository.getRealWorldApps());
        totalResources.addAll(repository.getTools());
        totalResources.addAll(repository.getVideos());
        totalResources.addAll(repository.getNews());
        totalResources.addAll(repository.getTutorials());
        totalResources.addAll(repository.getTips());
        totalResources.addAll(repository.getLearnJavaFX());
        totalResources.addAll(repository.getLearnRaspberryPi());
        totalResources.addAll(repository.getLearnMobile());
    }

    @Override
    public Node getPreferredFocusedNode() {
        return searchField;
    }

}
