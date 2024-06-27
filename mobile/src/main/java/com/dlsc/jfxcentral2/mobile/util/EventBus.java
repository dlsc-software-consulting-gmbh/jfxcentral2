package com.dlsc.jfxcentral2.mobile.util;

import javafx.application.Platform;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventBus implementation for JavaFX applications.
 * This class manages the registration of event listeners and the dispatching
 * of events to the appropriate handlers based on the specified thread mode.
 */
public class EventBus {

    private final Map<Class<?>, List<EventListener>> listenerMap = new HashMap<>();


    /**
     * Registers an object to listen for events. The object's methods annotated
     * with @Subscribe will be invoked when events of the appropriate type are posted.
     *
     * @param listener the object to register
     */
    public void register(Object listener) {
        Method[] methods = listener.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Subscribe.class) && method.getParameterCount() == 1) {
                Class<?> eventType = method.getParameterTypes()[0];
                // Default to posting thread mode
                ThreadType threadType = ThreadType.POSTING;
                if (method.isAnnotationPresent(RunOn.class)) {
                    threadType = method.getAnnotation(RunOn.class).value();
                }
                listenerMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(new EventListener(listener, method, threadType));
            }
        }
    }

    /**
     * Unregisters an object, removing it from the list of event listeners.
     *
     * @param listener the object to unregister
     */
    public void unregister(Object listener) {
        listenerMap.values().forEach(list -> list.removeIf(l -> l.target() == listener));
    }

    /**
     * Posts an event to all registered listeners. The listeners' methods
     * will be invoked on the appropriate thread based on the specified thread mode.
     *
     * @param event the event to post
     */
    public void post(Object event) {
        List<EventListener> eventListeners = listenerMap.get(event.getClass());
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                switch (listener.threadType()) {
                    case POSTING:
                        listener.invoke(event);
                        break;
                    case FX:
                        Platform.runLater(() -> listener.invoke(event));
                        break;
                    default:
                        listener.invoke(event);
                        break;
                }
            }
        }
    }

    private record EventListener(Object target, Method method, ThreadType threadType) {

        public void invoke(Object event) {
                try {
                    method.setAccessible(true);
                    method.invoke(target, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
}
