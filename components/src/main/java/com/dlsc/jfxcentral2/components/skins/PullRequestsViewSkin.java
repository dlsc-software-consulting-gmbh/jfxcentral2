package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.components.PullRequestsView;
import javafx.beans.binding.Bindings;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class PullRequestsViewSkin extends SkinBase<PullRequestsView> {
    private static final int PAGE_SIZE = 12;

    public PullRequestsViewSkin(PullRequestsView view) {
        super(view);

        PaginationControl2 paginationControl = new PaginationControl2();
        paginationControl.pageCountProperty().bind(Bindings.createIntegerBinding(() -> view.getPullRequests().size() / PAGE_SIZE, view.pullRequestsProperty()));
        paginationControl.pageFactoryProperty().bind(Bindings.createObjectBinding(() -> page -> new RequestsPage(view.getPullRequests(), page), view.pullRequestsProperty()));

        getChildren().add(paginationControl);
    }

    private static class RequestsPage extends VBox {

        public RequestsPage(List<PullRequest> allPullRequests, int page) {
            getStyleClass().add("pull-requests-page");

            int startIndex = page * PAGE_SIZE;
            int endIndex = Math.min(allPullRequests.size(), startIndex + PAGE_SIZE);

            if (startIndex < allPullRequests.size()) {
                List<PullRequest> subList = allPullRequests.subList(startIndex, endIndex);
                for (int i = 0; i < subList.size(); i++) {
                    PullRequest pr = subList.get(i);
                    SinglePullRequestView view = new SinglePullRequestView(pr);
                    getChildren().add(view);
                    Region divider = new Region();
                    divider.getStyleClass().add("divider");

                    // hide the last divider, but keep it for layout purposes
                    divider.setVisible(i < subList.size() - 1);
                    getChildren().add(divider);
                }
            }
        }
    }
}
