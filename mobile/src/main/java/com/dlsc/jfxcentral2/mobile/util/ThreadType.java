package com.dlsc.jfxcentral2.mobile.util;

/**
 * Enumeration of possible execution threads for handling events.
 * This enum defines the different thread contexts that can be used
 * to execute event handling methods.
 */
public enum ThreadType {
    /**
     * Indicates that the event handler should be executed on the same thread
     * that posts the event. This is the default behavior.
     *
     * <p>Use this mode if the event handling is quick and does not involve any
     * UI updates or long-running tasks. It helps to avoid the overhead of thread
     * switching.</p>
     */
    POSTING,

    /**
     * Indicates that the event handler should be executed on a virtual thread.
     * Virtual threads are lightweight threads that can help to improve concurrency
     * and resource utilization.
     *
     * <p>Use this mode for tasks that can benefit from high concurrency and do not
     * require direct interaction with the UI thread. Virtual threads are suitable
     * for I/O-bound operations and large numbers of concurrent tasks.</p>
     */
    VIRTUAL,

    /**
     * Indicates that the event handler should be executed on the JavaFX Application
     * Thread. This is necessary for any event handling that updates the UI components.
     *
     * <p>Use this mode for tasks that need to update JavaFX UI components, as they
     * must be modified on the JavaFX Application Thread to ensure thread safety.</p>
     */
    FX,
}
