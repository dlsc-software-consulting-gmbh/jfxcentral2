package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.merge.ContentMergeStrategy;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class RefreshPage extends PageBase {


    public RefreshPage(ObjectProperty<Size> size) {
        super(size, TopMenuBar.Mode.LIGHT);
    }

    @Override
    public String title() {
        return "JFXCentral - Refresh Data";
    }

    @Override
    public String description() {
        return "Perform a Git update";
    }

    @Override
    public Node content() {
        Label label = new Label("Refreshing Data");
        label.setStyle("-fx-text-fill: red; -fx-padding: 50px; -fx-font-size: 18px;");
        VBox uiBox = new VBox(label);

        uiBox.setAlignment(Pos.BOTTOM_CENTER);

        StackPane.setAlignment(uiBox, Pos.TOP_CENTER);

        updateRepositoryInBackground(new ProgressMonitor() {
            @Override
            public void start(int i) {

            }

            @Override
            public void beginTask(String s, int i) {
                label.setText(s + ", " + i);
            }

            @Override
            public void update(int i) {

            }

            @Override
            public void endTask() {

            }

            @Override
            public boolean isCancelled() {
                return false;
            }
        }, () -> {
            System.out.println("done");
        });
        return wrapContent(uiBox);
    }

    public void updateRepositoryInBackground(ProgressMonitor monitor, Runnable callback) {
        Thread thread = new Thread(() -> {
            try {
                updateRepository(monitor);
                callback.run();
            } catch (GitAPIException | IOException e) {
                e.printStackTrace();
            }
        });

        thread.setDaemon(true);
        thread.setName("Repository Update Thread");
        thread.start();
    }

    public void updateRepository(ProgressMonitor monitor) throws GitAPIException, IOException {
        System.out.println("updating repository, monitor = " + monitor);
        File repoDirectory = DataRepository.getInstance().getRepositoryDirectory();
        if (!repoDirectory.exists()) {
            Git.cloneRepository()
                    .setURI("https://github.com/dlemmermann/jfxcentral-data.git")
                    .setBranch("live")
                    .setDirectory(repoDirectory)
                    .setProgressMonitor(monitor)
                    .call();
        } else {
            repoDirectory = new File(DataRepository.getInstance().getRepositoryDirectory(), "/.git");
            Git git = new Git(new FileRepositoryBuilder().create(repoDirectory));
            git.pull().setContentMergeStrategy(ContentMergeStrategy.THEIRS).call();
        }

        Git.shutdown();

        // trigger the data loading inside the data repository if needed
        DataRepository.getInstance().loadData();

        monitor.endTask();
    }
}
