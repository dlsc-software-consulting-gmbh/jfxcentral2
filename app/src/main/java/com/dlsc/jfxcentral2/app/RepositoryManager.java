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
import java.net.*;
import java.util.Locale;

public class RepositoryManager {

    private static final BooleanProperty repositoryUpdated = new SimpleBooleanProperty();
    private static final String GITHUB_HOST = "github.com";
    private static final String GITEE_HOST = "gitee.com";
    private static final String GITHUB_REPOSITORY_URL = "https://github.com/dlsc-software-consulting-gmbh/jfxcentral-data.git";
    private static final String GITEE_REPOSITORY_URL = "https://gitee.com/leewyatt/jfxcentral-data-mirror.git";
    private static final int TIMEOUT = 2000;
    private static final int HTTP_PORT = 443;

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
        // Network not available, skip the initial load
        if (!isNetworkAvailable()) {
            monitor.beginTask("Network not available.", 1);
            monitor.endTask();
            return;
        }

        File repoDirectory = DataRepository2.getRepositoryDirectory();
        if (!repoDirectory.exists()) {
            String repoUrl = GITHUB_REPOSITORY_URL;

            if (isCountryEqualToChina()) {
                monitor.beginTask("Checking network...", 1);
                repoUrl = shouldUseGiteeMirror() ? GITEE_REPOSITORY_URL : GITHUB_REPOSITORY_URL;
                monitor.endTask();
            }

            CloneCommand cloneCmd = Git.cloneRepository()
                    .setProgressMonitor(monitor)
                    .setURI(repoUrl)
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

    private static boolean shouldUseGiteeMirror() {
        if (!isCountryEqualToChina()) {
            return false;
        }
        // If locale is CN, then test both GitHub and Gitee to see which one is faster.
        return isGiteeFaster();
    }

    private static boolean isCountryEqualToChina() {
        return "CN".equalsIgnoreCase(Locale.getDefault().getCountry());
    }

    private static long pingHost(String host) {
        try (Socket socket = new Socket()) {
            long startTime = System.currentTimeMillis();
            socket.connect(new InetSocketAddress(host, HTTP_PORT), TIMEOUT);
            return System.currentTimeMillis() - startTime;
        } catch (IOException e) {
            return TIMEOUT;
        }
    }

    private static boolean isGiteeFaster() {
        int numberOfPings = 3;
        long giteeTotalTime = 0;
        long githubTotalTime = 0;

        for (int i = 0; i < numberOfPings; i++) {
            giteeTotalTime += pingHost(GITEE_HOST);
            githubTotalTime += pingHost(GITHUB_HOST);
        }

        return (giteeTotalTime * 1.0 / numberOfPings) < (githubTotalTime * 1.0 / numberOfPings);
    }

    private static boolean isNetworkAvailable() throws IOException {
        return isHostAvailable(GITEE_HOST) || isHostAvailable(GITHUB_HOST);
    }

    private static boolean isHostAvailable(String hostName) throws IOException {
        try (Socket socket = new Socket()) {
            InetSocketAddress socketAddress = new InetSocketAddress(hostName, HTTP_PORT);
            socket.connect(socketAddress, TIMEOUT);
            return true;
        } catch (UnknownHostException unknownHost) {
            return false;
        }
    }

    public static void prepareForRefresh() {
        repositoryUpdated.set(false);
    }
}
