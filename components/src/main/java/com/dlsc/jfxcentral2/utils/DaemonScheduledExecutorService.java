package com.dlsc.jfxcentral2.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * A utility class to create a ScheduledExecutorService that uses daemon threads.
 * Daemon threads do not prevent the JVM from exiting when the program finishes but the thread is still running.
 */
public class DaemonScheduledExecutorService {
    private static final Logger LOGGER = LogManager.getLogger(DaemonScheduledExecutorService.class);


    public static ScheduledExecutorService create() {
        try {
            return Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
        } catch (Exception e) {
            LOGGER.error("Failed to create a ScheduledExecutorService", e);
            throw new RuntimeException("Failed to create a ScheduledExecutorService", e);
        }
    }

    private static class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            if (r == null) {
                LOGGER.error("Runnable is null");
                throw new NullPointerException("Runnable is null");
            }
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }
}
