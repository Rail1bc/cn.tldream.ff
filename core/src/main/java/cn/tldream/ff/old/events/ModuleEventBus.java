package cn.tldream.ff.old.events;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class ModuleEventBus {
    private static final Map<Class<?>, List<Consumer<Object>>> listeners = new ConcurrentHashMap<>();

    public static <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
            .add((Consumer<Object>) listener);
    }

    public static <T> void publish(T event) {
        listeners.getOrDefault(event.getClass(), Collections.emptyList())
            .forEach(listener -> listener.accept(event));
    }
}
