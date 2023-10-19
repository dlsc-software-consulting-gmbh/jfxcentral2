package com.dlsc.jfxcentral2.utils;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.util.Duration;

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

    public static void runInFXThread(Runnable runnable, long delayMillis) {
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(delayMillis));
        pauseTransition.setOnFinished(event -> {
            if (runnable != null) {
                runnable.run();
            }
        });
        pauseTransition.play();
    }

}
