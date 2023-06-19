package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository2;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.merge.ContentMergeStrategy;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;

public class RepositoryManager {

    private static double total;
    private static double acc;
    private static int lastPercentage;
    private static String taskName;

    /* This main is called by JPro during startup! */
    public static void main(String[] args) {
        updateRepository(new ProgressMonitor() {

            @Override
            public void start(int i) {
            }

            @Override
            public void beginTask(String task, int work) {
                taskName = task;
                total = work;
                acc = 0;
                lastPercentage = 0;
            }

            @Override
            public void update(int i) {
                if (total != ProgressMonitor.UNKNOWN) {
                    acc += i;
                    int percentage = (int) ((acc / total) * 100);
                    if (percentage > lastPercentage) {
                        lastPercentage = percentage;
                        System.out.println(taskName + " " + percentage + "%");
                    }
                }
            }

            @Override
            public void endTask() {
                System.out.println(taskName + " complete");
            }

            @Override
            public boolean isCancelled() {
                return false;
            }
        });
    }

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
                repositoryUpdated.set(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isFirstTimeSetup() {
        return !DataRepository2.getRepositoryDirectory().exists();
    }

    private static void initialLoad(ProgressMonitor monitor) throws Exception {
        if (System.getProperty("jfxcentral.repo") == null) {
            File repoDirectory = DataRepository2.getRepositoryDirectory();
            if (!repoDirectory.exists()) {
                Git.cloneRepository()
                        .setProgressMonitor(monitor)
                        .setURI("https://github.com/dlemmermann/jfxcentral-data.git") //
                        .setBranch("live")
                        .setDirectory(repoDirectory)
                        .call();
            } else {
                repoDirectory = new File(DataRepository2.getRepositoryDirectory(), "/.git");
                new Git(FileRepositoryBuilder.create(repoDirectory))
                        .pull()
                        .setProgressMonitor(monitor)
                        .setFastForward(MergeCommand.FastForwardMode.FF)
                        .setContentMergeStrategy(ContentMergeStrategy.THEIRS)
                        .call();
            }
        }
    }

    public static void prepareForRefresh() {
        repositoryUpdated.set(false);
    }
}
