package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository2;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.merge.ContentMergeStrategy;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Locale;

public class RepositoryManager {

    private static final BooleanProperty repositoryUpdated = new SimpleBooleanProperty();
    private static final String GITHUB_HOST = "github.com";
    private static final String GITEE_HOST = "gitee.com";
    private static final String GITHUB_REPOSITORY_URL = "https://github.com/dlsc-software-consulting-gmbh/jfxcentral-data.git";
    private static final String GITEE_REPOSITORY_URL = "https://gitee.com/leewyatt/jfxcentral-data-mirror.git";
    private static final int TIMEOUT = 2000;

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
        // Network not available, skip initial load
        if (!isNetworkAvailable()) {
            monitor.beginTask("Network not available.", 1);
            monitor.endTask();
            return;
        }

        File repoDirectory = DataRepository2.getRepositoryDirectory();
        if (!repoDirectory.exists()) {
            CloneCommand cloneCmd = Git.cloneRepository()
                    .setProgressMonitor(monitor)
                    .setURI(shouldUseGitee() ? GITEE_REPOSITORY_URL : GITHUB_REPOSITORY_URL)
                    .setBranch("live")
                    .setDirectory(repoDirectory);
            try (Git git = cloneCmd.call()) {
                // Git object is here only to ensure resources are properly closed; no other actions needed.
            }
        } else {
            repoDirectory = new File(DataRepository2.getRepositoryDirectory(), "/.git");
            try (Git git = new Git(FileRepositoryBuilder.create(repoDirectory))) {
                git.pull()
                        .setProgressMonitor(monitor)
                        .setContentMergeStrategy(ContentMergeStrategy.THEIRS)
                        .call();
            }
        }
    }


    public static boolean shouldUseGitee() {
        boolean localIsCN = "zh_CN".equalsIgnoreCase(Locale.getDefault().toString());
        if (!localIsCN) {
            return false;
        }
        // If locale is zh_CN, then test both GitHub and Gitee to see which one is faster.
        return isGiteeFaster();
    }

    private static boolean isGiteeFaster() {
        int numberOfPings = 3;
        long giteeTotalTime = 0;
        long githubTotalTime = 0;

        try {
            InetAddress giteeAddress = InetAddress.getByName(GITEE_HOST);
            InetAddress githubAddress = InetAddress.getByName(GITHUB_HOST);

            for (int i = 0; i < numberOfPings; i++) {
                long startTime = System.currentTimeMillis();
                if (giteeAddress.isReachable(TIMEOUT)) {
                    giteeTotalTime += (System.currentTimeMillis() - startTime);
                } else {
                    giteeTotalTime += TIMEOUT;
                }

                startTime = System.currentTimeMillis();
                if (githubAddress.isReachable(TIMEOUT)) {
                    githubTotalTime += (System.currentTimeMillis() - startTime);
                } else {
                    githubTotalTime += TIMEOUT;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // If there is an exception, use Github directly
            return false;
        }

        // Calculate average ping time, choose faster server
        return (giteeTotalTime / numberOfPings) < (githubTotalTime / numberOfPings);
    }

    private static boolean isNetworkAvailable() {
        return isHostReachable(GITHUB_HOST) || isHostReachable(GITEE_HOST);
    }

    private static boolean isHostReachable(String host) {
        try {
            InetAddress address = InetAddress.getByName(host);
            return address.isReachable(TIMEOUT);
        } catch (Exception e) {
            return false;
        }
    }


    public static void prepareForRefresh() {
        repositoryUpdated.set(false);
    }
}
