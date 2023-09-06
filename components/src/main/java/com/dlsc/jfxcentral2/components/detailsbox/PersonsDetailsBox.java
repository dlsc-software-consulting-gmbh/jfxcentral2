package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.function.Consumer;

public class PersonsDetailsBox extends DetailsBoxBase<Person> {

    public PersonsDetailsBox() {
        getStyleClass().add("persons-details-box");

        setTitle("PEOPLE");
        setIkon(IkonUtil.getModelIkon(Person.class));
        setMaxItemsPerPage(Integer.MAX_VALUE);
        setOnHomepage(Person::getWebsite);
    }

    @Override
    protected List<Node> createActionButtons(Person model) {
        return List.of(createDetailsButton(model), createHomepageButton(model));
    }

    private final ObjectProperty<Consumer<Person>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<Person> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<Person>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<Person> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

    @Override
    protected Node createMainPreView(Person model) {
        ObjectProperty<Image> imageProperty = ModelObjectTool.getModelPreviewImageProperty(model, false);

        if (imageProperty != null) {
            AvatarView avatarView = new AvatarView();
            avatarView.imageProperty().bind(imageProperty);

            StackPane mainPreviewPane = new StackPane(avatarView);
            StackPane.setAlignment(avatarView, isSmall() ? Pos.CENTER_LEFT : Pos.TOP_LEFT);
            mainPreviewPane.getStyleClass().addAll("image-wrapper", "main-preview-wrapper");
            mainPreviewPane.managedProperty().bind(mainPreviewPane.visibleProperty());
            mainPreviewPane.visibleProperty().bind(imageProperty.isNotNull());

            return mainPreviewPane;
        }

        return null;
    }
}
