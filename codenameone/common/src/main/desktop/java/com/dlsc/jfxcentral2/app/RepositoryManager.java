package com.dlsc.jfxcentral2.app;

import com.codename1.io.Storage;
import com.dlsc.jfxcentral.data.DataRepository;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/// Tracks whether the content is ready. The desktop build cloned and pulled the
/// jfxcentral-data git repository here; this build reads the same files over
/// HTTP (see [DataRepository]), so "updating the repository" is fetching the
/// collection files again.
public final class RepositoryManager {

    /// Written once the first load succeeded, so a second start can show the
    /// home page straight away and refresh behind it.
    private static final String LOADED_MARKER = "jfxcentral-loaded";

    private static final BooleanProperty repositoryUpdated = new SimpleBooleanProperty(false);

    private RepositoryManager() {
    }

    public static boolean isRepositoryUpdated() {
        return repositoryUpdated.get();
    }

    public static BooleanProperty repositoryUpdatedProperty() {
        return repositoryUpdated;
    }

    public static boolean isFirstTimeSetup() {
        return !Storage.getInstance().exists(LOADED_MARKER);
    }

    /// Loads or refreshes the content off the UI thread and marks it updated on
    /// the JavaFX thread. Setup counts as done only once the content arrived:
    /// a first start without a network leaves the marker unwritten, so the next
    /// start fetches again, and `onDone` hears that it failed.
    public static void updateRepository(boolean refresh, java.util.function.Consumer<Boolean> onDone) {
        Thread thread = new Thread(() -> {
            DataRepository repository = DataRepository.getInstance();
            boolean loaded = refresh ? repository.reload() : repository.load();
            if (loaded) {
                Storage.getInstance().writeObject(LOADED_MARKER, Boolean.TRUE);
            }
            Platform.runLater(() -> {
                repositoryUpdated.set(loaded);
                if (onDone != null) {
                    onDone.accept(loaded);
                }
            });
        }, "jfxcentral-data");
        thread.start();
    }
}
