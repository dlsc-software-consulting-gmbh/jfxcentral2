package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.components.skins.SinglePullRequestView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class PullRequestsView extends StripView {
    private static final int PAGE_SIZE = 12;
    private ScrollPane cacheScrollPane;
    public PullRequestsView() {
        getStyleClass().add("pull-requests-view");

        PaginationControl2 paginationControl = new PaginationControl2();
        paginationControl.pageCountProperty().bind(Bindings.createIntegerBinding(() -> getPullRequests().size() / PAGE_SIZE, pullRequestsProperty()));
        getChildren().addAll(paginationControl);

        paginationControl.pageFactoryProperty().bind(Bindings.createObjectBinding(() -> pageIndex -> {
            RequestsPage requestsPage = new RequestsPage(getPullRequests(), pageIndex, sizeProperty());
            if (getChildren().size() > 1) {
                getChildren().remove(0);
            }
            getChildren().add(0, requestsPage);
            return null;
        }, pullRequestsProperty()));

    }

    private static class RequestsPage extends VBox {
        public RequestsPage(List<PullRequest> allPullRequests, int page, ObjectProperty<Size> sizeProperty) {
            getStyleClass().add("pull-requests-page");

            int startIndex = page * PAGE_SIZE;
            int endIndex = Math.min(allPullRequests.size(), startIndex + PAGE_SIZE);

            if (startIndex < allPullRequests.size()) {
                List<PullRequest> subList = allPullRequests.subList(startIndex, endIndex);
                for (int i = 0; i < subList.size(); i++) {
                    PullRequest pr = subList.get(i);
                    SinglePullRequestView view = new SinglePullRequestView(pr);
                    view.sizeProperty().bind(sizeProperty);
                    getChildren().add(view);
                    Region divider = new Region();
                    divider.getStyleClass().add("divider");

                    // hide the last divider, but keep it for layout purposes
                    divider.setVisible(i < subList.size() - 1);
                    getChildren().add(divider);
                }
            }
            VBox.setVgrow(PullRequestsView.RequestsPage.this, Priority.ALWAYS);
        }
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
