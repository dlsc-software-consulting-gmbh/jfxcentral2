package com.dlsc.jfxcentral2.app.utils;

import com.dlsc.jfxcentral2.app.RepositoryManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/// Drives the first load and later refreshes for the refresh page. A git clone
/// reported progress per object; an HTTP load of a few JSON files is quick, so
/// the page shows a message and no percentage (`-1`).
public class RepositoryUpdater {

    private final StringProperty loadMessage = new SimpleStringProperty(this, "loadMessage", "");
    private final IntegerProperty loadPercentage = new SimpleIntegerProperty(this, "loadPercentage", -1);

    private final javafx.beans.property.BooleanProperty failed =
            new javafx.beans.property.SimpleBooleanProperty(this, "failed", false);

    public void performUpdate(boolean refresh) {
        failed.set(false);
        loadMessage.set(refresh ? "Checking for updates ..." : "Loading content ...");
        RepositoryManager.updateRepository(refresh, loaded -> {
            failed.set(!loaded);
            loadMessage.set(loaded ? "Done" : "No connection. Tap to try again.");
        });
    }

    /// Whether the last update brought no content, so the page offers a retry.
    public javafx.beans.property.ReadOnlyBooleanProperty failedProperty() {
        return failed;
    }

    public String getLoadMessage() {
        return loadMessage.get();
    }

    public StringProperty loadMessageProperty() {
        return loadMessage;
    }

    public int getLoadPercentage() {
        return loadPercentage.get();
    }

    public IntegerProperty loadPercentageProperty() {
        return loadPercentage;
    }
}
