package com.dlsc.jfxcentral2.utils;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * EventBus implementation for JavaFX applications.
 * This class manages the registration of event listeners and the dispatching
 * of events to the appropriate handlers based on the specified thread mode.
 */
public class EventBus {

    // Listeners are registered by event type, explicitly. The desktop version
    // found @Subscribe methods by reflection, which a ParparVM build cannot do:
    // nothing would reference those methods, so they are stripped.
    private final Map<Class<?>, List<Subscription<?>>> listenerMap = new HashMap<>();

    public <T> void subscribe(Class<T> eventType, Object owner, Consumer<T> listener) {
        listenerMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(new Subscription<>(owner, listener));
    }

    public void unregister(Object owner) {
        listenerMap.values().forEach(list -> list.removeIf(l -> l.owner() == owner));
    }

    @SuppressWarnings("unchecked")
    public void post(Object event) {
        List<Subscription<?>> listeners = listenerMap.get(event.getClass());
        if (listeners != null) {
            for (Subscription<?> s : new ArrayList<>(listeners)) {
                ((Consumer<Object>) s.listener()).accept(event);
            }
        }
    }

    private record Subscription<T>(Object owner, Consumer<T> listener) {
    }
}
