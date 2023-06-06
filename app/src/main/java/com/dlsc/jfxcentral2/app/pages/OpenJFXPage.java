package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.OpenJFXProjectView;
import com.dlsc.jfxcentral2.components.PullRequestsView;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.filters.PullRequestsFilterView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;

public class OpenJFXPage extends PageBase {


    public OpenJFXPage(ObjectProperty<Size> size) {
        super(size, TopMenuBar.Mode.LIGHT);
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
        blockingProperty().bind(pullRequestsFilterView.blockingProperty());

        // pull requests
        PullRequestsView pullRequestsView = new PullRequestsView();
        pullRequestsView.sizeProperty().bind(sizeProperty());

        // data
        ObservableList<PullRequest> itemsList = FXCollections.observableArrayList(DataRepository2.getInstance().loadPullRequests());

        FilteredList<PullRequest> filteredList = new FilteredList<>(itemsList);
        filteredList.predicateProperty().bind(pullRequestsFilterView.predicateProperty());

        SortedList<PullRequest> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(pullRequestsFilterView.comparatorProperty());

        pullRequestsView.setPullRequests(sortedList);

        // features
        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        // strip view for pull requests and features
        StripView stripView = new StripView(pullRequestsView, featuresContainer);

        return wrapContent(openJFXProjectView, pullRequestsFilterView, stripView);
    }
}
