package com.dlsc.jfxcentral2.utils;


/**
 * Utility class for accessing the event bus.
 * This class provides a singleton instance of the event bus
 * that can be used to post events and register event handlers.
 */
public class EventBusUtil {


    private static final EventBus eventBus = new EventBus();

    private EventBusUtil() {
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

    public static <T> void subscribe(Class<T> eventType, Object owner, java.util.function.Consumer<T> listener) {
        eventBus.subscribe(eventType, owner, listener);
    }

    public static void unregister(Object object) {
        eventBus.unregister(object);
    }

    public static void post(Object event) {
        eventBus.post(event);
    }

}
