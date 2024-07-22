package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.mobile.components.MobilePersonDetailView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobilePersonDetailsPage extends MobileDetailsPageBase<Person> {

    public MobilePersonDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Person.class, itemId);
    }

    @Override
    public List<Node> content() {
        Person person = getItem();

        MobilePersonDetailView personDetailView = new MobilePersonDetailView();
        personDetailView.setPerson(person);
        personDetailView.sizeProperty().bind(sizeProperty());
        PrettyScrollPane scrollPane = new PrettyScrollPane(new StackPane(personDetailView));
        scrollPane.getStyleClass().add("mobile");
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return List.of(scrollPane);
    }
}
