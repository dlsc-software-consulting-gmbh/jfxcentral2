package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository2;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.merge.ContentMergeStrategy;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;

public class LoadRepository {

    /* This main is called by JPro during startup! */
    public static void main(String[] args) {
        requestInitialUpdate();
    }

    static boolean isRequested;

    public static void requestInitialUpdate() {
        if (!isRequested) {
            isRequested = true;
            try {
                initialLoad();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void initialLoad() throws Exception {
        if (System.getProperty("jfxcentral.repo") == null) {
            File repoDirectory = DataRepository2.getRepositoryDirectory();
            if (!repoDirectory.exists()) {
                Git.cloneRepository()
                        .setURI("https://github.com/dlemmermann/jfxcentral-data.git") //
                        .setBranch("live")
                        .setDirectory(repoDirectory)
                        .call();
            } else {
                repoDirectory = new File(DataRepository2.getRepositoryDirectory(), "/.git");
                Git git = new Git(FileRepositoryBuilder.create(repoDirectory));
                git.pull().setContentMergeStrategy(ContentMergeStrategy.THEIRS).call();
            }

            Git.shutdown();
        }
    }
}
