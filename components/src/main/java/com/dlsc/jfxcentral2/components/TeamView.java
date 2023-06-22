package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.Member;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TeamView extends StripView {
    public TeamView() {
        getStyleClass().add("team-view");
        membersProperty().addListener((InvalidationListener) it -> updateMembers());
    }

    private void updateMembers() {
        getChildren().clear();

        ObservableList<Member> members = getMembers();
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            MemberCellView cellView = new MemberCellView(member);
            cellView.sizeProperty().bind(sizeProperty());
            if (i == 0) {
                cellView.getStyleClass().add("first");
            }
            if (i == members.size() - 1) {
                cellView.getStyleClass().add("last");
            }
            getChildren().add(cellView);
        }
    }

    private final ListProperty<Member> members = new SimpleListProperty<>(this, "members", FXCollections.observableArrayList());

    public ObservableList<Member> getMembers() {
        return members.get();
    }

    public ListProperty<Member> membersProperty() {
        return members;
    }

    public void setMembers(ObservableList<Member> members) {
        this.members.set(members);
    }
}
