package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.app.service.LoadPullRequestsService;
import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.OpenJFXProjectView;
import com.dlsc.jfxcentral2.components.PullRequestsView;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.filters.PullRequestsFilterView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;

import java.util.ArrayList;

public class OpenJFXPage extends PageBase {

    private final ObservableList<PullRequest> pullRequests;
    private final FilteredList<PullRequest> filteredList;

    public OpenJFXPage(ObjectProperty<Size> size) {
        super(size, Mode.LIGHT);

        // data
        pullRequests = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(pullRequests);

        // load pull requests service
        LoadPullRequestsService service = new LoadPullRequestsService();
        service.valueProperty().addListener((ob, ov, nv) -> {
            if (nv == null || nv.isEmpty()) {
                return;
            }
            pullRequests.setAll(nv);
        });
        service.start();
    }

    @Override
    public String title() {
        return "JFXCentral - The OpenJFX Project";
    }

    @Override
    public String description() {
        return "The open source project behind JavaFX";
    }

    @Override
    public Node content() {
        // openjfx project view
        OpenJFXProjectView openJFXProjectView = new OpenJFXProjectView();
        openJFXProjectView.sizeProperty().bind(sizeProperty());

        // filter view
        PullRequestsFilterView pullRequestsFilterView = new PullRequestsFilterView();
        pullRequestsFilterView.sizeProperty().bind(sizeProperty());
        pullRequestsFilterView.setDisable(pullRequests.isEmpty());
        blockingProperty().bind(pullRequestsFilterView.blockingProperty());

        // pull requests
        PullRequestsView pullRequestsView = new PullRequestsView();
        pullRequestsView.sizeProperty().bind(sizeProperty());

        pullRequestsFilterView.predicateProperty().addListener((ob, ov, nv) -> {
            filteredList.setPredicate(nv);
            pullRequestsView.setPullRequests(pullRequests.isEmpty() ? null : new ArrayList<>(filteredList));
        });

        if (!pullRequests.isEmpty()) {
            updateUI(pullRequestsFilterView, pullRequestsView);
        }

        pullRequests.addListener((InvalidationListener) it -> updateUI(pullRequestsFilterView, pullRequestsView));

        // features
        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        // strip view for pull requests and features
        StripView stripView = new StripView(pullRequestsView, featuresContainer);

        return wrapContent(openJFXProjectView, pullRequestsFilterView, stripView);
    }

    private void updateUI(PullRequestsFilterView pullRequestsFilterView, PullRequestsView pullRequestsView) {
        filteredList.setPredicate(pullRequestsFilterView.getPredicate());
        if (!pullRequests.isEmpty()) {
            pullRequestsFilterView.setDisable(false);
        }
        pullRequestsView.setPullRequests(pullRequests.isEmpty() ? null : new ArrayList<>(filteredList));
    }
}
