package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.components.skins.SinglePullRequestView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;

public class PullRequestsView extends StripView {
    private static final int PAGE_SIZE = 12;
    private final BorderPane contentPane;

    public PullRequestsView() {
        getStyleClass().add("pull-requests-view");

        Label tipsLabel = new Label("Loading ...");
        tipsLabel.managedProperty().bind(tipsLabel.visibleProperty());
        tipsLabel.setGraphic(new FontIcon(AntDesignIconsOutlined.CLOUD_DOWNLOAD));
        tipsLabel.getStyleClass().add("tips-label");

        PaginationControl2 paginationControl = new PaginationControl2();
        paginationControl.setPlaceholder(null);
        paginationControl.pageCountProperty().bind(Bindings.createIntegerBinding(() -> getPullRequests() == null ? 0 : (int) Math.ceil(getPullRequests().size() * 1.0 / PAGE_SIZE), pullRequestsProperty()));

        contentPane = new BorderPane();
        contentPane.setTop(tipsLabel);
        BorderPane.setAlignment(tipsLabel, Pos.CENTER);
        getChildren().addAll(contentPane, paginationControl);

        paginationControl.pageFactoryProperty().bind(Bindings.createObjectBinding(() -> pageIndex -> {
            RequestsPage requestsPage = new RequestsPage(getPullRequests(), pageIndex, sizeProperty());
            contentPane.setCenter(requestsPage);
            return null;
        }, pullRequestsProperty()));

        pullRequestsProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                tipsLabel.setText("Loading ...");
                tipsLabel.setGraphic(new FontIcon(AntDesignIconsOutlined.CLOUD_DOWNLOAD));
                tipsLabel.setVisible(true);
                contentPane.setCenter(null);
            } else if (newValue.isEmpty()) {
                tipsLabel.setText("No pull requests found.");
                tipsLabel.setGraphic(new FontIcon(MaterialDesign.MDI_ALERT));
                tipsLabel.setVisible(true);
                contentPane.setCenter(null);
            } else {
                tipsLabel.setText("");
                tipsLabel.setGraphic(null);
                tipsLabel.setVisible(false);
                RequestsPage requestsPage = new RequestsPage(getPullRequests(), 0, sizeProperty());
                contentPane.setCenter(requestsPage);
            }
            paginationControl.setCurrentPageIndex(0);
        });

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

    private final ObjectProperty<List<PullRequest>> pullRequests = new SimpleObjectProperty<>(this, "pullRequests");

    public List<PullRequest> getPullRequests() {
        return pullRequests.get();
    }

    public ObjectProperty<List<PullRequest>> pullRequestsProperty() {
        return pullRequests;
    }

    public void setPullRequests(List<PullRequest> pullRequests) {
        this.pullRequests.set(pullRequests);
    }
}
