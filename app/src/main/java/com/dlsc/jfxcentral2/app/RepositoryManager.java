package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository2;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.merge.ContentMergeStrategy;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;

public class RepositoryManager {

    private static final BooleanProperty repositoryUpdated = new SimpleBooleanProperty();

    public static boolean isRepositoryUpdated() {
        return repositoryUpdated.get();
    }

    public static BooleanProperty repositoryUpdatedProperty() {
        return repositoryUpdated;
    }

    public static void updateRepository(ProgressMonitor monitor) {
        if (!isRepositoryUpdated()) {
            try {
                initialLoad(monitor);
                Platform.runLater(() -> repositoryUpdated.set(true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isFirstTimeSetup() {
        return !DataRepository2.getRepositoryDirectory().exists();
    }

    private static void initialLoad(ProgressMonitor monitor) throws Exception {
        File repoDirectory = DataRepository2.getRepositoryDirectory();
        if (!repoDirectory.exists()) {
            Git.cloneRepository()
                    .setProgressMonitor(monitor)
                    .setURI("https://github.com/dlsc-software-consulting-gmbh/jfxcentral-data.git") //
                    .setBranch("live")
                    .setDirectory(repoDirectory)
                    .call();
        } else {
            repoDirectory = new File(DataRepository2.getRepositoryDirectory(), "/.git");
            new Git(FileRepositoryBuilder.create(repoDirectory))
                    .pull()
                    .setProgressMonitor(monitor)
                    .setContentMergeStrategy(ContentMergeStrategy.THEIRS)
                    .call();
        }
    }

    public static void prepareForRefresh() {
        repositoryUpdated.set(false);
    }
}
