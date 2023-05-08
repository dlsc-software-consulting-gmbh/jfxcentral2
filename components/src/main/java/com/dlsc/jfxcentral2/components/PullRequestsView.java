package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.components.skins.PullRequestsViewSkin;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Skin;

public class PullRequestsView extends ControlBase {

    public PullRequestsView() {
        getStyleClass().add("pull-requests-view");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new PullRequestsViewSkin(this);
    }

    private final ListProperty<PullRequest> pullRequests = new SimpleListProperty<>(this, "pullRequests", FXCollections.observableArrayList());

    public ObservableList<PullRequest> getPullRequests() {
        return pullRequests.get();
    }

    public ListProperty<PullRequest> pullRequestsProperty() {
        return pullRequests;
    }

    public void setPullRequests(ObservableList<PullRequest> pullRequests) {
        this.pullRequests.set(pullRequests);
    }
}
