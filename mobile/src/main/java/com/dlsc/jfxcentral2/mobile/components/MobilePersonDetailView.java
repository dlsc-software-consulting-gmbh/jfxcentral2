package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class MobilePersonDetailView extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "mobile-person-detail-view";
    private final TilePane linkedObjectsPane;
    private final AvatarView avatarView;
    private final Label nameLabel;
    private final HBox badgeBox;
    private final Label descriptionLabel;

    public record LinkedObjectBadge(String name, Ikon ikon, int count) {
    }

    public MobilePersonDetailView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Button backButton = new Button();
        backButton.getStyleClass().add("back-button");
        backButton.setGraphic(new FontIcon(FontAwesome.ANGLE_LEFT));
        MobileLinkUtil.setGoToBackLink(backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);

        avatarView = new AvatarView();
        StackPane topPane = new StackPane(backButton, avatarView);
        topPane.getStyleClass().add("top-pane");

        nameLabel = new Label();
        nameLabel.getStyleClass().add("name");

        badgeBox = new HBox();
        badgeBox.getStyleClass().add("badges");

        descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setWrapText(true);

        linkedObjectsPane = new TilePane();
        linkedObjectsPane.getStyleClass().add("linked-objects-pane");

        VBox personContentBox = new VBox(nameLabel, badgeBox, descriptionLabel, linkedObjectsPane);
        personContentBox.getStyleClass().add("person-content-box");

        getChildren().addAll(topPane, personContentBox);

        personProperty().addListener(it -> updateView());
        updateView();
    }

    private void updateView() {
        Person person = getPerson();

        // reset the view
        if (avatarView.imageProperty().isBound()) {
            avatarView.imageProperty().unbind();
        }
        nameLabel.setText("");
        descriptionLabel.setText("");
        badgeBox.getChildren().clear();
        linkedObjectsPane.getChildren().clear();

        // update the view
        if (person != null) {
            avatarView.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));
            nameLabel.setText(person.getName());
            descriptionLabel.setText(DataRepository2.getInstance().getPersonReadMe(person));
            updateLinkedObjectBadges();
            updateBadge();
        }
    }

    private void updateBadge() {
        Person person = getPerson();
        if (person.isChampion()) {
            Label championBadge = new Label("Rockstar", new FontIcon(IkonUtil.champion));
            championBadge.getStyleClass().addAll("badge-item", "rockstar");
            championBadge.setMinWidth(Region.USE_PREF_SIZE);
            badgeBox.getChildren().add(championBadge);
        }

        if (person.isRockstar()) {
            Label rockStartBadge = new Label("Champion", new FontIcon(IkonUtil.rockstar));
            rockStartBadge.getStyleClass().addAll("badge-item", "champion");
            rockStartBadge.setMinWidth(Region.USE_PREF_SIZE);
            badgeBox.getChildren().add(rockStartBadge);
        }
    }

    protected void updateLinkedObjectBadges() {
        Person person = getPerson();
        DataRepository2 dataRepository = DataRepository2.getInstance();
        List<LinkedObjectBadge> linkedObjectBadge = List.of(
                new LinkedObjectBadge("Apps", IkonUtil.getModelIkon(RealWorldApp.class), dataRepository.getLinkedObjects(person, RealWorldApp.class).size()),
                new LinkedObjectBadge("Books", IkonUtil.getModelIkon(Book.class), dataRepository.getLinkedObjects(person, Book.class).size()),
                new LinkedObjectBadge("Libraries", IkonUtil.getModelIkon(Library.class), dataRepository.getLinkedObjects(person, Library.class).size()),
                new LinkedObjectBadge("Tools", IkonUtil.getModelIkon(Tool.class), dataRepository.getLinkedObjects(person, Tool.class).size()),
                new LinkedObjectBadge("Videos", IkonUtil.getModelIkon(Video.class), dataRepository.getLinkedObjects(person, Video.class).size()),
                new LinkedObjectBadge("Tips", IkonUtil.getModelIkon(Tip.class), dataRepository.getLinkedObjects(person, Tip.class).size()),
                new LinkedObjectBadge("Learn", IkonUtil.getModelIkon(Learn.class),
                        dataRepository.getLinkedObjects(person, LearnJavaFX.class).size()
                                + dataRepository.getLinkedObjects(person, LearnMobile.class).size()
                                + dataRepository.getLinkedObjects(person, LearnRaspberryPi.class).size()
                ));

        linkedObjectBadge.stream().filter(item -> item.count() > 0).forEach(item -> {
            Label label = new Label(item.name());
            label.getStyleClass().add("linked-badge");
            linkedObjectsPane.getChildren().add(label);

            Label numberLabel = new Label(String.valueOf(item.count()));
            numberLabel.getStyleClass().add("number-label");
            StackPane.setAlignment(numberLabel, Pos.TOP_RIGHT);
            FontIcon icon = new FontIcon(item.ikon());

            StackPane graphic = new StackPane(icon, numberLabel);
            graphic.getStyleClass().add("badge-icon-wrapper");

            label.setGraphic(graphic);
        });
    }

    private final ObjectProperty<Person> person = new SimpleObjectProperty<>(this, "person");

    public final ObjectProperty<Person> personProperty() {
        return person;
    }

    public final Person getPerson() {
        return personProperty().get();
    }

    public final void setPerson(Person person) {
        personProperty().set(person);
    }

}
