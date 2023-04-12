package com.dlsc.jfxcentral2.utils;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;

public class JFXCentralUtil {
    private JFXCentralUtil() {
    }

    public static void run(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        runnable.run();
    }

    public static void run(ObjectProperty<Runnable> runnableProperty) {
        if (runnableProperty == null) {
            return;
        }
        run(runnableProperty.get());
    }

    public static void runInFXThread(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

    public static void runInFXThread(ObjectProperty<Runnable> runnableProperty) {
        if (runnableProperty == null) {
            return;
        }
        runInFXThread(runnableProperty.get());
    }

}
